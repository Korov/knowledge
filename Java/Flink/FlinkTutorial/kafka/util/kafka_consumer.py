from kafka import KafkaConsumer


def consumer_msg(topic, server, group_id="test", auto_offset_reset="earliest"):
    if (not topic is None) and (not server is None):
        consumer = KafkaConsumer(topic, bootstrap_servers=server, group_id=group_id,
                                 auto_offset_reset=auto_offset_reset)

    index = 0
    for msg in consumer:
        index = index + 1
        print(index)
        recv = "%s:%d:%d: key=%s value=%s" % (msg.topic, msg.partition, msg.offset, msg.key, msg.value.decode('utf-8'))
        print(recv)
