from pymongo.mongo_client import MongoClient

client = MongoClient('mongodb://admin:mongo@localhost:27017/admin')
db = client['backup']
collection = db['value-record']
count = 0
for url in collection.find({}):
    count = count + 1
    print("count:%s, key:%s, name:%s, timestamp:%s, message:%s", url.get("count"), url.get("key"), url.get("name"), url.get("timestamp"), url.get("message"), count)