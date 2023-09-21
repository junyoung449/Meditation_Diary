# -*- coding: utf-8 -*-
import os

from aiohttp import ClientError
from fastapi import FastAPI

# ipynb 실행용
import nbformat
from nbconvert import PythonExporter
from nbconvert.preprocessors import ExecutePreprocessor

from pydantic import BaseModel

from typing import List

import boto3

import dotenv

dotenv.load_dotenv()

app = FastAPI()

from database import engineconn

import models

# from models import MeditationAudio

app = FastAPI()

dotenv.load_dotenv()

client_s3 = boto3.client(
    's3',
    aws_access_key_id=os.getenv("CREDENTIALS_ACCESS_KEY"),
    aws_secret_access_key=os.getenv("CREDENTIALS_SECRET_KEY")
)

engine = engineconn()
session = engine.sessionmaker()

class Audio(BaseModel):
    audioName: str

class ImageURLRequest(BaseModel):
    meditationIdx: int
    images: List[str]


@app.post("/ai/text")
def ipynb(imageRequest: ImageURLRequest):
    resultList = []
    # IPython 노트북 파일 경로 설정
    ipynb_file_path = "ipynb/image_chatgpt.ipynb"

    # IPython 노트북 파일을 읽기
    with open(ipynb_file_path, 'r', encoding='utf-8') as nb_file:
        notebook = nbformat.read(nb_file, as_version=4)

    # 매개변수 전달
    for image in imageRequest.images:
        for cell in notebook.cells:
            if cell.cell_type == 'code':
                cell.source = cell.source.replace("{{ imageUrl }}", image)

        # Python 코드로 변환
        python_exporter = PythonExporter()
        python_code, _ = python_exporter.from_notebook_node(notebook)

        # Python 코드 실행 및 결과 추출
        exec_preprocessor = ExecutePreprocessor(timeout=600)
        exec_preprocessor.preprocess(notebook, {'metadata': {'path': './'}})

        # 실행 결과 가져오기
        cell_outputs = notebook.cells[-1].outputs

        # 결과를 문자열로 가져옴
        result = ""
        for output in cell_outputs:
            if 'text' in output:
                result += output['text']
            elif 'data' in output and 'text/plain' in output['data']:
                result += output['data']['text/plain']

        # resultList.append(result)

        # 명상용 텍스트 -> 음성

        # 음성파일 프로젝트에 저장

        # saveAudio(음성파일명) 호출
        audioUrl = saveAudioAtS3()

        saveMeditationAudioUrlAtDB(audioUrl, imageRequest.meditationIdx)

    return {"message": resultList}

@app.post("/ai/audio")
def saveAudioAtS3(audio : Audio):
    try:
        client_s3.upload_file(
            "./"+audio.audioName,
            os.getenv("S3_BUCKET"),
            "audio/" + audio.audioName,
            ExtraArgs={'ContentType': 'audio/mp3'}
        )
        
        # 로컬에 저장되어있는 음성파일 삭제
        print(os.getenv("S3_URL") + "/" +audio.audioName)
    except ClientError as e:
        print(f'Credential error => {e}')
    except Exception as e:
        print(f"Another error => {e}")

def saveMeditationAudioUrlAtDB(audioUrl, meditationIdx):
    db_meditationAudio = models.MeditationAudio(meditation_idx=meditationIdx, audio_url=audioUrl)
    session.add(db_meditationAudio)
    session.commit()