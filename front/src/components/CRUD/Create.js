import React from "react";
// import React, { useState } from 'react';
// import axios from 'axios';

function Create() {
    const audio = '/audio/sample.m4a'; // public 폴더를 기준으로 상대 경로 지정
//   const [image, setImage] = useState(null);
//   const [audio, setAudio] = useState(null);

//   const handleImageChange = (event) => {
//     const selectedImage = event.target.files[0];
//     setImage(selectedImage);
//   };

//   const handleImageUpload = () => {
//     if (image) {
//       const formData = new FormData();
//       formData.append('image', image);

//       axios
//         .post('/api/upload-image', formData)
//         .then((response) => {
//           // 이미지 업로드 성공 후 응답 처리
//           console.log('Image uploaded successfully:', response.data);
//         })
//         .catch((error) => {
//           console.error('Error uploading image:', error);
//         });
//     }
//   };

//   const handleAudioRequest = () => {
//     axios
//       .get('/api/request-audio')
//       .then((response) => {
//         // 음성 파일 요청 후 응답 처리
//         console.log('Received audio file:', response.data);

//         // 받은 음성 파일을 상태로 설정
//         setAudio(response.data);
//       })
//       .catch((error) => {
//         console.error('Error requesting audio:', error);
//       });
//   };

  return (
    <div>
      <h1>File Upload and Audio Request</h1>

      <div>
        <h2>Upload an Image</h2>
        {/* <input type="file" accept="image/*" onChange={handleImageChange} />
        <button onClick={handleImageUpload}>Upload</button> */}
      </div>

      <div>
        <h2>Request Audio</h2>
        {/* <button onClick={handleAudioRequest}>Request Audio</button> */}
      </div>

      {audio && (
        <div>
          <h2>Received Audio</h2>
          {/* 음성 파일을 플레이어나 오디오 태그로 표시 */}
          <audio controls>
            <source src={audio} type="audio/mp4" />
          </audio>
        </div>
      )}
    </div>
  );
}

export default Create;
