import React, { useState } from 'react';
import axios from 'axios';

function ImageUpload() {
  const [images, setImages] = useState([]);
  const memberIdx = localStorage.getItem('memberIdx')
  const accessToken = localStorage.getItem('accessToken')

  const handleImageChange = (event) => {
    const selectedImages = Array.from(event.target.files);
    setImages(selectedImages);
  };

  const handleImageUpload = () => {
    const formData = new FormData();
    images.forEach((image, index) => {
      formData.append(`images[${index}]`, image); // "images" 이름으로 이미지들을 전송
    });
    formData.append('memberIdx', memberIdx);

    axios
      .post('/api/meditation', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${accessToken}`,
        },
      })
      .then((response) => {
        // 이미지 업로드 성공 후 응답 처리
        console.log('Image(s) uploaded successfully:', response.data);
      })
      .catch((error) => {
        console.error('Error uploading image(s):', error);
      });
  };

  return (
    <div>
      <h1>Image Upload</h1>
      <input type="file" accept="image/*" multiple onChange={handleImageChange} />
      <button onClick={handleImageUpload}>Upload Images</button>
    </div>
  );
}

export default ImageUpload;
