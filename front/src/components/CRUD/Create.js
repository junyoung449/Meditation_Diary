import React, { useState } from 'react';
import axios from 'axios';
// import Footer from 'layouts/CalendarAndMemoAndFeed/Footer';

function ImageUpload() {
  const [images, setImages] = useState([]);
  // const [selectedTab, setSelectedTab] = useState('Calendar'); // 기본적으로 Calendar 탭 선택
  const memberIdx = localStorage.getItem('memberIdx');
  const accessToken = localStorage.getItem('accessToken');

  const handleImageChange = (event) => {
    const selectedImages = Array.from(event.target.files);
    setImages(selectedImages);

    // 파일 선택 시 이미지 업로드 실행
    uploadImages(selectedImages);
  };

  const uploadImages = (selectedImages) => {
    const formData = new FormData();
    selectedImages.forEach((image) => {
      formData.append('images', image); // "images" 이름으로 이미지들을 전송
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
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '100vh' }}>
      <h1>Image Upload</h1>
      <label htmlFor="imageInput" style={{ cursor: 'pointer' }}>
        <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/footer/footer-center.png" alt="Upload Icon" width="100" height="100" />
      </label>
      <input type="file" id="imageInput" accept="image/*" multiple onChange={handleImageChange} style={{ display: 'none' }} />
      {/* <Footer setSelectedTab={setSelectedTab} /> */}
    </div>
  );
}

export default ImageUpload;
