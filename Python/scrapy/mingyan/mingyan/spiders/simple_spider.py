import scrapy

"""
    scrapy初始Url的两种写法，
    一种是常量start_urls，并且需要定义一个方法parse（）
    另一种是直接定义一个方法：star_requests()
    
    来到当前文件夹下使用如下命令 scrapy crawl simple_url
"""
class simple_url(scrapy.Spider):
    name = "simple_url"
    start_urls = [
        'http://lab.scrapyd.cn/page/1/',
        'http://lab.scrapyd.cn/page/2/',
    ]

    def parse(self, response, **kwargs):
        page = response.url.split("/")[-2]
        filename = 'mingyan-simple-%s.html' % page
        with open(filename, 'wb') as f:
            f.write(response.body)
        self.log('保存文件: %s' % filename)