from pymongo.mongo_client import MongoClient
import pymysql.cursors
import psycopg2

pg_connection = psycopg2.connect(dbname="backup", user="minions", password="postgres", host="localhost", port="5432")

mysql_connection = pymysql.connect(host='localhost',
                             user='test',
                             password='test',
                             database='backup',
                             cursorclass=pymysql.cursors.DictCursor)

client = MongoClient('mongodb://admin:mongo@localhost:27017/admin')
db = client['backup']
collection = db['value-record']
count = 0
for url in collection.find({}):
    count = count + 1
    with mysql_connection.cursor() as cursor:
        # Create a new record
        sql = "insert into value_record(count, value_key, message, value_name, value_time) VALUES (%s,%s,%s,%s,%s);"
        cursor.execute(sql, (url.get("count"),url.get("key"),url.get("message"),url.get("name"),url.get("timestamp")))

    # connection is not autocommit by default. So you must commit to save
    # your changes.
    mysql_connection.commit()

    with pg_connection.cursor() as cursor:
        # Create a new record
        sql = 'insert into "public"."value-record"(count, value_key, message, value_name, value_time) VALUES (%s,%s,%s,%s,%s);'
        cursor.execute(sql, (url.get("count"),url.get("key"),url.get("message"),url.get("name"),url.get("timestamp")))

    # connection is not autocommit by default. So you must commit to save
    # your changes.
    pg_connection.commit()
    print("count:%s", count )