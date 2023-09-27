// import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function MeditationThumbnail({ index, thumbnailImageUrl }) {
//   const [meditationData, setMeditationData] = useState(null);
  const history = useNavigate();
  const accessToken = localStorage.getItem('accessToken');
  const handleClick = () => {
    // Axios 요청을 이용하여 명상 글 데이터 가져오기
    axios.get(`/api/meditation/${index}`, {
        headers : {
            'Authorization' : `Bearer ${accessToken}`,
        }
    })
      .then((response) => {
        // setMeditationData(response.data);
        // 클릭 시 해당 명상 글 상세 페이지로 이동
        history.push(`/meditation/${index}`, { meditationData: response.data });
      })
      .catch((error) => {
        console.error("Error fetching meditation data:", error);
      });
  };

  return (
    <div className="thumbnail" onClick={handleClick}>
      <img src={thumbnailImageUrl} alt={`Thumbnail ${index}`} />
    </div>
  );
}

export default MeditationThumbnail;
