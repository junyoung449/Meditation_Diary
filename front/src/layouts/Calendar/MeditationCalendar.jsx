import React, {useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import Calendar from 'react-calendar';
import '../../assets/css/Calendar.css';
import moment from 'moment';
import axios from 'axios'; // axios 라이브러리를 추가로 가져옵니다.

function MeditationCalendar() {
    const [accessToken, setAccessToken] = useState(() => {
        if (typeof window !== "undefined") {
          const token = localStorage.getItem("accessToken");
          if (token !== null) {
            return token;
          }
        }
    });

    const [value, onChange] = useState(new Date());
    const [calendarList, setCalendarList] = useState([]);
    const [memoMarks, setMemoMarks] = useState([]);
    const [meditationMarks, setMeditationMarks] = useState([]);
    const [meditationDates, setMeditationDates] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const memberIdx = localStorage.getItem('memberIdx');

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
            .filter((item) => item.meditationIdx !== null)
            .map((item) => {
                const month = String(item.month).padStart(2, '0');
                const day = String(item.day).padStart(2, '0');
                return {
                    date: `${day}-${month}-${item.year}`,
                    meditationIdx: item.meditationIdx
                };
            });

            setMemoMarks(newMemoMarks);
            setMeditationMarks(newMeditationMarks);
            const newMeditationDates = newMeditationMarks.map(mark => mark.date);
            setMeditationDates(newMeditationDates);

            console.log(newMemoMarks);
            console.log(newMeditationMarks);
        })
          .catch((error) => {
            console.error('데이터를 가져오는 중 오류 발생:', error);
          });
    }, []);
    
    const handleDayClick = (date) => {
        const dateStr = moment(date).format("DD-MM-YYYY");

        if (meditationDates.includes(dateStr)) {
            const matchingMark = meditationMarks.find(mark => mark.date === dateStr);

            if (matchingMark) {
                console.log(matchingMark.meditationIdx);

                axios.get(`https://j9b205.p.ssafy.io/api/meditation/${matchingMark.meditationIdx}`, {
                headers : {
                    'Authorization' : `Bearer ${accessToken}`,
                },
                }).then((response) => {
                    console.log('meditationData :', response.data);
                    navigate(`/meditation/${meditationMarks.meditationIdx}`, { state: { meditationData: response.data } });
                })
                .catch((error) => {
                    console.error("Error fetching meditation data:", error);
                });
            }
        }
    };

    return (
        <div>
            <Calendar
                onChange={onChange}
                onClickDay={handleDayClick}
                value={value}
                locale="en-US" firstDayOfWeek={0}
                tileClassName={({ date, view }) => {
                    const dateStr = moment(date).format("DD-MM-YYYY");
                    if (memoMarks.includes(dateStr)) {
                        return "memo-highlight"; // 메모 표시 스타일
                    } else if (meditationDates.includes(dateStr)) {
                        return "meditation-highlight"; // 명상 표시 스타일
                    }
                    return ""; // 조건에 맞지 않는 경우 빈 문자열 반환
                }}
            />
        </div>
    );
}

export default MeditationCalendar;