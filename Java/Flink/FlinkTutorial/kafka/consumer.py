from util.kafka_consumer import consumer_msg

consumer_msg(topic="korov-demo", server="localhost:9092", group_id="siem_test1")