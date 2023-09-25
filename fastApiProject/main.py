# ElevenLabs 실행용

import requests

# -*- coding: utf-8 -*-
import os

from aiohttp import ClientError
from fastapi import FastAPI, File, UploadFile
from pathlib import Path


# image_chatgpt

from clarifai_grpc.channel.clarifai_channel import ClarifaiChannel
from clarifai_grpc.grpc.api import resources_pb2, service_pb2, service_pb2_grpc
from clarifai_grpc.grpc.api.status import status_code_pb2


import openai

import dotenv
import os

# ipynb 실행용
# import nbformat
# from nbconvert import PythonExporter
# from nbconvert.preprocessors import ExecutePreprocessor
import boto3

from pydantic import BaseModel

from typing import List

from pydub import AudioSegment

AudioSegment.ffmpeg = "ffmpeg-2023-09-07-git-9c9f48e7f2-full_build/bin/ffmpeg.exe"
dotenv.load_dotenv()

app = FastAPI()

dotenv.load_dotenv()

OPENAI_YOUR_KEY = os.getenv("CHATGPT_API_KEY")
openai.api_key = OPENAI_YOUR_KEY


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

    PAT = os.getenv("CLARIFAI_API_KEY")
    USER_ID = 'clarifai'
    APP_ID = 'main'
    MODEL_ID = 'general-image-recognition'
    MODEL_VERSION_ID = 'aa7f35c01e0642fda5cf400f543e7c40'
    

    print(imageRequest.images)
    result = ""
    # 매개변수 전달
    for image in imageRequest.images:
        
        IMAGE_URL = image
        
        message = "해당 단어를 결합해 감성적인 문장을 만들어줘. 명상할 때 읽어줄 문장이고 해당 단어는 그날 명상하는 사람이 찍은 사진에 나온 단어야. 그 사람의 입장을 생각해서 만들어줘. 모든 단어를 활용할 필요는 없어. 이런 느낌을 느낄 수 있지 않았을까 하는 공감대를 형성하는 방향으로 문장을 작성해줘. 단어 뒤에 나온 숫자는 사진에서 자동으로 인식한 단어의 인식률이야. 그리고 영어로 답변해줘"

        channel = ClarifaiChannel.get_grpc_channel()
        stub = service_pb2_grpc.V2Stub(channel)

        metadata = (('authorization', 'Key ' + PAT),)

        userDataObject = resources_pb2.UserAppIDSet(user_id=USER_ID, app_id=APP_ID)

        post_model_outputs_response = stub.PostModelOutputs(
            service_pb2.PostModelOutputsRequest(
                user_app_id=userDataObject,
                model_id=MODEL_ID,
                version_id=MODEL_VERSION_ID, 
                inputs=[
                    resources_pb2.Input(
                        data=resources_pb2.Data(
                            image=resources_pb2.Image(
                                url=IMAGE_URL
                            )
                        )
                    )
                ]
            ),
            metadata=metadata
        )

        if post_model_outputs_response.status.code != status_code_pb2.SUCCESS:
            print(post_model_outputs_response.status)
            raise Exception("Post model outputs failed, status: " + post_model_outputs_response.status.description)

        output = post_model_outputs_response.outputs[0]

        print("Predicted concepts:")
        for concept in output.data.concepts:
            print("%s %.2f" % (concept.name, concept.value))
            message += concept.name + " " + str(concept.value) + "\n"


        MODEL = "gpt-3.5-turbo"
        USER_INPUT_MSG = message
        message2 = ""

        response = openai.ChatCompletion.create(
            model=MODEL,
            messages=[
                {"role": "system", "content": "You are a helpful assistant."},
                {"role": "user", "content": USER_INPUT_MSG},
                # {"role": "assistant", "content": "Who's there?"},
            ],
            temperature=0.5,
            max_tokens=600  # 최대 토큰 수를 설정하여 답변의 길이를 제어합니다.
        )

        message2 += response['choices'][0]['message']['content']

        # response['choices'][0]['message']['content']


        message2 += " 이걸 한국말로 번역해줘."


        USER_INPUT_MSG2 = message2
        MODEL2 = "gpt-3.5-turbo-0613"

        response2 = openai.ChatCompletion.create(
            model=MODEL2,
            messages=[
                {"role": "system", "content": "You are a kind assistant."},
                {"role": "user", "content": USER_INPUT_MSG2},
                {"role": "assistant", "content": "You must answer continuously."},
            ],
            temperature=0.6,
            max_tokens=600  # 최대 토큰 수를 설정하여 답변의 길이를 제어합니다.
        )

        result += f"{response2['choices'][0]['message']['content']} "
    

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
            "stability": 0.6,
            "similarity_boost": 1,
            "style": 0,
            "use_speaker_boost": 'false'
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
