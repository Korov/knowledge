import json

import pymysql

with open("C:\\Users\\Korov\\Desktop\\temporary\\python.txt", mode="r", encoding="utf-8") as file:
    text = file.read()

conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='root123', db='test', charset='utf8')
cur = conn.cursor()
cur.execute("USE test")

texts = json.loads(text)
for user in texts:
    userId = user["PRAN_UUID"]
    userName = user["RPI_NAME"]
    userSco = user["SCO_NAME"]
    userEco = user["ECO_NAME"]
    userAoi = user["AOI_NAME"]
    userPti = user["PTI_NAME"]
    countcer = user["COUNTCER"]
    countcx = user["COUNTCX"]
    rnum = user["RNUM"]
    sql = "insert into python(PRAN_UUID,RPI_NAME,SCO_NAME,ECO_NAME,AOI_NAME,PTI_NAME,COUNTCER,COUNTCX,RNUM) values ('{}','{}','{}','{}','{}','{}','{}','{}','{}')".format(
        userId, userName, userSco, userEco, userAoi, userPti, countcer, countcx, rnum)
    print(sql)
    cur.execute(sql)
conn.commit()
cur.close()
conn.close()
