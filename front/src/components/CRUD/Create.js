import React, { useState } from 'react';
import axios from 'axios';

function Create() {
  const [image, setImage] = useState(null);
  const [audio, setAudio] = useState(null);

  const handleImageChange = (event) => {
    const selectedImage = event.target.files[0];
    setImage(selectedImage);
  };

  const handleImageUpload = () => {
    if (image) {
      const formData = new FormData();
      formData.append('image', image);

      // 액세스 토큰 가져오기
      const accessToken = localStorage.getItem('accessToken'); // 예: 로컬 스토리지에서 가져옴

      // axios 요청 헤더에 액세스 토큰 추가
      const config = {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      };

      axios
        .post('/api/meditation', formData, config)
        .then((response) => {
          console.log('Image uploaded successfully:', response.data);
        })
        .catch((error) => {
          console.error('Error uploading image:', error);
        });
    }
  };

  const handleAudioRequest = () => {
    // 액세스 토큰 가져오기
    const accessToken = localStorage.getItem('accessToken'); // 예: 로컬 스토리지에서 가져옴

    // axios 요청 헤더에 액세스 토큰 추가
    const config = {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    };

    axios
      .get('/api/request-audio', config)
      .then((response) => {
        console.log('Received audio file:', response.data);
        setAudio(response.data);
      })
      .catch((error) => {
        console.error('Error requesting audio:', error);
      });
  };

  return (
    <div>
      <h1>File Upload and Audio Request</h1>

      <div>
        <h2>Upload an Image</h2>
        <input type="file" accept="image/*" onChange={handleImageChange} />
        <button onClick={handleImageUpload}>Upload</button>
      </div>

      <div>
        <h2>Request Audio</h2>
        <button onClick={handleAudioRequest}>Request Audio</button>
      </div>

      {audio && (
        <div>
          <h2>Received Audio</h2>
          <audio controls>
            <source src={audio} type="audio/mp4" />
          </audio>
        </div>
      )}
    </div>
  );
}

export default Create;
