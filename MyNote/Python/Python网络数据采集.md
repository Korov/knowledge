理论上，网络数据采集是一种通过多种手段收集网络数据的方式，不光是通过与API 交互（或者直接与浏览器交互）的方式。最常用的方法是写一个自动化程序向网络服务器请求数据（通常是用HTML 表单或其他网页文件），然后对数据进行解析，提取需要的信息。

实践中，网络数据采集涉及非常广泛的编程技术和手段，比如数据分析、信息安全等。本书将在第一部分介绍关于网络数据采集和网络爬行（crawling）的基础知识，一些高级主题放在第二部分介绍。

# 第一部分 创建爬虫

这部分内容重点介绍网络数据采集的基本原理：如何用Python 从网络服务器请求信息，如何对服务器的响应进行基本处理，以及如何以自动化手段与网站进行交互。最终，你将轻松游弋于网络空间，创建出具有域名切换、信息收集以及信息存储功能的爬虫。

这部分内容涵盖了一般人（也包括技术达人）在思考“网络爬虫”时通常的想法：

- 通过网站域名获取HTML 数据
- 根据目标信息解析数据
-  存储目标信息
- 如果有必要，移动到另一个网页重复这个过程

## 第一章 初见网络爬虫

### 1.1 网络连接

urllib：Python的标准库，包含了从网络请求数据，处理cookie，甚至改变像请求头和用户代理这些元数据的函数。

urlopen：用来打开并读取一个从网络获取的远程对象

### 1.2 BeautifulSoup

它通过点位HTML标签来格式化和组织复杂的网络信息，用简单易用的Python对象为我们XML结构信息。

## 第二章 复杂HTML解析

解析复杂的HTML网页时需要使用技巧将网页上那些不需要的信息敲掉。

### 2.1 不是一直都要用锤子

在解析复杂的HTML页面时需要避免的问题。

假如你已经确定了目标内容，可能是采集一个名字、一组统计数据，或者一段文字。你的目标内容可能隐藏在一个HTML“烂泥堆”的第20 层标签里，带有许多没用的标签或HTML 属性。假如你不经考虑地直接写出下面这样一行代码来抽取内容：

```Python
bsObj.findAll("table")[4].findAll("tr")[2].find("td").findAll("div")[1].find("a")
```

虽然也可以达到目标，但这样看起来并不是很好。除了代码欠缺美感之外，还有一个问题是，当网站管理员对网站稍作修改之后，这行代码就会失效，甚至可能会毁掉整个网络爬虫。那么你应该怎么做呢？

- 寻找“打印此页”的链接，或者看看网站有没有HTML样式更友好的移动版
- 寻找隐藏在JavaScript文件里的信息。要实现这一点，你可能需要查看网页加载的JavaScript文件。我曾经要把一个网站上的街道地址（以经度和纬度呈现的）整理成格式整洁的数组时，查看过内嵌谷歌地图的JavaScript文件，里面有每个地址的标记点。
- 虽然网页标题经常会用到，但是这个信息也许可以从网页的URL链接里获取。
- 如果你要找的信息只存在于一个网站上，别处没有，那你确实是运气不佳。如果不只限于这个网站，那么你可以找找其他数据源。有没有其他网站也显示了同样的数据？网站上显示的数据是不是从其他网站上抓取后攒出来的？

**尤其是在面对埋藏很深或格式不友好的数据时，千万不要不经思考就写代码，一定要三思而后行**。如果你确定自己不能另辟蹊径，那么本章后面的内容就是为你准备的。

### 2.2 再端一碗BeautifulSoup

在这一节，我们将介绍通过属性查找标签的方法，标签组的使用，以及标签解析树的导航过程。

网络爬虫可以通过class属性的值，轻松地区分出两种不同的标签。例如，它们可以用BeautifulSoup抓取网页上所有的红色文字，而绿色文字一个都不抓。

#### 2.2.1 BeautifulSoup的find和findAll()

这两个函数非常相似，BeautifulSoup文档里两者的定义就是这样：

```Python
findAll(tag, attributes, recursive, text, limit, keywords)
find(tag, attributes, recursive, text, keywords)
```

标签参数tag前面已经介绍过——你可以传一个标签的名称或多个标签名称组成的Python列表做标签参数。例如，下面的代码将返回一个包含HTML文档中所有标题标签的列表：

```Python
.findAll({"h1","h2","h3","h4","h5","h6"})
```

属性参数attributes是用一个Python字典封装一个标签的若干属性和对应的属性值。例如，下面这个函数会返回HTML文档里红色与绿色两种颜色的span标签：

```Python
.findAll("span", {"class":{"green", "red"}})
```

