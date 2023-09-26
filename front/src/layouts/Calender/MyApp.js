import React, {useState, useEffect} from 'react';
import Calendar from 'react-calendar';
import '/Users/leehyungseok/Desktop/specialization/Front/S09P22B205/front/src/assets/css/Calendar.css';
import moment from 'moment';
import axios from 'axios'; // axios 라이브러리를 추가로 가져옵니다.

function MyApp() {
    const [value, onChange] = useState(new Date());
    const [calendarList, setCalendarList] = useState([]);
    const [memoMarks, setMemoMarks] = useState([]);
    const [meditationMarks, setMeditationMarks] = useState([]);
    
    useEffect(() => {
        const memberIdx = localStorage.getItem('memberIdx');
        const accessToken = localStorage.getItem('accessToken'); 

        // axios를 사용하여 서버로 요청을 보냅니다.
        axios.get(`https://j9b205.p.ssafy.io/api/calendar/${memberIdx}`, {
            headers: {
                Authorization: `Bearer ${accessToken}`, // Bearer 토큰을 헤더에 추가
            },
        }).then((response) => {
            const calendarList = response.data.calendarList

           const newMemoMarks = calendarList
            .filter((item) => item.memoIdx !== null).map((item) => {
                const month = String(item.month).padStart(2, '0');
                return `${item.day}-${month}-${item.year}`;
            });

            const newMeditationMarks = calendarList
                .filter((item) => item.meditationIdx !== null).map((item) => {
                    const month = String(item.month).padStart(2, '0');
                    return `${item.day}-${month}-${item.year}`;
            });

            setMemoMarks(newMemoMarks);
            setMeditationMarks(newMeditationMarks);

            console.log(newMemoMarks);
            console.log(newMeditationMarks);
        })
          .catch((error) => {
            console.error('데이터를 가져오는 중 오류 발생:', error);
          });
    }, []);

    return (
        <div>
            <Calendar
                onChange={onChange}
                value={value}
                locale="en-EN"
                tileClassName={({ date, view }) => {
                    const dateStr = moment(date).format("DD-MM-YYYY");
                    if (memoMarks.includes(dateStr)) {
                        return "memo-highlight"; // 메모 표시 스타일
                    } else if (meditationMarks.includes(dateStr)) {
                        return "meditation-highlight"; // 명상 표시 스타일
                    }
                    return ""; // 조건에 맞지 않는 경우 빈 문자열 반환
                }}
            />
        </div>
    );
}

export default MyApp;