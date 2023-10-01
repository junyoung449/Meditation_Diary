import React from 'react';
import MeditationCalendar from 'layouts/Calendar/MeditationCalendar';
import Feed from 'layouts/Feed/Feed';
import '../../assets/css/Center.css';
// import Calendar from 'react-calendar';

function Center({ selectedTab }) {
  return (
    <div className="center">
      {selectedTab === 'Calendar' ? <MeditationCalendar /> : <Feed />}
    </div>
  );
}

export default Center;
