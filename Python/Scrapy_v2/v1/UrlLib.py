# post请求提交用户信息到服务器

import ssl
import urllib.parse
import urllib.request

import pymysql
from pandas.io.json import json

context = ssl._create_unverified_context()

# CGI(Common Gateway Interface)是HTTP服务器运行的程序
# 通过Internet把用户请求送到服务器
# 服务器接收用户请求并交给CGI程序处理
# CGI程序把处理结果传送给服务器
# 服务器把结果送回到用户

url = "https://exam.sac.net.cn/pages/registration/train-line-register!orderSearch.action"
values = {'filter_LES_ROWNUM': '900', 'filter_GTS_RNUM': '0', 'ORDER': 'ASC', 'sqlkey': 'registration',
          'sqlval': 'SEARCH_SAC_OTHER_PUBLICITY'}
data = urllib.parse.urlencode(values).encode('utf-8')

req = urllib.request.Request(url, data=data)

response = urllib.request.urlopen(req, context=context)
texts = json.loads(str(response.read(), 'utf-8'))

conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='root123', db='test', charset='utf8')
cur = conn.cursor()
cur.execute("USE test")

for user in texts:
    userId = user["PRAN_UUID"]
    userName = user["RPI_NAME"]
    userSco = user["SCO_NAME"]
    userEco = ''
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
