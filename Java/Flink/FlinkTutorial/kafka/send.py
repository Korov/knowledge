import time

from util.kafka_producer import send_msg

for i in range(0, 100):
    send_msg(server="localhost:9092", topic="test", key="key", msg="value_" + str(i))

# 睡眠10s等待发送完成
time.sleep(20)