递归参数recursive是一个布尔变量。你想抓取HTML文档标签结构里多少层的信息？如果recursive设置为True，findAll就会根据你的要求去查找标签参数的所有子标签，以及子标签的子标签。如果recursive设置为False，findAll就只查找文档的一级标签。findAll默认是支持递归查找的（recursive默认值是True）；一般情况下这个参数不需要设置，除非你真正了解自己需要哪些信息，而且抓取速度非常重要，那时你可以设置递归参数。

文本参数text有点不同，它是用标签的文本内容去匹配，而不是用标签的属性。假如我们想查找前面网页中包含“theprince”内容的标签数量，我们可以把之前的findAll方法换成下面的代码：

```Python
nameList = bsObj.findAll(text="the prince")
print(len(nameList))
// 结果为7
```

范围限制参数limit，显然只用于findAll方法。find其实等价于findAll的limit等于1时的情形。如果你只对网页中获取的前x项结果感兴趣，就可以设置它。但是要注意，这个参数设置之后，获得的前几项结果是按照网页上的顺序排序的，未必是你想要的那前几项。

还有一个关键词参数keyword，可以让你选择那些具有指定属性的标签。例如：

```Python
allText = bsObj.findAll(id="text")
print(allText[0].get_text())
```

#### 2.2.2 其他BeautifulSoup对象

- BeautifulSoup对象
- 标签Tag对象
- NavigableString对象：用来表示标签里的文字，不是标签
- Comment对象：用来查找HTML文档的注释标签，<!-- like this -->

#### 2.2.3 导航树

**1.处理字标签和其代标签**

一般情况下，BeautifulSoup函数总是处理当前标签的后代标签。例如，bsObj.body.h1选择了body标签后代里的第一个h1标签，不会去找body外面的标签。

类似地，bsObj.div.findAll("img")会找出文档中第一个div标签，然后获取这个div后代里所有的img标签列表。

如果你只想找出子标签，可以用.children标签。

**2.处理兄弟标签**

next_siblings() 函数，获取当前标签的之后的兄弟标签，不包括自己。

previous_siblings函数，获取当前标签的之前的兄弟标签，不包括自己。

还有next_sibling和previous_sibling函数，与next_siblings和previous_siblings的作用类似，只是它们返回的是单个标签，而不是一组标签。

**3.父标签处理**

parent和parents。

### 2.3 正则表达式和BeautifulSoup

```python
images = bsObj.findAll("img",{"src":re.compile("\.\.\/img\/gifts/img.*\.jpg")})
```

通过re来撇皮正则表达式。

### 2.4 获取属性

对于一个标签对象，可以用下面的代码获取它的全部属性：

myTag.attrs

要注意这行代码返回的是一个Python字典对象，可以获取和操作这些属性。比如要获取图片的资源位置src，可以用下面这行代码：

myImgTag.attrs["src"]

### 2.5 Lambda表达式

BeautifulSoup允许我们把特定函数类型当作findAll函数的参数。唯一的限制条件是这些函数必须把一个标签作为参数且返回结果是布尔类型。BeautifulSoup用这个函数来评估它遇到的每个标签对象，最后把评估结果为“真”的标签保留，把其他标签剔除。

例如，下面的代码就是获取有两个属性的标签：

```Python
soup.findAll(lambda tag: len(tag.attrs) == 2)
```

### 2.6 超越BeautifulSoup

- lxml：可以用来解析HTML和XML文档，以非常底层的实现而闻名于世。
- HTML parser：Python自带的解析库。

## 第三章 开始采集

### 3.1 用Scrapy采集

Scrapy就是一个帮你大幅降低网页链接查找和识别工作复杂度的Python库，它可以让你轻松地采集一个或多个域名的信息。

Scrapy用Item对象决定要从它浏览的页面中提取哪些信息。Scrapy支持用不同的输出格式来保存这些信息，比如CSV、JSON或XML。

当然你也可以自定义Item对象，把结果写入你需要的一个文件或数据库中，只要在爬虫的parse部分增加相应的代码即可。

Scrapy是处理网路数据采集相关问题的利器。它可以自动收集所有URL，然后和指定的规则进行比较；确保所有的URL是唯一的；根据需求对相关的URL进行标准化；以及到更深层的页面中递归查找。

## 第四章 使用API

### 4.1 API通用规则

#### 4.1.1 方法

利用HTTP从网络服务获取信息有四种方式：

- GET：是在输入API之后从网站获取数据
- POST：基本就是当你填写表单或提交信息到网络服务器的后端程序时所做的事情
- PUT：用来更新一个对象或信息
- DELETE：用于删除一个对象

#### 4.1.2 验证

