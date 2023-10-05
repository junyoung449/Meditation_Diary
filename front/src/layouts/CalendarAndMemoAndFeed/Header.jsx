import React, { useState, useEffect, useContext } from 'react';
import '../../assets/css/Header.css';
import axios from 'axios';
import { ThemeContext, themes } from 'contexts/ThemeContext';

function Header({ selectedTab, setSelectedTab }) {
  const [userName, setUserName] = useState(''); // 초기 상태는 빈 문자열
  const { theme, changeTheme } = useContext(ThemeContext);

  // 테마 변경에 반응하여 headerClass를 업데이트
  const headerClass = theme === themes.light ? 'light-theme' : 'dark-theme';

  useEffect(() => {
    const memberIdx = localStorage.getItem('memberIdx');
    const accessToken = localStorage.getItem('accessToken');

    // axios를 사용하여 서버로 요청을 보냅니다.
    axios.get(`https://j9b205.p.ssafy.io/api/member/${memberIdx}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`, // Bearer 토큰을 헤더에 추가
      },
    }).then((response) => {
      setUserName(response.data.name);
    })
  }, [theme]); // 테마 변경에 반응

  return (
    <div className="header">
      <div className="profile-container">
        <div className="profile-info">
          <span className={`username ${headerClass}`}>{userName}</span>
        </div>
        <img src="https://i.namu.wiki/i/bCmE_8XrnEYeEKlbme2ZS8rsG6dcB1vGD-UJtxvGncvXuYL9fiBqL8Fk_6cQ58EKJYTyyw9mA0LWK3yIaRYQow.webp" alt="Profile" className="profile-image" />
      </div>
      <div className="description">
        {selectedTab === 'Calendar' ? 
          <>
            Click date that you want.
            <br />
            You will hear the story of that moment
          </> : 
          selectedTab === 'Feed' ? 
          <>
            Click picture that you want.
            <br />
            You will hear the story of that moment
          </> : 
          ''}
      </div>
      <div className="navigation-buttons">
        <button onClick={() => setSelectedTab('Feed')} className={`${selectedTab === 'Feed' ? 'active' : ''} ${headerClass}`}>Feed</button>
        <button onClick={() => setSelectedTab('Calendar')} className={`${selectedTab === 'Calendar' ? 'active' : ''} ${headerClass}`}>Calendar</button>
      </div>
    </div>
  );
}

export default Header;
