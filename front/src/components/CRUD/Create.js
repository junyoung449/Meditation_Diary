import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function ImageUpload() {
  const navigate = useNavigate();

  const handleImageChange = (event) => {
    const selectedImages = Array.from(event.target.files);
    const formData = new FormData();
    
    selectedImages.forEach((image) => {
      formData.append('images', image); // 이미지를 FormData에 추가
    });

    // 다른 페이지로 이동하면서 FormData를 전달
    navigate('/audio-upload', { state: formData });
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