通常API验证的方法都是用类似令牌（token）方式调用，每次API调用都会把令牌传递到服务器上。这种令牌要么是用户注册的时候分配给用户，要么就是在用户调用的时候才提供，可能是长期固定的值，也可能是频繁变化的，通过服务器对用户名和密码的组合处理后生成。

## 第五章 存储数据

urllib.request.urlretrieve可以根据文件的URL下载文件：

```Python
from urllib.request import urlretrieve
from urllib.request import urlopen
from bs4 import BeautifulSoup
html = urlopen("http://www.pythonscraping.com")
bsObj = BeautifulSoup(html)
imageLocation = bsObj.find("a", {"id": "logo"}).find("img")["src"]
urlretrieve (imageLocation, "logo.jpg")
```

这段程序从http://pythonscraping.com下载logo图片，然后再程序运行的文件夹里保存为logo.jpg文件。

Python的csv库可以非常简单地修改CSV文件，甚至从零开始创建一个CSV文件。

### 5.3 MySQL

使用pymysql整合Python和MySQL。

```Python
import pymysql

#cursor不支持%d传参，整数也按照%s传
conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='root123', db='test')
cur = conn.cursor()
cur.execute("USE test")
#增加
cur.execute("insert into test(`id`, `name`, `age`) VALUES (%s,%s,%s)", ('6', 'name3', '40'))
conn.commit()
#更新
cur.execute("update test set name=%s where id=%s", ('name6', '6'))
conn.commit()
#查询
cur.execute("SELECT * FROM test")
print(cur.fetchall())
#删除
cur.execute("delete from test where id=%s", ('6'))
conn.commit()
cur.close()
conn.close()
```

此程序中有两个对象：链接对象（conn）和光标对象（cur）。

```MySQL
ALTER DATABASE scraping CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
ALTER TABLE pages CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE pages CHANGE title title VARCHAR(200) CHARACTER SET utf8mb4 COLLATE
utf8mb4_unicode_ci;
ALTER TABLE pages CHANGE content content VARCHAR(10000) CHARACTER SET utf8mb4 CO
LLATE utf8mb4_unicode_ci;
```

执行上面的设置可以让MySQL支持Unicode字符。

### 5.4 Email

Python有两个包可以发送邮件：smtplib和email。

## 第六章 读取文档

互联网并不是一个HTML页面的集合。它是一个信息集合，而HTML文件只是展示信息的一个框架而已。如果我恩的爬虫不能读取其他类型的文件，包括纯文本、PDF、图像、视频、邮件等，我们将会失去很大一部分数据。

本章重点介绍文档处理的相关内容，包括把文件下载到文件夹里，以及读取文档并提取数据。我们还会介绍文档的不同编码类型，让程序可以读取非英文的HTML页面。

Python中对读取的文档使用UTF-8进行解析**str(value,'utf-8')**；

用BeautifulSoup和Python3.x对文档进行UTF-8编码，如下所示：

```Python
from urllib.request import urlopen
from bs4 import BeautifulSoup

html = urlopen("http://en.wikipedia.org/wiki/Python_(programming_language)")
bsObj = BeautifulSoup(html, "html.parser")
content = bsObj.find("div", {"id":"mw-content-text"}).get_text()
content = bytes(content, "UTF-8")
content = content.decode("UTF-8")
print(content)
```

### 6.3 读取CSV

```Python
from urllib.request import urlopen
from io import StringIO
import csv

data = urlopen("http://pythonscraping.com/files/MontyPythonAlbums.csv").read().decode('ascii', 'ignore')
dataFile = StringIO(data)
csvReader = csv.reader(dataFile)

for row in csvReader:
	print("The album \""+row[0]+"\" was released in "+str(row[1]))
```

### 6.4 PDF

```Python
from pdfminer.pdfinterp import PDFResourceManager, process_pdf
from pdfminer.converter import TextConverter
from pdfminer.layout import LAParams
from io import StringIO
from urllib.request import urlopen

def readPDF(pdfFile):
    rsrcmgr = PDFResourceManager()
    retstr = StringIO()
    laparams = LAParams()
    device = TextConverter(rsrcmgr, retstr, laparams=laparams)

    process_pdf(rsrcmgr, device, pdfFile)
    device.close()

    content = retstr.getvalue()
    retstr.close()
    return content

pdfFile = urlopen("http://pythonscraping.com/pages/warandpeace/chapter1.pdf")
outputString = readPDF(pdfFile)
print(outputString)
pdfFile.close()
```

### 6.5 微软Word和.docx

