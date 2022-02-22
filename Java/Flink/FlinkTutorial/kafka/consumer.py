from util.kafka_consumer import create_consumer, display_topic_partition, consumer_seek, display_all_topics

kafka_consumer = create_consumer(bootstrap_servers="localhost:9092", group_id="monitor")
# display_all_topics(kafka_consumer)
display_topic_partition(kafka_consumer, ["alert_box"])
# consumer_seek(kafka_consumer, "raw_message", 0, 1269806324)
