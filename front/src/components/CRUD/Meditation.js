import React, { useState, useEffect } from "react";
import axios from "axios";

function Meditation({ match }) {
  const [audioUrl, setAudioUrl] = useState("");
  const meditationIdx = match.params.index; // 경로에서 meditationIdx 추출

  useEffect(() => {
    // 해당 meditationIdx에 대한 음성 파일 URL을 가져옴
    axios
      .get(`/api/meditation/${meditationIdx}`)
      .then((response) => {
        setAudioUrl(response.data.audioUrl);
      })
      .catch((error) => {
        console.error("Error fetching audio URL:", error);
      });
  }, [meditationIdx]);

  return (
    <div>
      {audioUrl && (
        <div>
          <h2>Audio</h2>
          <audio controls>
            <source src={audioUrl} type="audio/mp3" />
            Your browser does not support the audio element.
          </audio>
        </div>
      )}
      {/* 다른 게시글 정보 표시 */}
    </div>
  );
}

export default Meditation;
