import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import Footer from "layouts/CalendarAndMemoAndFeed/Footer";

function MeditationDetail() {
  const location = useLocation();
  const meditationData = location.state.meditationData;
  const [currentAudioIndex, setCurrentAudioIndex] = useState(0);
  const [audioPlayer, setAudioPlayer] = useState(null);
  const [selectedTab, setSelectedTab] = useState('Calendar'); // 기본적으로 Calendar 탭 선택
  console.log('meditationDetail에서 Data :', meditationData);

  useEffect(() => {
    // Audio 객체 생성
    const audio = new Audio();
    audio.src = meditationData.meditationMedia[currentAudioIndex].audioUrl;

    // 음성 파일이 끝나면 다음 음성 파일 재생
    audio.addEventListener("ended", () => {
      if (currentAudioIndex < meditationData.meditationMedia.length - 1) {
        setCurrentAudioIndex(currentAudioIndex + 1);
        audio.src = meditationData.meditationMedia[currentAudioIndex + 1].audioUrl;
        audio.play();
      }
    });

    setAudioPlayer(audio);

    // 컴포넌트가 언마운트될 때 리소스 해제
    return () => {
      audio.pause();
      audio.removeEventListener("ended", () => {});
    };
  }, [currentAudioIndex, meditationData]);

  const playAudio = () => {
    if (audioPlayer) {
      audioPlayer.play();
    }
  };

  const pauseAudio = () => {
    if (audioPlayer) {
      audioPlayer.pause();
    }
  };

  return (
    <div>
      <img src={meditationData.meditationMedia[currentAudioIndex].imageUrl} alt="Meditation" />
      <div>
        <button onClick={playAudio}>재생</button>
        <button onClick={pauseAudio}>일시 정지</button>
      </div>
      <Footer setSelectedTab={setSelectedTab} />
    </div>
  );
}

export default MeditationDetail;