```Python
from zipfile import ZipFile
from urllib.request import urlopen
from io import BytesIO
from bs4 import BeautifulSoup

wordFile = urlopen("http://pythonscraping.com/pages/AWordDocument.docx").read()
wordFile = BytesIO(wordFile)
document = ZipFile(wordFile)
xml_content = document.read('word/document.xml')

wordObj = BeautifulSoup(xml_content.decode('utf-8'), "lxml-xml")
textStrings = wordObj.findAll("w:t")
for textElem in textStrings:
    closeTag = ""
    try:
        style = textElem.parent.previousSibling.find("w:pStyle")
        if style is not None and style["w:val"] == "Title":
            print("<h1>")
            closeTag = "</h1>"
    except AttributeError: #不打印标签
        pass 
    print(textElem.text)
    print(closeTag)
```

此代码将一个远程Word文档读成一个二进制文件对象（BytesIO与本章之前用的StringIO类似），再用Python的标准库zipfile解压（所有的.docx文件为了节省空间都进行过压缩），然后读取这个解压文件，就变成XML了。

# 第二部分 高级数据采集

用BeautifulSoup和Python3.x对文档进行UTF-8编码，如下所示：这部分内容就是要帮你分析原始数据，获取隐藏在数据背后的故事——网站的真实故事其实都隐藏在JavaScript、登录表单和网站反抓取措施的背后。

通过这部分内容的学习，你将掌握如何用网络爬虫测试网站，自动化处理，以及通过更多的方式接入网络。最后你将学到一些数据采集的工具，帮助你在不同的环境中收集和操作任意类型的网络数据，深入互联网的每个角落。

## 第七章 数据清洗

本章将介绍一些工具和技术，通过改变代码的编写方式，帮你从源头控制数据零乱的问题，并且对已经进入数据库的数据进行清洗。

n-gram：将一段字符串分割成成对的数组，例如Python programming language分割成2-gram是Python programming，programming language。

但是其中还有凌乱的数据，用正则先把换行符变成空格，把连续的多个空格替换成一个空格，把Unicode字符过滤掉。把内容转换成UTF-8格式以消除转义字符。

另外处理数据的规则：

- 剔除单字符的“单词”，除非这个字符是i或a
- 剔除维基百科的引用标记
- 剔除标点符号

数据标准化：电话号码会有不同的格式，因此间各不同的格式统一，有助于查看数据

对存储后的数据再进行清洗可以使用OpenRefine，看这个软件类似于Excel啊。

## 第八章 自然语言处理

在这一章里，我们将尝试探索英语这个复杂的主题。

使用n-gram将一篇文章清洗出来之后，去掉那些is啊什么的，这些常用的没有意义的单词，然后将频率最高的单词获取出来，找出这些单词所在的语句，基本就可以用这些语句总结这篇文章的主旨了。

### 8.2 马尔可夫模型

将整个字符串中的单词进行处理，然后得出马尔可夫链组成的句子。这个句子只是根据前后单词出现的概率进行设置的，可能是一个没有任务意义的句子。

### 8.3 自然语言工具包

本章主要讨论对文本中所有单词的统计分析。哪些单词使用得最频繁？哪些单词用得最少？一个单词后面跟着哪几个单词？这些单词是如何组合在一起的？我们应该做却还没做的事情，是理解每个单词的具体含义。

自然语言工具包（Natural Language Toolkit，NLTK）用于识别和标记英语文本中各个词的词性。

自然语言工具包将上面所说的文本处理方式进行了整合和加强，使得处理数据更快更方便。

NLTK一个基本功能就是识别句子中各个词的词性。识别这个单词是符号、介词、动词还是其他的词性。pos_tag函数。

NLTK用英语的上下文无关文法（contextfreegrammar）识别词性。上下文无关文法基本上可以看成一个规则集合，用一个有序的列表确定一个词后面可以跟哪些词。NLTK的上下文无关文法确定的是一个词性后面可以跟哪些词性。无论什么时候，只要遇到像“dust”这样一个含义不明确的单词，NLTK都会用上下文无关文法的规则来判断，然后确定一个合适的词性。

## 第九章 穿越网页表单与登录窗口进行采集

本章将重点介绍POST方法，即把信息推送给网络服务器进行存储和分析。

### 9.1 Python Request库

擅长处理那些复杂HTTP请求、cookie、header等内容的Python第三方库。

```Python
import requests
#post中需要传输的数据使用一个字典进行封装，也就是用chrome浏览器看到的form data数据
params = {'firstname': 'Ryan', 'lastname': 'Mitchell'}
r = requests.post("http://pythonscraping.com/files/processing.php", data=params)
print(r.text)
```

### 9.4 提交文件和图像

```Python
import requests
files = {'uploadFile': open('../files/Python-logo.png', 'rb')}
r = requests.post("http://pythonscraping.com/pages/processing2.php",
files=files)
print(r.text)
```

需要注意，这里提交给表单字段uploadFile的值不是一个简单的字符串了，而是一个用open函数打开的Python文件对象。在这个例子中，我提交了一个保存在我电脑上的图像文件，文件路径是相对这个Python程序所在位置的../files/Python-logo.png。

