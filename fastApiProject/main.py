import requests

# -*- coding: utf-8 -*-
import os

from aiohttp import ClientError
from fastapi import FastAPI, File, UploadFile

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

import requests

import secrets

from pydub import AudioSegment

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
    images: List[str]
    voiceUrl: str

def TTS(voice_id, result):
    
    CHUNK_SIZE = 1024
    if voice_id:
        elevenlabs_url = "https://api.elevenlabs.io/v1/text-to-speech/" + voice_id
    else:
        elevenlabs_url = "https://api.elevenlabs.io/v1/text-to-speech/1Y8VEQXMwbo4IxJO2JKy"

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

    name = secrets.token_hex(nbytes=16)

    response = requests.post(elevenlabs_url, json=elevenlabs_data, headers=headers)
    with open("./audio/" + name + ".mp3", 'wb') as f:
        for chunk in response.iter_content(chunk_size=CHUNK_SIZE):
            if chunk:
                f.write(chunk)
    return name

def add(id, file):
    url = "https://api.elevenlabs.io/v1/voices/add"
    
    headers = {
    "Accept": "application/json",
    "xi-api-key": "4825d47028ddf82c66449a5b4429b775"
    }

    data = {
        'name': id
    }
    files = {
        ('files', (file, open(file, 'rb'), 'audio/mpeg'))
    }
    response = requests.post(url, headers=headers, data=data, files=files)
    return response

def delete(voice_id):
    
    headers = {
    "Accept": "application/json",
    "xi-api-key": "4825d47028ddf82c66449a5b4429b775"
        }
    url = "https://api.elevenlabs.io/v1/voices/" + voice_id
    response = requests.delete(url, headers=headers)
    return response



@app.post("/ai/text")
def ipynb(imageRequest: ImageURLRequest):
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

        message = ""

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

        response = openai.ChatCompletion.create(
            model=MODEL,
            messages=[
                {"role": "system",
                 "content": "존댓말로 작성해. 셰익스피어처럼 표현해줘. 나래이션의 스크립트가 필요해. 그 문단은 이 20개의 단어로 구성되어야 해. 꼭 이 단어를 전부 사용할 필요는 없어. 감성적인 표현이 주가 되어야 해. 한국어로 작성하도록 해."},
                {"role": "user", "content": USER_INPUT_MSG},
                {"role": "user", "content": "300글자 이상 500글자 이하가 되도록 문단을 작성해."},
                {"role": "assistant", "content": "네. 글자수는 300글자 이상 500글자 이하가 되도록 문단을 작성하도록 하겠습니다."},
                {"role": "user", "content": "시스템 메시지를 다시 언급하지 마. 반드시 존댓말로 작성하도록 해."},
                {"role": "assistant", "content": "반드시 존댓말로 작성해 드릴게요."},
                # {"role": "assistant", "content": "Who's there?"},
            ],
            temperature=0.7,
            max_tokens=1000  # 최대 토큰 수를 설정하여 답변의 길이를 제어합니다.
        )

        # response['choices'][0]['message']['content']

        result += f"{response['choices'][0]['message']['content']} "

    print(result)

    input_voice = imageRequest.voiceUrl
    new_voice_id = ""
    if input_voice:
        new_voice_id = add("custom", input_voice).json()["voice_id"]
    print(new_voice_id)
    name = TTS(f"{new_voice_id}", result)
    if input_voice:
        delete(f"{new_voice_id}")

    makeBackGroundMusic(name)
    fileName.append(name + ".mp3")

    return {"audios": saveAudioAtS3(fileName)}


def saveAudioAtS3(audio):
    audioUrl = []

    try:
        for a in audio:
            client_s3.upload_file(
                "./audio/" + a,
                os.getenv("S3_BUCKET"),
                "audio/" + a,
                ExtraArgs={'ContentType': 'audio/mp3'}
            )

            # 로컬에 저장되어있는 음성파일 삭제
            os.remove("./audio/" + a)

            audioUrl.append(os.getenv("S3_URL") + "/" + a)

        return audioUrl
    except ClientError as e:
        print(f'Credential error => {e}')
    except Exception as e:
        print(f"Another error => {e}")


def makeBackGroundMusic(name):
    # 배경 음악 파일 로드
    background_music = AudioSegment.from_mp3("./audio/back.mp3") - 4

    # 원본 mp3 파일 로드
    original_audio = AudioSegment.from_mp3("./audio/" + name + ".mp3")

    # 로컬에 있는 mp3 파일 삭제
    os.remove("./audio/" + name + ".mp3")

    # 배경 음악과 원본 오디오 합치기
    output_audio = background_music.overlay(original_audio)

    # 결과를 새 파일로 저장
    output_audio.export("./audio/" + name + ".mp3", format="mp3")

    print("배경 음악이 추가된 오디오를 저장했습니다.")
