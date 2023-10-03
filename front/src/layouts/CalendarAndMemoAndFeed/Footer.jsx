import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../../assets/css/Footer.css';

function Footer({ setSelectedTab }) {
  const navigate = useNavigate();

  const handleCreateClick = () => {
    navigate("/crud");
  };

  const handleFeedClick = () => { // 오른쪽 버튼 클릭 핸들러를 추가합니다.
    setSelectedTab("Feed");
  };

  return (
    <div className="footer">
      <button className="footer-btn btn-left">
        <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/footer/footer-left.png"/>
      </button>
      <button className="footer-btn btn-center" onClick={handleCreateClick}>
        <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/footer/footer-center.png"/>
      </button>
      <button className="footer-btn btn-right" onClick={handleFeedClick}>
        <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/footer/footer-right.png"/>
      </button>
    </div>
  );
}

export default Footer;