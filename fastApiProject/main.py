# -*- coding: utf-8 -*-
import os

from fastapi import FastAPI

# ipynb 실행용
import nbformat
from nbconvert import PythonExporter
from nbconvert.preprocessors import ExecutePreprocessor

from pydantic import BaseModel

from typing import List

app = FastAPI()

class ImageURLRequest(BaseModel):
    images: List[str]

@app.post("/text")
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
