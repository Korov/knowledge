import urllib3

# 取消ssl认证
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
http = urllib3.PoolManager()
url = "https://exam.sac.net.cn/pages/registration/train-line-register!orderSearch.action"
data = {'filter_LES_ROWNUM': '100', 'filter_GTS_RNUM': '0', 'ORDER': 'ASC', 'sqlkey': 'registration',
        'sqlval': 'SEARCH_SAC_PUBLICITY'}
header = {'Content-Type': 'application/x-www-form-urlencoded',
          'Cookie': 'acw_tc=707c9fd815728778983107923e68836090a59cac21b47669f5a8bd8ccbb57c; BIGipServergrade=2907809984.20480.0000; JSESSIONID=zco21PbTGGNu96Wi7M7KqSQz2GBz9nBo5Pc4Nn0XY4yeOjMqAom5!-1710539372'}
r = http.request('POST', url, fields=data, headers=header)
