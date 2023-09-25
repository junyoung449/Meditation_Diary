# -*- coding: utf-8 -*-
import os

from aiohttp import ClientError
from fastapi import FastAPI, File, UploadFile
from pathlib import Path

# ipynb 실행용
import nbformat
from nbconvert import PythonExporter
from nbconvert.preprocessors import ExecutePreprocessor

from pydantic import BaseModel

from typing import List

import boto3

import dotenv

import requests

from pydub import AudioSegment

AudioSegment.ffmpeg = "ffmpeg-2023-09-07-git-9c9f48e7f2-full_build/bin/ffmpeg.exe"
dotenv.load_dotenv()

app = FastAPI()

dotenv.load_dotenv()

client_s3 = boto3.client(
    's3',
    aws_access_key_id=os.getenv("CREDENTIALS_ACCESS_KEY"),
    aws_secret_access_key=os.getenv("CREDENTIALS_SECRET_KEY")
)


class Audio(BaseModel):
    audioName: List[str]


class ImageURLRequest(BaseModel):
    # meditationIdx: int
    images: List[str]


@app.post("/ai/text")
def ipynb(imageRequest: ImageURLRequest):
    audioUrl = []
    fileName = []

    # IPython 노트북 파일 경로 설정
    ipynb_file_path = "ipynb/image_chatgpt.ipynb"

    # IPython 노트북 파일을 읽기
    with open(ipynb_file_path, 'r', encoding='utf-8') as nb_file:
        notebook = nbformat.read(nb_file, as_version=4)

    print(imageRequest.images)

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


        print(result)
        CHUNK_SIZE = 1024
        elevenlabs_url = "https://api.elevenlabs.io/v1/text-to-speech/jDf0qpioBfjTxjqlFBsW"

        headers = {
            "Accept": "audio/mpeg",
            "Content-Type": "application/json",
            "xi-api-key": os.getenv("ELEVENLABS_API_KEY")
        }

        elevenlabs_data = {
            "text": result,
            "model_id": "eleven_multilingual_v2",
            "voice_settings": {
                "stability": 0.35,
                "similarity_boost": 0.75,
                "style": 0.27
            }
        }
        response = requests.post(elevenlabs_url, json=elevenlabs_data, headers=headers)
        with open('./audio/output.mp3', 'wb') as f:
            for chunk in response.iter_content(chunk_size=CHUNK_SIZE):
                if chunk:
                    f.write(chunk)

        fileName.append("파일명")

    image_count = len(imageRequest.images)

    if image_count == 1:
        fileName.append("test1.mp3")
    elif image_count == 2:
        fileName.append("test1.mp3")
        fileName.append("test2.mp3")

    return {"audios": saveAudioAtS3(fileName)}


# @app.post("/ai/audio")
def saveAudioAtS3(audio):
    audioUrl = []

    # audio = []

    # audio.append("440Hz-5sec.mp3")
    # audio.append("1000Hz-5sec.mp3")

    try:
        for a in audio:
            client_s3.upload_file(
                "./audio/" + a,
                os.getenv("S3_BUCKET"),
                "audio/" + a,
                ExtraArgs={'ContentType': 'audio/mp3'}
            )

            # 로컬에 저장되어있는 음성파일 삭제
            # os.remove("./audio/" + a)

            audioUrl.append(os.getenv("S3_URL") + "/" + a)

        return audioUrl
    except ClientError as e:
        print(f'Credential error => {e}')
    except Exception as e:
        print(f"Another error => {e}")


@app.get("/ai/back")
def makeBackGroundMusic():
    # 배경 음악 파일 로드
    background_music = AudioSegment.from_mp3("./audio/back.mp3")

    # 원본 mp3 파일 로드
    # original_audio = AudioSegment.from_mp3("./audio/"+audioName+".mp3")
    original_audio = AudioSegment.from_mp3("./audio/speak.mp3")

    # 배경 음악과 원본 오디오 합치기
    output_audio = original_audio.overlay(background_music)

    # 결과를 새 파일로 저장
    output_audio.export("output_audio.mp3", format="mp3")

    print("배경 음악이 추가된 오디오를 저장했습니다.")
