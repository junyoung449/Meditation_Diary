from sqlalchemy import Column, TEXT, BIGINT
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class MeditationMedia(Base):
    __tablename__ = "meditation_media"

    media_idx = Column(BIGINT, nullable=False, autoincrement=True, primary_key=True)
    meditation_idx = Column(BIGINT, nullable=False)
    image_url = Column(TEXT, nullable=False)
    audio_url = Column(TEXT, nullable=False)