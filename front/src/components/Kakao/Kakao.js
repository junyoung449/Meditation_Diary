import React from "react";
import { useParams } from "react-router-dom";

function KakaoLogined() {
  const { code } = useParams(); // 경로 파라미터인 'code'를 가져옵니다.

  // 'code'를 사용하여 필요한 작업 수행
  // 예: API 호출, 데이터 처리, 상태 업데이트 등

  return (
    <div>
      <h1>Your Component</h1>
      <p>Code: {code}</p>
      {/* 이제 'code'를 이용하여 필요한 내용을 렌더링합니다. */}
    </div>
  );
}

export default KakaoLogined;