### 9.5 处理登录和cookie

一旦网站验证了你的登录权证，它就会将它们保存在你的浏览器的cookie中，里面通常包含一个服务器生成的令牌、登录有效时限和状态跟踪信息。网站会把这个cookie当作信息验证的证据，在你浏览网站的每个页面时出示给服务器。

```Python
import requests
session = requests.Session()
params = {'username': 'username', 'password': 'password'}
s = session.post("http://pythonscraping.com/pages/cookies/welcome.php", params)
print("Cookie is set to:")
print(s.cookies.get_dict())
print("-----------")
print("Going to profile page...")
s = session.get("http://pythonscraping.com/pages/cookies/profile.php")
print(s.text)
```

会话（session）对象（调用requests.Session()获取）会持续跟踪会话信息，像cookie、header，甚至包括运行HTTP协议的信息，比如HTTPAdapter（为HTTP和HTTPS的链接会话提供统一接口）。

**HTTP基本接入认证**

```Python
import requests
from requests.auth import AuthBase
from requests.auth import HTTPBasicAuth
auth = HTTPBasicAuth('ryan', 'password')
r = requests.post(url="http://pythonscraping.com/pages/auth/login.php", auth=
auth)
print(r.text)
```

虽然这看着像是一个普通的POST请求，但是有一个HTTPBasicAuth对象作为auth参数传递到请求中。显示的结果将是用户名和密码验证成功的页面（如果验证失败，就是一个拒绝接入页面）。

## 第十章 采集javascript

### Selenium

Selenium可以让浏览器自动加载页面，获取需要的数据，甚至页面截屏，或者判断网站上某些动作是否发生。

Selenium自己不带浏览器，它需要与第三方浏览器结合在一起使用。如果你在Firefox上运行Selenium，可以直接看到一个Firefox窗口被打开，进入网站，然后执行你在代码中设置的动作。

```Python
from selenium import webdriver
import time
driver = webdriver.PhantomJS(executable_path='')
driver.get("http://pythonscraping.com/pages/javascript/ajaxDemo.html")
time.sleep(3)
print(driver.find_element_by_id('content').text)
driver.close()
```

这段代码用PhantomJS库创建了一个新的SeleniumWebDriver，首先用WebDriver加载页面，然后暂停执行3秒钟，再查看页面获取（希望已经加载完成的）内容。

## 第十一章 图像识别与文字处理

将图像翻译成文字一般被称为光学文字识别（Optical Character Recognition，OCR）。可以实现OCR的底层库并不多，目前很多库都是使用共同的几个底层OCR库，或者是在上面进行定制。这类OCR系统有时会变得非常复杂，所有我建议你在实践这一章的代码示例之前先阅读下一节的内容。

虽然有很多库可以进行图像处理，但在这里我们只重点介绍两个库：Pillow和Tesseract。

### 11.1 Tesseract

Tesseract是目前公认最优秀、最精确的开源OCR系统。

Tesseract是一个Python的命令行工具,安装之后，要用tesseract命令在Python的外面运行。

### 11.2 NumPy

NumPy是一个非常强大的库，具有大量线性代数以及大规模科学计算的方法。因为NumPy可以用数学方法把图片表示成巨大的像素数组，所以它可以流畅地配合Tesseract完成任务。

### 11.3 处理格式规范的文字

```shell
#将图片识别成文字
$tesseract text.tif textoutput | cat textoutput.txt
```

若图片中包含背景色，那么数据处理就会大打折扣。可以先试用Pillow库对图片进行预处理，创建一个阈值过滤器来去掉渐变的背景色，只把文字留下来

```Python
from PIL import Image
import subprocess
def cleanFile(filePath, newFilePath):
    image = Image.open(filePath)
# 对图片进行阈值过滤，然后保存
    image = image.point(lambda x: 0 if x<143 else 255)
    image.save(newFilePath)
# 调用系统的tesseract命令对图片进行OCR识别
    subprocess.call(["tesseract", newFilePath, "output"])
# 打开文件读取结果
    outputFile = open("output.txt", 'r')
    print(outputFile.read())
    outputFile.close()
cleanFile("text_2.jpg", "text_2_clean.png")
```

## 第十二章 避开采集陷阱

### 12.1 让网络机器人看起来像人类用户

#### 12.1.1 修改请求头

经典的Python爬虫在使用urllib标准库时，都会发送如下的请求头：

| 属性            | 内容              |
| --------------- | ----------------- |
| Accept-Encoding | identity          |
| User-Agent      | Python-urllib/3.4 |

