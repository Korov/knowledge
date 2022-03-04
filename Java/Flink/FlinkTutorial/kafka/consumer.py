import util.kafka_consumer

kafka_consumer = util.kafka_consumer.create_consumer(bootstrap_servers="192.168.205.135:30092", group_id="monitor")
util.kafka_consumer.display_all_topics(kafka_consumer)
# util.kafka_consumer.display_topic_partition(kafka_consumer, ["raw_message"])
# util.kafka_consumer.display_topic_consumers(kafka_consumer, ["raw_message"])
# consumer_seek(kafka_consumer, "raw_message", 0, 1269806324)
