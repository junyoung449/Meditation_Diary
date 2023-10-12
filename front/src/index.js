/*!

=========================================================
* Black Dashboard React v1.2.2
=========================================================

* Product Page: https://www.creative-tim.com/product/black-dashboard-react
* Copyright 2023 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/black-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";

import AdminLayout from "layouts/Admin/Admin.js";
import RTLLayout from "layouts/RTL/RTL.js";

import "assets/scss/black-dashboard-react.scss";
import "assets/demo/demo.css";
import "assets/css/nucleo-icons.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

import ThemeContextWrapper from "./components/ThemeWrapper/ThemeWrapper";
import BackgroundColorWrapper from "./components/BackgroundColorWrapper/BackgroundColorWrapper";
import KakaoLogined from "./components/Kakao/Kakao";
import Create from "components/CRUD/Create";
import BeforeLogin from "layouts/Admin/BeforeLogin";
import Meditation from "components/CRUD/Meditation";

import Frame from "layouts/CalendarAndMemoAndFeed/Frame"
import MobileWelcome from "components/CRUD/Welcome";
import Waiting from "components/CRUD/Waiting";
import AudioUpload from "components/CRUD/AudioUpload";
const root = ReactDOM.createRoot(document.getElementById("root"));

document.body.classList.add('white-content');

root.render(
  <ThemeContextWrapper>
    <BackgroundColorWrapper>
      <BrowserRouter>
        <Routes>
          <Route path="/admin/*" element={<AdminLayout />} />
          <Route path="/rtl/*" element={<RTLLayout />} />
          <Route path="/kakao" element={<KakaoLogined />} />
          <Route path="/crud" element={<Create />} />
          <Route path="/beforelogin" element={<BeforeLogin />} />
          <Route path="/frame" element={<Frame/>}/>
          <Route path="/meditation/:index" element={<Meditation />} />
          <Route path="/welcome" element={<MobileWelcome/>}/>
          <Route path="/audio-upload" element={<AudioUpload/>}/>
          <Route path="/waiting" element={<Waiting/>}/>
          <Route
            path="*"
            element={<Navigate to="/frame" replace />}
          />
        </Routes>
      </BrowserRouter>
    </BackgroundColorWrapper>
  </ThemeContextWrapper>
);