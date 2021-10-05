import logging
from kafka import KafkaConsumer
from kafka.structs import TopicPartition
from loguru import logger

from util.kafka_consumer import consumer_msg, consumer_seek, create_consumer, display_all_topics, display_topic_partition

logging.basicConfig(level=logging.INFO, filename="test.log", filemode="a",
                    format="%(asctime)s.%(msecs)03d - %(threadName)s - %(name)s:%(funcName)s - %(levelname)s - %(filename)s:%(lineno)s - %(message)s",
                    datefmt="%Y-%m-%d %H:%M:%S")

logger = logging.getLogger(__name__)

kafka_consumer = create_consumer(bootstrap_servers="192.168.1.19:9092", group_id="test_group1")
display_topic_partition(kafka_consumer, ["flink_siem"])
consumer_seek(kafka_consumer, "flink_siem", 4, 6443748)
