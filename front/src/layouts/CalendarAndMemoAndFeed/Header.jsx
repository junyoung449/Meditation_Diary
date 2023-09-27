import React from 'react';
import '../../assets/css/Header.css';

function Header({ selectedTab, setSelectedTab }) {
  return (
    <div className="header">
      <button onClick={() => setSelectedTab('Feed')} className={selectedTab === 'Feed' ? 'active' : ''}>Feed</button>
      <button onClick={() => setSelectedTab('Calendar')} className={selectedTab === 'Calendar' ? 'active' : ''}>Calendar</button>
    </div>
  );
}

export default Header;
