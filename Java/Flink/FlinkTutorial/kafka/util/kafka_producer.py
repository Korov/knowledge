from kafka import KafkaProducer

producer = KafkaProducer(bootstrap_servers = "korov-linux.org:9092")

def on_send_success(*args, **kwargs):
    print("send success")
    return args


def on_send_error(*args, **kwargs):
    print("send failed")
    return args


def send_msg(topic, key, msg):
    key = str(key).encode('utf-8')
    msg = str(msg).encode('utf-8')
    future = producer.send(topic, key=key, value=msg).add_callback(on_send_success).add_errback(on_send_error)
    result = future.get(timeout=60)
    print(result)
