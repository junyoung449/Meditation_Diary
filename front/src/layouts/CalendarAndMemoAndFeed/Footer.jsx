import React, { useContext } from 'react';
import { ThemeContext } from 'contexts/ThemeContext';
import { useNavigate } from 'react-router-dom';
import '../../assets/css/Footer.css';

function Footer({ setSelectedTab }) {
  const navigate = useNavigate();
  const { theme, changeTheme } = useContext(ThemeContext);

  const toggleTheme = () => {
    // 테마 변경 함수 호출
    changeTheme(); // 이 함수를 호출하면 테마가 변경됩니다.
  };

  const handleCreateClick = () => {
    navigate("/crud");
  };

  const handleFeedClick = () => { // 오른쪽 버튼 클릭 핸들러를 추가합니다.
    setSelectedTab("Feed");
  };

  return (
    <div className="footer">
      <button className="footer-btn btn-left" onClick={toggleTheme}>
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