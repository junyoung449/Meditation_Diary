import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Waiting.css'; // 스타일 파일을 불러옵니다.
import axios from "axios";

function Waiting() {
  const navigate = useNavigate();

  useEffect(() => {
    // 2분 5초(125000 밀리초) 후에 다른 페이지로 이동
    const redirectTimeout = setTimeout(() => {
        const memberIdx = parseInt(localStorage.getItem('memberIdx'),10);
      const accessToken = localStorage.getItem('accessToken');
      // 게시글 리스트를 가져오는 요청
      axios.get(`https://j9b205.p.ssafy.io/api/meditation/list/${memberIdx}`, {
        headers: {
          'Authorization': `Bearer ${accessToken}`,
        }
      })
      .then((response) => {
        if (response.data.meditationList.length === 0) {
          window.location.href = "/welcome"
        } else {
          const idx = response.data.meditationList[response.data.meditationList.length-1].meditationIdx
          navigate(`/meditation/${idx}`);
        }
      })
      .catch((error) => {
        console.error("Error fetching meditation list:", error);
      });
      }, 125000);
  
      // 컴포넌트가 언마운트될 때 타임아웃 정리
      return () => clearTimeout(redirectTimeout);
    }, [navigate]);

  return (
    <div className="audio-player">
      <p className="loading-text">명상 파일 로딩 중입니다...</p>
      <iframe
        title="Audio Player"
        width="0"
        height="0"
        src="/assets/naration/loading_naration.wav"
        frameborder="0"
        allow="autoplay"
        visibility="hidden"
      ></iframe>
    </div>
  );
}

export default Waiting;
