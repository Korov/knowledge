from pymongo.mongo_client import MongoClient
import pymysql.cursors
import psycopg2
import time

pg_connection = psycopg2.connect(dbname="backup",
                                 user="postgres",
                                 password="zl7636012086",
                                 host="korov.myqnapcloud.cn",
                                 port="5432")

mysql_connection = pymysql.connect(host='korov.myqnapcloud.cn',
                                   user='root',
                                   password='zl7636012086',
                                   database='backup',
                                   cursorclass=pymysql.cursors.DictCursor)

client = MongoClient('mongodb://admin:zl7636012086@korov.myqnapcloud.cn:27017/admin')
db = client['backup']
collection = db['value-record']
count = 0
batch_count = 10000
urls = []
for url in collection.find({}):
    count = count + 1

    line = []
    line.append(url.get("count"))
    line.append(url.get("key"))
    line.append(url.get("message"))
    line.append(url.get("name"))
    line.append(url.get("timestamp"))

    urls.append(line)
    if count % batch_count == 0:
        with mysql_connection.cursor() as cursor:
        # Create a new record
            sql = "insert into value_record(count, value_key, message, value_name, value_time) VALUES (%s,%s,%s,%s,%s);"
            cursor.executemany(sql, urls)

        # connection is not autocommit by default. So you must commit to save
        # your changes.
        mysql_connection.commit()

        with pg_connection.cursor() as cursor:
        # Create a new record
            sql = 'insert into "public"."value-record"(count, value_key, message, value_name, value_time) VALUES (%s,%s,%s,%s,%s);'
            cursor.executemany(sql, urls)

        # connection is not autocommit by default. So you must commit to save
        # your changes.
        pg_connection.commit()

        print("insert count:%s, time:%s" %
              (batch_count, time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())))
        urls.clear()
