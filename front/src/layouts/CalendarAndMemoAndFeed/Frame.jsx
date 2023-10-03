import React, { useState, useContext, useEffect } from 'react';
import Header from './Header';
import Center from './Center';
import Footer from './Footer';
import '../../assets/css/Frame.css'
import { BackgroundColorContext } from "../../contexts/BackgroundColorContext";
import FixedPlugin from "../../components/FixedPlugin/FixedPlugin";

function Frame() {
  const [selectedTab, setSelectedTab] = useState('Calendar'); // 기본적으로 Calendar 탭 선택
  const { color: bgColor, changeColor } = useContext(BackgroundColorContext);
  const [isWhiteContent, setIsWhiteContent] = useState(false);

  useEffect(() => {
    // body 요소의 클래스 변화를 감지하는 Mutation Observer 생성
    const observer = new MutationObserver((mutationsList) => {
      for (const mutation of mutationsList) {
        if (mutation.type === "attributes" && mutation.attributeName === "class") {
          // body 클래스가 변경될 때마다 실행되는 부분
          const bodyHasWhiteContent = document.body.classList.contains("white-content");
          setIsWhiteContent(bodyHasWhiteContent);
        }
      }
    });

    // Mutation Observer를 body 요소에 연결
    observer.observe(document.body, { attributes: true });

    // 컴포넌트가 언마운트될 때 Observer 해제
    return () => {
      observer.disconnect();
    };
  }, []); 

  return (
    <div className={`Frame ${bgColor === 'white' ? 'dark-theme' : 'light-theme'}`}>
      <Header selectedTab={selectedTab} setSelectedTab={setSelectedTab} />
      <Center selectedTab={selectedTab} />
      <Footer setSelectedTab={setSelectedTab} />
      <FixedPlugin bgColor={bgColor} handleBgClick={changeColor} />
    </div>
  );
}

export default Frame;