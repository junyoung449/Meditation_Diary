import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Waiting.css'; // 스타일 파일을 불러옵니다.
import axios from "axios";

function Waiting() {
  const navigate = useNavigate();
  const [audioPlayed, setAudioPlayed] = useState(false);
  let audio = null;

  const playAudio = () => {
    if (audio) {
      audio.play();
    }
  };

  useEffect(() => {
    // 오디오 파일 생성
    audio = new Audio('/audio/loading_naration.wav');
    // 오디오 재생 종료 시 페이지 이동 예약
    audio.addEventListener('ended', () => {
      setAudioPlayed(true);
    });

    // 컴포넌트 언마운트 시 리소스 해제
    return () => {
      if (audio) {
        audio.pause();
        audio.removeEventListener('ended', () => {});
      }
    };

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // 오디오 재생 후 페이지 이동
  useEffect(() => {
    if (audioPlayed) {
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
          navigate('/welcome');
        } else {
          const idx = response.data.meditationList[response.data.meditationList.length-1].meditationIdx
          axios.get(`https://j9b205.p.ssafy.io/api/meditation/${idx}`, {
              headers : {
                  'Authorization' : `Bearer ${accessToken}`,
              }
          })
          .then((response) => {
              console.log('meditationData :', response.data);
              // setMeditationData(response.data);
              // 클릭 시 해당 명상 글 상세 페이지로 이동
              navigate(`/meditation/${idx}`, { state: { meditationData: response.data } });
          })
          .catch((error) => {
              console.error("Error fetching meditation data:", error);
          });
        }
      })
      .catch((error) => {
        console.error("Error fetching meditation list:", error);
      });
    }
  }, [audioPlayed, navigate]);

  return (
    <div className="audio-player">
      <div className="loading-text">명상을 준비하는 시간입니다</div>
      <button className="audio-button" onClick={playAudio}>
        <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/image/play.png" alt="Play Audio" />
      </button>
    </div>
  );
}

export default Waiting;
