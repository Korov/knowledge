from util import kafka_producer

import time

while True:
    time.sleep(2)
    timestamp = str(round(time.time() * 1000))
    print(timestamp)
    kafka_producer.send_msg(server="korov-linux.org:9092", topic="korov-demo", msg=timestamp)