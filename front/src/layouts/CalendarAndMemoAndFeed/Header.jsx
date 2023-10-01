import React, {useState, useEffect} from 'react';
import '../../assets/css/Header.css';
import axios from 'axios';

function Header({ selectedTab, setSelectedTab }) {
  const [userName, setUserName] = useState(''); // 초기 상태는 빈 문자열

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
  }, []); // 빈 의존성 배열은 컴포넌트가 마운트될 때만 실행됩니다.

  return (
    <div className="header">
      <div className="profile-container">
        <div className="profile-info">
          <span className="username">{userName}</span>
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
        <button onClick={() => setSelectedTab('Feed')} className={selectedTab === 'Feed' ? 'active' : ''}>Feed</button>
        <button onClick={() => setSelectedTab('Calendar')} className={selectedTab === 'Calendar' ? 'active' : ''}>Calendar</button>
      </div>
    </div>
  );
}

export default Header;
