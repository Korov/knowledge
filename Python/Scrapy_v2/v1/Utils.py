from urllib.request import urlopen

from pandas.io.json import json


def getJson(url):
    result_json = urlopen("http://www.xcf.cn/s/MainPage/mainPageData?current=1&pageSize=10&type=3")
    result = json.loads(result_json.read())
    return result
