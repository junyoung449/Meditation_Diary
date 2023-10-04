import React, { useState, useEffect } from "react";
import axios from "axios";
import MeditationThumbnail from "../../views/Thumbnail";
import "../../assets/css/Feed.css"

import {
    Row,
    Col,
  } from "reactstrap";


function Feed() {
    const [meditationList, setMeditationList] = useState([]);

  useEffect(() => {
    const memberIdx = parseInt(localStorage.getItem('memberIdx'),10);
    const accessToken = localStorage.getItem('accessToken');
    // 게시글 리스트를 가져오는 요청
    axios.get(`https://j9b205.p.ssafy.io/api/meditation/list/${memberIdx}`, {
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
  }, []);

  // 3열 그리드로 나타내기 위한 함수
  const createGrid = () => {
    const grid = [];
    const itemsPerRow = 3;

    for (let i = 0; i < meditationList.length; i += itemsPerRow) {
      const row = meditationList.slice(i, i + itemsPerRow);
      const cols = row.map((item) => (
        <Col xs='4'>
          <MeditationThumbnail
            index={item.meditationIdx}
            thumbnailImageUrl={item.imageUrl}
          />
        </Col>
      ));
      grid.push(
        <Row>
          {cols}
        </Row>
      );
    }

    return grid;
  };

  return (
    <div className="content">
      {createGrid()}
    </div>
  );
}

export default Feed;