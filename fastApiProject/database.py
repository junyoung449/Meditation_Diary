from sqlalchemy import *
from sqlalchemy.orm import sessionmaker

import os
import dotenv

dotenv.load_dotenv()

DB_URL = 'mysql+pymysql://'+os.getenv("DB_USERNAME")+':'+os.getenv("DB_PASSWORD")+'@'+os.getenv("DB_HOST")+':'+os.getenv("DB_PORT")+'/'+os.getenv("DB_NAME")

class engineconn:
    def __init__(self):
        self.engine = create_engine(DB_URL, pool_recycle = 500)

    def sessionmaker(self):
        Session = sessionmaker(bind=self.engine)
        session = Session()
        return session

    def connection(self):
        conn = self.engine.connect()
        return conn