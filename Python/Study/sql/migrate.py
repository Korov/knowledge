import logging
import time

import psycopg2
import pymysql.cursors
from pymongo.mongo_client import MongoClient

logging.basicConfig(level=logging.INFO, filename="migrate.log", filemode="w",
                    format="%(asctime)s - %(name)s - %(levelname)-9s - %(filename)-8s : %(lineno)s line - %(message)s",
                    datefmt="%Y-%m-%d %H:%M:%S")

pg_connection = psycopg2.connect(dbname="backup",
                                 user="postgres",
                                 password="zl7636012086",
                                 host="nas.korov.org",
                                 port="5432")

mysql_connection = pymysql.connect(host='nas.korov.org',
                                   user='root',
                                   password='zl7636012086',
                                   database='backup',
                                   cursorclass=pymysql.cursors.DictCursor)

client = MongoClient('mongodb://admin:zl7636012086@nas.korov.org:27017/admin')
db = client['backup']
collection = db['value-record']
count = 0
all_count = 0
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
            sql = "insert into value_record_temp(count, value_key, message, value_name, value_time) VALUES (%s,%s,%s,%s,%s);"
            cursor.executemany(sql, urls)

        mysql_connection.commit()

        with pg_connection.cursor() as cursor:
            sql = 'insert into "public"."value_record_temp"(count, value_key, message, value_name, value_time) VALUES (%s,%s,%s,%s,%s);'
            cursor.executemany(sql, urls)

        pg_connection.commit()

        all_count = all_count + batch_count
        logging.info("insert count:%s, all count:%s, time:%s",
                     batch_count, all_count, time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))
        urls.clear()
