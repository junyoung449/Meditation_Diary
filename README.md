# 🧘심신수양록
**이미지 ➡️ 텍스트 ➡️ 음성**
<br>

**목표 : 자존감이 낮은, 수면장애가 있는 현대인들을 위해 명상 서비스를 제공**

1. 명상 음성 제공

 - 유저가 촬영한 사진을 바탕으로 명상 문구를 음성으로 실행합니다.

2. 피드

 - 유저가 작성한 명상 글 목록을 피드 형태로 조회합니다.

3. 캘린더

 - 각 날짜에 작성된 명상 글을 조회합니다.

 <br>


## 📅프로젝트 기간

**23.08.21 ~ 23.10.06 (6주간)**

## 🧝‍♂️팀원 및 역할

| **이름** | 역할 |
|----------|---------------------|
| **팀장** | 허준영 (AI) |
| **팀원** | 이동현 (Design, Presentation) |
|          | 정내혁 (FE)  |
|          | 이형석 (FE, BE, Infra, AI)     |

## 👨‍👩‍👧협업 툴

- GitLab
- Jira
- Notion
- Mattermost
- Webex



## 🛠️주요 기능

<details>
<summary>1. 로그인</summary>

![Alt text](readme사진/image-3.png)
![Alt text](readme사진/image-21.png)
![Alt text](readme사진/image-22.png)
</details></br>

<details>
<summary>2. 사진 업로드</summary>

![Alt text](readme사진/image-4.png)
![Alt text](readme사진/image-7.png)
![Alt text](readme사진/image-6.png)
</details></br>

<details>
<summary>3. 명상 글 조회</summary>
![Alt text](readme사진/image-8.png)
![Alt text](readme사진/image-9.png)
![Alt text](readme사진/image-10.png)
![Alt text](readme사진/image-11.png)
</details></br>

<details>
<summary>4. 피드, 캘린더 조회</summary>
![Alt text](readme사진/image-12.png)
![Alt text](readme사진/image-13.png)
![Alt text](readme사진/image-14.png)
</details></br>

## 🖥️ 개발 환경

🖱**Backend**

- IntelliJ
- spring boot 2.7.13
- spring-boot-jpa
- Java 11.0.18
- mysql 8.0.33

🖱**Frontend**

- Visual Studio Code
- React.js 18.2.0
- node.js 18.16.1
- axios 1.4.0

🖱**AI**

- Pycharm
- FastAPI
- Python 3.9
- Clarifai
- ElevenLabs
- OpenAI

🖱**CI/CD**

- AWS EC2
- docker
- nginx
- jenkins

## 🔧 서비스 아키텍쳐

![image](https://github.com/gudtjr2949/gudtjr2949/assets/83962223/4fd848c8-94ad-4d9f-9b1e-72c567d14c87)
<br>

## 📑 API 명세서

https://silent-asterisk-64c.notion.site/API-530765c9f7e14241addc7cdc23b89eef?pvs=4
<br>

## ✨ERD

![image](https://github.com/gudtjr2949/gudtjr2949/assets/83962223/69fa9ccf-1a75-4b31-b2ac-c1c995290119)
<br>

## 🌐EC2 PORT

| 서비스              | 포트  |
|---------------------|-------|
| Spring Boot (BE)    | 9090  |
| React (FE)          | 3000  |
| FastAPI             | 8000  |
| MySQL (DB)          | 3500  |
| Jenkins             | 8080  |


