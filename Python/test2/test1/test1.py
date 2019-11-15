from urllib.request import urlopen

from bs4 import BeautifulSoup

html = urlopen("http://www.pythonscraping.com/pages/page3.html")
bsObj = BeautifulSoup(html, "html5lib")
childs = bsObj.find("table", {"id": "giftList"}).children
for name in childs:
    # print(name.get_text())
    print(name)
