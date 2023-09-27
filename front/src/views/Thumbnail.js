import React from "react";
import { Link } from "react-router-dom";

function MeditationThumbnail({ index, thumbnailImageUrl }) {
  return (
    <div className="thumbnail">
      <Link to={`/meditation/${index}`}>
        <img src={thumbnailImageUrl} alt={`Thumbnail ${index}`} />
      </Link>
    </div>
  );
}

export default MeditationThumbnail;
