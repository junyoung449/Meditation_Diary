import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import MeditationFooter from "./MeditationFooter";

function MeditationDetail() {
  const location = useLocation();
  const meditationData = location.state.meditationData;
  const [currentAudioIndex, setCurrentAudioIndex] = useState(0);
  const [audioPlayer, setAudioPlayer] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false); // 재생 상태를 나타내는 상태값
  const [selectedTab, setSelectedTab] = useState("Feed"); // 기본적으로 Feed 탭 선택
  console.log("meditationDetail에서 Data :", meditationData);

  useEffect(() => {
    // Audio 객체 생성
    const audio = new Audio();
    audio.src = meditationData.meditationMedia[currentAudioIndex].audioUrl;

    // 음성 파일이 끝나면 다음 음성 파일 재생
    audio.addEventListener("ended", () => {
      if (currentAudioIndex < meditationData.meditationMedia.length - 1) {
        setCurrentAudioIndex(currentAudioIndex + 1);
        audio.src =
          meditationData.meditationMedia[currentAudioIndex + 1].audioUrl;
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
      setIsPlaying(true); // 재생 상태를 true로 설정
    }
  };

  const pauseAudio = () => {
    if (audioPlayer) {
      audioPlayer.pause();
      setIsPlaying(false); // 재생 상태를 false로 설정
    }
  };

  return (
    <div>
      <img
        src={meditationData.meditationMedia[currentAudioIndex].imageUrl}
        alt="Meditation"
      />
      <div>
        {/* 재생 버튼과 일시 정지 버튼을 조건부 렌더링 */}
        {isPlaying ? (
          <button onClick={pauseAudio}>II</button>
        ) : (
          <button onClick={playAudio}>▶</button>
        )}
      </div>
      <MeditationFooter setSelectedTab={setSelectedTab} />
    </div>
  );
}

export default MeditationDetail;
