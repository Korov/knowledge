from kafka import KafkaConsumer
from loguru import logger

# 去掉所有的handler，即sys.stderr
logger.remove(handler_id=None)
logger.add('test.log', rotation="1 MB", format="{time:YYYY-MM-DD HH:mm:ss.SSS} {level} {name}:{function}:{line} {message}", level="INFO")

def consume_message():
    consumer = KafkaConsumer("flink_siem", bootstrap_servers="192.168.1.19:9092", group_id="test_group2", auto_offset_reset="earliest")
    for msg in consumer:
        recv = "%s:%d:%d: key=%s value=%s" % (msg.topic, msg.partition, msg.offset, msg.key.decode('utf-8'), msg.value.decode('utf-8'))
        logger.info(recv)


consume_message()