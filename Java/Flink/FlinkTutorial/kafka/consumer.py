from util.kafka_consumer import create_consumer, display_topic_partition, consumer_seek, display_all_topics

kafka_consumer = create_consumer(bootstrap_servers="192.168.50.114:9092", group_id="test_group1")
display_all_topics(kafka_consumer)
display_topic_partition(kafka_consumer, ["flink_siem"])
# consumer_seek(kafka_consumer, "flink_siem", 0, 147325460)