```Python
import requests
from bs4 import BeautifulSoup

session = requests.Session()
headers = {"User-Agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit 537.36 (KHTML, like Gecko) Chrome","Accept":"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"}
url = "https://www.whatismybrowser.com/detect/what-http-headers-is-my-browser-sending"
req = session.get(url, headers=headers)

bsObj = BeautifulSoup(req.text, "lxml")
print(bsObj.find("table",{"class":"table-striped"}).get_text)
```

设置请求头，通常真正重要的参数就是User-Agent。无论你在做什么项目，一定要记得把User-Agent属性设置成不容易引起怀疑的内容，不要用Python-urllib/3.4。另外，如果你正在处理一个警觉性非常高的网站，就要注意那些经常用却很少检查的请求头，比如Accept-Language属性，也许它正是那个网站判断你是个人类访问者的关键。

#### 12.1.2 处理cookie

网站会用cookie跟踪你的访问过程，如果发现了爬虫异常行为就会中断你的访问，比如特别快速地填写表单，或者浏览大量页面。虽然这些行为可以通过关闭并重新连接或者改变IP地址来伪装（更多信息请参见第14章），但是如果cookie暴露了你的身份，再多努力也是白费。

查看cookie，还可以调用delete_cookie()、add_cookie()和delete_all_cookies()方法来处理cookie。

```Python
from selenium import webdriver
driver = webdriver.PhantomJS(executable_path='<Path to Phantom JS>')
driver.get("http://pythonscraping.com")
driver.implicitly_wait(1)
print(driver.get_cookies())
savedCookies = driver.get_cookies()
driver2 = webdriver.PhantomJS(executable_path='<Path to Phantom JS>')
driver2.get("http://pythonscraping.com")
driver2.delete_all_cookies()
for cookie in savedCookies:
driver2.add_cookie(cookie)
driver2.get("http://pythonscraping.com")
driver.implicitly_wait(1)
print(driver2.get_cookies())from selenium import webdriver
driver = webdriver.PhantomJS(executable_path='<Path to Phantom JS>')
driver.get("http://pythonscraping.com")
driver.implicitly_wait(1)
print(driver.get_cookies())
```

在这个例子中，第一个webdriver获得了一个网站，打印cookie并把它们保存到变量savedCookies里。第二个webdriver加载同一个网站（技术提示：必须首先加载网站，这样Selenium才能知道cookie属于哪个网站，即使加载网站的行为对我们没任何用处），删除所有的cookie，然后替换成第一个webdriver得到的cookie。当再次加载这个页面时，两组cookie的时间戳、源代码和其他信息应该完全一致。从GoogleAnalytics的角度看，第二个webdriver现在和第一个webdriver完全一样。

### 12.2 问题检查表

如果你一直被网站封杀却找不到原因，那么这里有个检查列表，可以帮你诊断一下问题出在哪里

- 首先，如果你从网络服务器收到的页面是空白的，缺少信息，或其遇到他不符合你预期的情况（或者不是你在浏览器上看到的内容），有可能是因为网站创建页面的JavaScript执行有问题
- 如果你准备向网站提交表单或发出POST请求，记得检查一下页面的内容，看看你想提交的每个字段是不是都已经填好，而且格式也正确
- 如果你已经登录网站却不能保持登录状态，或者网站上出现了其他的“登录状态”异常，请检查你的cookie。确认在加载每个页面时cookie都被正确调用，而且你的cookie在每次发起请求时都发送到了网站上
- 如果你在客户端遇到了HTTP错误，尤其是403禁止访问错误，这可能说明网站已经把你的IP当作机器人了，不再接受你的任何请求。你要么等待你的IP地址从网站黑名单里移除，要么就换个IP地址。如果你确定自己并没有被封杀，那么再检查下面的内容：确认你的爬虫在网站上的速度不是特别快；修改你的请求头；确认你没有点击或访问任何人类用户通常不能点击或接入的信息；

## 第十三章 用爬虫测试网站

### 13.1 Python单元测试

Python的单元测试模块unittest。只要先导入模块然后继承unittest.TestCase类就可以实现下面的功能：

- 为每个单元测试的开始和结束提供setUp和tearDown函数
- 提供不同类型的“断言”语句让测试成功或失败
- 把所有以test_开头的函数当做单元测试运行，忽略不带test_的函数

```Python
import unittest
class TestAddition(unittest.TestCase):
def setUp(self):
print("Setting up the test")
def tearDown(self):
print("Tearing down the test")
def test_twoPlusTwo(self):
total = 2+2
self.assertEqual(4, total)
if __name__ == '__main__':
unittest.main()
```

### 13.2 Selenium单元测试

Selenium不要求单元测试必须是类的一个函数，它的“断言”语句也不需要括号，而且测试通过的话不会有提示，只有当测试失败时才会产生信息提示：

