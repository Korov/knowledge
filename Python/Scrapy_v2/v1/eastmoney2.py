import json
from urllib.request import urlopen

import pymysql
from pymysql import ProgrammingError

i = 1


def insert(url, charset):
    json_result = urlopen(url).read().decode(charset)
    json_result = json_result.replace("var PWQSnUVC=", "")
    result = json.loads(json_result)
    texts = result["data"]

    conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='root123', db='test', charset='utf8')
    cur = conn.cursor()
    cur.execute("USE test")

    for text in texts:
        ChangePercent = text["ChangePercent"]
        Close = text["Close"]
        CompanyCode = text["CompanyCode"]
        CompanyName = text["CompanyName"]
        OrgCode = text["OrgCode"]
        OrgName = text["OrgName"]
        OrgSum = text["OrgSum"]
        SCode = text["SCode"]
        SName = text["SName"]
        NoticeDate = text["NoticeDate"]
        StartDate = text["StartDate"]
        EndDate = text["EndDate"]
        Place = text["Place"]
        Description = text["Description"]
        Orgtype = text["Orgtype"]
        OrgtypeName = text["OrgtypeName"]
        Personnel = text["Personnel"]
        Licostaff = text["Licostaff"]
        Maincontent = text["Maincontent"]
        sql = "INSERT INTO eastmoney VALUES('{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}')".format(
            ChangePercent, Close, CompanyCode, CompanyName, OrgCode, OrgName, OrgSum, SCode, SName, NoticeDate,
            StartDate,
            EndDate, Place, Description, Orgtype, OrgtypeName, Personnel, Licostaff, Maincontent)
        cur.execute(sql)
    conn.commit()
    cur.close()
    conn.close()


# url = "http://data.eastmoney.com/DataCenter_V3/jgdy/xx.ashx?pagesize=50&page={}&js=var%20PWQSnUVC&param=&sortRule=-1&sortType=0&rt=52".format(
#     3)
# insert(url)

error = set();
i = 6104
while i <= 6500:
    # time.sleep(1)
    url = "http://data.eastmoney.com/DataCenter_V3/jgdy/xx.ashx?pagesize=50&page={}&js=var%20PWQSnUVC&param=&sortRule=-1&sortType=0&rt=52".format(
        i)
    i = i + 1
    try:
        insert(url, 'gbk')
    except (UnicodeDecodeError, ProgrammingError):
        error.add(str(i))
        print("error:" + "".join(error))
    print(i)
    print("error:" + ",".join(error))
