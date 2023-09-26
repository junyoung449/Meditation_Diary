import React from "react";
import { useLocation } from "react-router-dom";
import axios from 'axios';

function KakaoLogined() {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const code = searchParams.get("code");

  // 'code'를 사용하여 필요한 작업 수행
  // 예: API 호출, 데이터 처리, 상태 업데이트 등

  axios.get('https://j9b205.p.ssafy.io/api/auth/kakao', {
    params: { code: code },
  })
    .then((response) => {
      // 요청이 성공했을 때의 처리
      console.log('카카오 로그인 성공:', response.data);
      localStorage.setItem('accessToken',response.data.accessToken)
      // localStorage.setItem('refreshToken',response.data.refreshToken)
      localStorage.setItem('islogined', true)
      localStorage.setItem('memberIdx', response.data.memberIdx)
      window.location.href = 'https://j9b205.p.ssafy.io/admin'
      // response.data를 이용하여 로그인 결과를 처리하세요.
    })
    .catch((error) => {
      // 요청이 실패했을 때의 처리
      console.error('카카오 로그인 실패:', error);
      // 오류 처리 로직을 작성하세요.
    });

  return (
    <div>
      <h1>로딩 중</h1>
      {/* <p>Code: {code}</p> */}
      {/* 이제 'code'를 이용하여 필요한 내용을 렌더링합니다. */}
    </div>
  );
}

export default KakaoLogined;