```Python
from selenium import webdriver


driver = webdriver.PhantomJS(executable_path='/Users/ryan/Documents/pythonscraping/code/headless/phantomjs-1.9.8-macosx/bin/phantomjs')
driver.get("http://en.wikipedia.org/wiki/Monty_Python")
assert "Monty Python" in driver.title
print("Monty Python was in the title")
driver.close()
```

## 第十四章 远程采集

启用远程平台的人通常有两个目的：对更大计算能力和灵活性的需求，以及对可变IP地址的需求。

### 14.1 Tor代理服务器

通过不同服务器构成多个层把客户端包在最里面。数据进入网络之前会被加密，因此任何服务器都不能偷取通信数据。

PySocks：是一个非常简单的Python代理服务器通信模块，它可以和Tor配合使用。

# XPath

## XPath节点

### 节点

在XPath中，有七种类型的节点：元素、属性、文本、命名空间、处理指令、注释以及文档（根）节点。

```xml
<?xml version="1.0" encoding="ISO-8859-1"?>

<bookstore>

<book>
  <title lang="en">Harry Potter</title>
  <author>J K. Rowling</author> 
  <year>2005</year>
  <price>29.99</price>
</book>

</bookstore>
```

上面的XML文档中的节点例子：

```
<bookstore> （文档节点）
<author>J K. Rowling</author> （元素节点）
lang="en" （属性节点） 
```

### 基本值（或称原子值，Atomic value）

基本值是无父或无子的节点。

## XPath语法

```xml
<?xml version="1.0" encoding="ISO-8859-1"?>

<bookstore>

<book>
  <title lang="eng">Harry Potter</title>
  <price>29.99</price>
</book>

<book>
  <title lang="eng">Learning XML</title>
  <price>39.95</price>
</book>

</bookstore>
```

### 选取节点

XPath使用路径表达式在XML文档中选取节点。节点是通过沿着路径或者step来选取的

下面列出了最有用的路径表达式：

| 表达式   | 描述                                                     |
| -------- | -------------------------------------------------------- |
| nodename | 选取此节点的所有子节点                                   |
| /        | 从跟节点选取                                             |
| //       | 从匹配选择的当前节点选择文档中的节点，而不考虑他们的位置 |
| .        | 选取当前节点                                             |
| ..       | 选取当前节点的父节点                                     |
| @        | 选取属性                                                 |

示例

在下面的表格中，我们列出了一些路径表达式以及表达式的结果

| 路径表达式      | 结果                                                         |
| --------------- | ------------------------------------------------------------ |
| bookstore       | 选取bookstore元素的所有子节点                                |
| /bookstore      | 选取根元素bookstore。（假如路径起始于正斜杠/，则此路径始终代表到某元素的绝对路径） |
| bookstore/book  | 选取属于bookstore的子元素的所有book元素                      |
| //book          | 选取所有book子元素，而不管他们在文档中的位置                 |
| bookstore//book | 选择属于bookstore元素的后代的所有book元素，而不管他们位于bookstore之下的什么位置 |
| //@lang         | 选取名为lang的所有属性                                       |

### 谓语

谓语用来查找某个特定的节点或者包含某个指定的值的节点，谓语被嵌在方括号内

示例

下面的表格中，我们列出了带有谓语的一些路径表达式，以及表达式的结果

| 路径表达式                         | 结果                                                         |
| ---------------------------------- | ------------------------------------------------------------ |
| /bookstore/book[1]                 | 选取属于bookstore子元素的第一个book元素                      |
| /bookstore/book[last()]            | 选取属于bookstore子元素的最后一个book元素                    |
| /bookstore/book[last()-1]          | 选取属于 bookstore 子元素的倒数第二个 book 元素。            |
| /bookstore/book[position()<3]      | 选取最前面的两个属于 bookstore 元素的子元素的 book 元素。    |
| //title[@lang]                     | 选取所有拥有名为 lang 的属性的 title 元素。                  |
| //title[@lang='eng']               | 选取所有 title 元素，且这些元素拥有值为 eng 的 lang 属性。   |
| /bookstore/book[price>35.00]       | 选取 bookstore 元素的所有 book 元素，且其中的 price 元素的值须大于 35.00。 |
| /bookstore/book[price>35.00]/title | 选取 bookstore 元素中的 book 元素的所有 title 元素，且其中的 price 元素的值须大于 35.00。 |

### 选取未知节点

XPath通配符可用来选取未知的XML元素

| 通配符 | 描述               |
| ------ | ------------------ |
| *      | 匹配任何元素节点   |
| @*     | 匹配任何属性节点   |
| node() | 匹配任何类型的节点 |

示例

