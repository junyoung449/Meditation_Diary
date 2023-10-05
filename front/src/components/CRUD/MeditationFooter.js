function MeditationFooter({ setSelectedTab, isPlaying, onPlayClick, onPauseClick }) {
  const navigate = useNavigate();
  const { theme, changeTheme } = useContext(ThemeContext);

  const toggleTheme = () => {
    // 현재 테마가 "light"이면 "dark"로, 그 반대면 "light"로 변경
    const newTheme = theme === themes.light ? themes.dark : themes.light;
    console.log(newTheme,'으로 배경색 변경')
    changeTheme(newTheme);
  };

  const handleFeedClick = () => {
    setSelectedTab("Feed");
    navigate("/frame");
  };

  return (
    <div className="footer">
      <button className="footer-btn btn-left" onClick={toggleTheme}>
        <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/footer/footer-left.png"/>
      </button>
      {isPlaying ? (
        <button className="footer-btn btn-center" onClick={onPauseClick}>
          <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/image/pause.png" alt="일시정지" />
        </button>
      ) : (
        <button className="footer-btn btn-center" onClick={onPlayClick}>
          <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/image/play.png" alt="재생" />
        </button>
      )}
      <button className="footer-btn btn-right" onClick={handleFeedClick}>
        <img src="https://s3.ap-northeast-2.amazonaws.com/b205.s3test.bucket/footer/footer-right.png"/>
      </button>
    </div>
  );
}
