# Retrieve HTML string from the URL
import json
from urllib.request import urlopen

url = "http://data.eastmoney.com/DataCenter_V3/jgdy/xx.ashx?pagesize=50&page=1&js=var%20PWQSnUVC&param=&sortRule=-1&sortType=0&rt=52"
data = {'filter_LES_ROWNUM': '100', 'filter_GTS_RNUM': '0', 'ORDER': 'ASC', 'sqlkey': 'registration',
        'sqlval': 'SEARCH_SAC_PUBLICITY'}
json_result = urlopen(url).read().decode('gb2312')
json_result = json_result.replace("var PWQSnUVC=", "")
result = json.loads(json_result)
texts = result["data"]
print(texts)
