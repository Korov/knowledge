from kafka import KafkaConsumer

def consumer_msg(topic, server):
    if (not topic is None) and (not server is None):
        consumer = KafkaConsumer(topic, bootstrap_servers=server)

    index = 0
    for msg in consumer:
        index = index + 1
        print(index)
        recv = "%s:%d:%d: key=%s value=%s" % (msg.topic, msg.partition, msg.offset, msg.key, msg.value.decode('utf-8'))
        print(recv)
