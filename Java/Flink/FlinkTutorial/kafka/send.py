from util.kafka_producer import send_msg

for i in range(0, 100):
    send_msg("test", "key", "value_" + str(i))
