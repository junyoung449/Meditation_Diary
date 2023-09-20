# -*- coding: utf-8 -*-
import os

import uvicorn
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


class ImageURLRequest(BaseModel):
    images: List[str]


@app.post("/ai/text")
async def ipynb(imageRequest: ImageURLRequest):
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

        resultList.append(result)

    return {"message": resultList}


import boto3

def s3_connection():
    try:
        # s3 클라이언트 생성
        s3 = boto3.client(
            service_name="s3",
            region_name="ap-northeast-2",
            aws_access_key_id="AKIAYJ4UAMZIK2UJGZF7",
            aws_secret_access_key="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",
        )
    except Exception as e:
        print(e)
    else:
        print("s3 bucket connected!")
        return s3


s3 = s3_connection()

try:
    s3.upload_file("{로컬에서 올릴 파일이름}","{버킷 이름}","{버킷에 저장될 파일 이름}")
except Exception as e:
    print(e)