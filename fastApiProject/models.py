from sqlalchemy import Column, TEXT, INT, BIGINT
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class MeditationAudio(Base):
    __tablename__ = "meditation_audio"

    audio_idx = Column(BIGINT, nullable=False, autoincrement=True, primary_key=True)
    meditation_idx = Column(BIGINT, nullable=False)
    audio_url = Column(TEXT, nullable=False)