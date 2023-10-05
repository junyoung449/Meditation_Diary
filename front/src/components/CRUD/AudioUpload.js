import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';

function AudioUpload() {
  const navigate = useNavigate();
  const location = useLocation();
  const formData = new FormData();
  const selectedImages = location.state.selectedImages; // ImageUpload 컴포넌트에서 전달된 FormData

  console.log('selectedImages :', selectedImages);

  const memberIdx = localStorage.getItem('memberIdx');
  formData.append('memberIdx', memberIdx);

  selectedImages.forEach((image) => {
    formData.append('images', image); // 이미지를 FormData에 추가
  });

  const handleAudioChange = (event) => {
    const selectedAudios = Array.from(event.target.files);

    selectedAudios.forEach((audio) => {
      formData.append('voice', audio); // 오디오를 FormData에 추가
    });

    // 오디오를 추가하면 자동으로 axios POST 요청 보내고 다음 페이지로 이동
    upload();
  };

  const navigateToNextPage = () => {
    navigate('/waiting'); // 로딩 페이지의 경로를 설정하세요
  };

  const upload = () => {
    const accessToken = localStorage.getItem('accessToken');
    
    // axios를 사용하여 FormData를 서버로 전송
    axios
      .post('/api/meditation', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${accessToken}`,
        },
      })
    navigateToNextPage(); // 업로드 완료 후 다음 페이지로 이동
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '100vh' }}>
      {/* 이미지 아이콘을 클릭하면 파일 선택(input)이 클릭되도록 설정 */}
      <label htmlFor="audioInput" style={{ cursor: 'pointer', marginBottom: 10}}>
        <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/image/upload.png" alt="Upload Icon"/>
      </label>
      <input type="file" id="audioInput" accept="audio/*" multiple onChange={handleAudioChange} style={{ display: 'none' }} />
      <h2 style={{ fontWeight: 'bold', marginTop: '30px', color: '#000000' }}>Upload Your Audio</h2>
      {/* 이미지로 대체된 스킵 버튼 */}
      <img
        src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/image/skip.png"
        alt="Skip"
        onClick={upload}
        style={{
          cursor: 'pointer',
          position: 'fixed',
          bottom: '30px',
          right: '30px',
        }}
      />
      <p style={{ marginBottom: '25px' }}>Skip</p> {/* Skip 텍스트 추가 */}
    </div>
  );
}

export default AudioUpload;
