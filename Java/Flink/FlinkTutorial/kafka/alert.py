from kafka import KafkaConsumer
import pymongo
import time

client = pymongo.MongoClient("192.168.50.95", 27017)
db = client.admin
db.authenticate(name="admin", password="admin", source="admin", mechanism="SCRAM-SHA-256")

consumer = KafkaConsumer("flink_siem", bootstrap_servers="192.168.1.19:9092", group_id="siem_test",
                         auto_offset_reset="earliest")
docs = []
for msg in consumer:
    if len(docs) == 1000:
        db.alert.insert_many(docs)
        print("%s :insert count %s" % (time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()), len(docs)))
        docs = []
    else:
        docs.append({"key": msg.key.decode('utf-8'), "value": msg.value.decode('utf-8')})
