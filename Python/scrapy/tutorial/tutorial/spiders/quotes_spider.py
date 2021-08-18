from tutorial.quotes_item import quotes_item
import scrapy


class QuotesSpider(scrapy.Spider):
    # Spider的名称，必须在项目中唯一
    name = "quotes"
    
    # 数字说明运行顺序，越小越优先
    custom_settings = {
    	'ITEM_PIPELINES':{'tutorial.pipelines.quotes_pipeline': 300},
    }

    # 必须返回一个可遍历的请求，或者可以产生请求的方法
    def start_requests(self):
        urls = [
            'http://quotes.toscrape.com/page/1/',
            'http://quotes.toscrape.com/page/2/',
        ]
        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse)

    # 处理从每个请求中下载下来的数据，response参数是一个 TextResponse 类型的参数，包含界面内容以及其他帮助方法
    # 此方法通常用来解析返回数据，抽取需要提取的数据，或者提取出新的URL继续下载内容
    def parse(self, response):
        items = []
        for quote in response.xpath('//div[@class="quote"]'):
            my_item = quotes_item(text=quote.xpath('./span[@class="text"]/text()').extract_first(), author=quote.xpath('.//small[@class="author"]/text()').extract_first(), tags=quote.xpath('.//div[@class="tags"]/a[@class="tag"]/text()').extract())
            # yield {
                # 'text': my_item.text,
                # 'author': my_item.author,
                # 'tags': my_item.tags
            # }
            items.append(my_item)
            yield my_item
            
        next_page = response.css('li.next a::attr(href)').get()
        if next_page is not None:
            # 用urljoin方法提取链接
            next_page = response.urljoin(next_page)
            # callback可以设置不同的解析函数来解析链接
            yield scrapy.Request(next_page, callback=self.parse)

        # 直接提取href或者a作为链接
        for href in response.css('ul.pager a::attr(href)'):
            yield response.follow(href, callback=self.parse)

        for a in response.css('ul.pager a'):
            yield response.follow(a, callback=self.parse)

        # 直接提取一批链接继续追踪
        anchors = response.css('ul.pager a')
        yield from response.follow_all(anchors, callback=self.parse)

        yield from response.follow_all(css='ul.pager a', callback=self.parse)
