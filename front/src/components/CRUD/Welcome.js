import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import "Welcome.css"

function MobileWelcome() {
  const history = useHistory();
  const [redirectTo, setRedirectTo] = useState(null);

  useEffect(() => {
    // 3초 후에 다른 페이지로 리다이렉트
    const redirectTimeout = setTimeout(() => {
      // 여기에 이동할 페이지의 경로를 설정하세요.
      // 예: setRedirectTo("/your-next-page");
      setRedirectTo("/crud");
    }, 3000);

    // 컴포넌트가 언마운트될 때 타임아웃을 정리합니다.
    return () => clearTimeout(redirectTimeout);
  }, []);

  useEffect(() => {
    // redirectTo가 설정되면 페이지를 리다이렉트합니다.
    if (redirectTo) {
      history.push(redirectTo);
    }
  }, [redirectTo, history]);

  return (
    <div className="mobile-welcome">
        <p>사진을 업로드하고</p>
        <p>첫 명상에 빠져보세요</p>
    </div>
  );
}

export default MobileWelcome;
