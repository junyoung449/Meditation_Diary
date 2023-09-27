import React, { useState } from 'react';
import Header from './Header';
import Center from './Center';
import Footer from './Footer';
import '../../assets/css/Frame.css'

function Frame() {
  const [selectedTab, setSelectedTab] = useState('Calendar'); // 기본적으로 Calendar 탭 선택

  return (
    <div className="Frame">
      <Header selectedTab={selectedTab} setSelectedTab={setSelectedTab} />
      <Center selectedTab={selectedTab} />
      <Footer />
    </div>
  );
}

export default Frame;