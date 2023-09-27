import React, { useState, useEffect } from "react";
import axios from "axios";
import MeditationThumbnail from "./Thumbnail";

function MeditationGrid({ memberIdx }) {
  const [meditationList, setMeditationList] = useState([]);
  const accessToken = localStorage.getItem('accessToken');

  useEffect(() => {
    // 게시글 리스트를 가져오는 요청
    axios.get(`/api/meditation/list/${memberIdx}`, {
      headers: {
        'Authorization': `Bearer ${accessToken}`,
      }
    })
      .then((response) => {
        setMeditationList(response.data.meditationList);
      })
      .catch((error) => {
        console.error("Error fetching meditation list:", error);
      });
  }, [memberIdx, accessToken]);

  // 3열 그리드로 나타내기 위한 함수
  const createGrid = () => {
    const grid = [];
    const itemsPerRow = 3;

    for (let i = 0; i < meditationList.length; i += itemsPerRow) {
      const row = meditationList.slice(i, i + itemsPerRow);
      const cols = row.map((item) => (
        <div key={item.index} className="col-lg-4">
          <MeditationThumbnail
            index={item.index}
            thumbnailImageUrl={item.thumbnailImageUrl}
          />
        </div>
      ));
      grid.push(
        <div key={i} className="row">
          {cols}
        </div>
      );
    }

    return grid;
  };

  return (
    <div className="meditation-grid">
      {createGrid()}
    </div>
  );
}

export default MeditationGrid;
