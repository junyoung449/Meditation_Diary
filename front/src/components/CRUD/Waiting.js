import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Waiting.css'; // 스타일 파일을 불러옵니다.

function Waiting() {
  const navigate = useNavigate();

  useEffect(() => {
    // 오디오 파일 생성
    const audio = new Audio('/src/assets/naration/loading_naration.wav');

    // 오디오 재생 종료 시 페이지 이동
    audio.addEventListener('ended', () => {
      // 페이지 이동 코드를 여기에 추가
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
    });

    // 오디오 파일 재생
    audio.play();

    // 컴포넌트 언마운트 시 리소스 해제
    return () => {
      audio.pause();
      audio.removeEventListener('ended', () => {});
    };
  }, [navigate]);

  return (
    <div className="audio-player">
      <p className="loading-text">명상 파일 로딩 중입니다...</p>
    </div>
  );
}

export default Waiting;
