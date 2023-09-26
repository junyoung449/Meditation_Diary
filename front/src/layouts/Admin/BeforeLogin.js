import React, { useContext, useEffect, useState } from "react";
import { BackgroundColorContext } from "contexts/BackgroundColorContext";
import FixedPlugin from "components/FixedPlugin/FixedPlugin.js";
import { Button } from "reactstrap";

function BeforeLogin() {
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

  const handleKakaoLogin = () => {
    const clientId = process.env.REACT_APP_CLIENT_ID;
    const redirectUri = process.env.REACT_APP_REDIRECT_URI;
    const apiUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code`;
    window.location.href = apiUrl;
  };

  const centerButtonStyle = {
    backgroundImage: 'none',
    color: isWhiteContent ? '#ffffff' : '#000000',
    fontSize: '24px',
    padding: '20px 40px',
    borderRadius: '50px',
    margin: 'auto',
    display: 'block',
  };

  return (
    <div className="wrapper" style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
    }}>
      <Button
        style={centerButtonStyle}
        className={`bg-${isWhiteContent ? 'dark' : 'white'}`}
        onClick={handleKakaoLogin}
      >
        Login with Kakao
      </Button>
      <FixedPlugin bgColor={bgColor} handleBgClick={changeColor} />
    </div>
  );
}

export default BeforeLogin;