| 路径表达式   | 结果                            |
| ------------ | ------------------------------- |
| /bookstore/* | 选取bookstore元素的所有子元素   |
| //*          | 选取文档中的所有元素            |
| //title[@*]  | 选取所有带有属性的 title 元素。 |

### 选取若干路径

| 路径表达式                       | 结果                                                         |
| -------------------------------- | ------------------------------------------------------------ |
| //book/title \| //book/price     | 选取 book 元素的所有 title 和 price 元素。                   |
| //title \| //price               | 选取文档中的所有 title 和 price 元素。                       |
| /bookstore/book/title \| //price | 选取属于 bookstore 元素的 book 元素的所有 title 元素，以及文档中所有的 price 元素。 |

### XPath轴

```xml
<?xml version="1.0" encoding="ISO-8859-1"?>

<bookstore>

<book>
  <title lang="eng">Harry Potter</title>
  <price>29.99</price>
</book>

<book>
  <title lang="eng">Learning XML</title>
  <price>39.95</price>
</book>

</bookstore>
```

轴可定义相对于当前节点的节点集。

| 轴名称             | 结果                                                     |
| ------------------ | -------------------------------------------------------- |
| ancestor           | 选取当前节点的所有先辈（父、祖父等）。                   |
| ancestor-or-self   | 选取当前节点的所有先辈（父、祖父等）以及当前节点本身。   |
| attribute          | 选取当前节点的所有属性。                                 |
| child              | 选取当前节点的所有子元素                                 |
| descendant         | 选取当前节点的所有后代元素（子、孙等）。                 |
| descendant-or-self | 选取当前节点的所有后代元素（子、孙等）以及当前节点本身。 |
| following          | 选取文档中当前节点的结束标签之后的所有节点。             |
| namespace          | 选取当前节点的所有命名空间节点。                         |
| parent             | 选取当前节点的父节点。                                   |
| preceding          | 选取文档中当前节点的开始标签之前的所有节点。             |
| preceding-sibling  | 选取当前节点之前的所有同级节点。                         |
| self               | 选取当前节点。                                           |

示例

| 例子                   | 结果                                                         |
| ---------------------- | ------------------------------------------------------------ |
| child::book            | 选取所有属于当前节点的子元素的 book 节点。                   |
| attribute::lang        | 选取当前节点的 lang 属性。                                   |
| child::*               | 选取当前节点的所有子元素。                                   |
| attribute::*           | 选取当前节点的所有属性。                                     |
| child::text()          | 选取当前节点的所有文本子节点。                               |
| child::node()          | 选取当前节点的所有子节点。                                   |
| descendant::book       | 选取当前节点的所有 book 后代。                               |
| ancestor::book         | 选择当前节点的所有 book 先辈。                               |
| ancestor-or-self::book | 选取当前节点的所有 book 先辈以及当前节点（如果此节点是 book 节点） |
| child::*/child::price  | 选取当前节点的所有 price 孙节点。                            |

### XPath运算

| 运算符 | 描述           | 实例                      | 返回值                                                       |
| ------ | -------------- | ------------------------- | ------------------------------------------------------------ |
| \|     | 计算两个节点集 | //book \| //cd            | 返回所有拥有 book 和 cd 元素的节点集                         |
| +      | 加法           | 6 + 4                     | 10                                                           |
| -      | 减法           | 6 - 4                     | 2                                                            |
| *      | 乘法           | 6 * 4                     | 24                                                           |
| div    | 除法           | 8 div 4                   | 2                                                            |
| =      | 等于           | price=9.80                | 如果 price 是 9.80，则返回 true。 如果 price 是 9.90，则返回 false。 |
| !=     | 不等于         | price!=9.80               | 如果 price 是 9.90，则返回 true。 如果 price 是 9.80，则返回 false。 |
| <      | 小于           | price<9.80                | 如果 price 是 9.00，则返回 true。 如果 price 是 9.90，则返回 false。 |
| <=     | 小于或等于     | price<=9.80               | 如果 price 是 9.00，则返回 true。 如果 price 是 9.90，则返回 false。 |
| >      | 大于           | price>9.80                | 如果 price 是 9.90，则返回 true。 如果 price 是 9.80，则返回 false。 |
| >=     | 大于或等于     | price>=9.80               | 如果 price 是 9.90，则返回 true。 如果 price 是 9.70，则返回 false。 |
| or     | 或             | price=9.80 or price=9.70  | 如果 price 是 9.80，则返回 true。 如果 price 是 9.50，则返回 false。 |
| and    | 与             | price>9.00 and price<9.90 | 如果 price 是 9.80，则返回 true。 如果 price 是 8.50，则返回 false。 |
| mod    | 计算除法的余数 | 5 mod 2                   | 1                                                            |