import os
import sys
from loguru import logger

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

log_file_path = os.path.join(BASE_DIR, 'logs/info.log')
err_log_file_path = os.path.join(BASE_DIR, 'logs/err.log')

logger.add(sys.stderr, format="{time} {level} {message}", filter="my_module", level="INFO")
# logger.add(s)
logger.add(log_file_path, rotation="500 MB", encoding='utf-8')  # Automatically rotate too big file
logger.add(err_log_file_path, rotation="500 MB", encoding='utf-8', level='ERROR')  # Automatically rotate too big file

log = logger