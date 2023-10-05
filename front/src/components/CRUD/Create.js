import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function ImageUpload() {
  const navigate = useNavigate();

  const handleImageChange = (event) => {
    const selectedImages = Array.from(event.target.files);
    navigate('/audio-upload', { state: {selectedImages: selectedImages }});
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
