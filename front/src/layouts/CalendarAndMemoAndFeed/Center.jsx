import React from 'react';
import MyApp from 'layouts/Calendar/MyApp';
import Feed from 'layouts/Feed/Feed';
import '../../assets/css/Center.css';

function Center({ selectedTab }) {
  return (
    <div className="center">
      {selectedTab === 'Calendar' ? <MyApp /> : <Feed />}
    </div>
  );
}

export default Center;
