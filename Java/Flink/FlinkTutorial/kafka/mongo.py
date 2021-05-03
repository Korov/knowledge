import pymongo;

client = pymongo.MongoClient("192.168.50.95", 27017)
db = client.admin
db.authenticate(name="admin", password="admin", source="admin", mechanism="SCRAM-SHA-256")
id = db.alert_test.insert_one({"key": "key", "value": "value"}).inserted_id
print(id)
