# 架构

![scrapy_architecture_02.png](http://korov.myqnapcloud.cn:19000/images/scrapy_architecture_02.png)

The data flow in Scrapy is controlled by the execution engine, and goes like this:

1. The [Engine](https://docs.scrapy.org/en/latest/topics/architecture.html#component-engine) gets the initial Requests to crawl from the [Spider](https://docs.scrapy.org/en/latest/topics/architecture.html#component-spiders).
2. The [Engine](https://docs.scrapy.org/en/latest/topics/architecture.html#component-engine) schedules the Requests in the [Scheduler](https://docs.scrapy.org/en/latest/topics/architecture.html#component-scheduler) and asks for the next Requests to crawl.
3. The [Scheduler](https://docs.scrapy.org/en/latest/topics/architecture.html#component-scheduler) returns the next Requests to the [Engine](https://docs.scrapy.org/en/latest/topics/architecture.html#component-engine).
4. The [Engine](https://docs.scrapy.org/en/latest/topics/architecture.html#component-engine) sends the Requests to the [Downloader](https://docs.scrapy.org/en/latest/topics/architecture.html#component-downloader), passing through the [Downloader Middlewares](https://docs.scrapy.org/en/latest/topics/architecture.html#component-downloader-middleware) (see [`process_request()`](https://docs.scrapy.org/en/latest/topics/downloader-middleware.html#scrapy.downloadermiddlewares.DownloaderMiddleware.process_request)).
5. Once the page finishes downloading the [Downloader](https://docs.scrapy.org/en/latest/topics/architecture.html#component-downloader) generates a Response (with that page) and sends it to the Engine, passing through the [Downloader Middlewares](https://docs.scrapy.org/en/latest/topics/architecture.html#component-downloader-middleware) (see [`process_response()`](https://docs.scrapy.org/en/latest/topics/downloader-middleware.html#scrapy.downloadermiddlewares.DownloaderMiddleware.process_response)).
6. The [Engine](https://docs.scrapy.org/en/latest/topics/architecture.html#component-engine) receives the Response from the [Downloader](https://docs.scrapy.org/en/latest/topics/architecture.html#component-downloader) and sends it to the [Spider](https://docs.scrapy.org/en/latest/topics/architecture.html#component-spiders) for processing, passing through the [Spider Middleware](https://docs.scrapy.org/en/latest/topics/architecture.html#component-spider-middleware) (see [`process_spider_input()`](https://docs.scrapy.org/en/latest/topics/spider-middleware.html#scrapy.spidermiddlewares.SpiderMiddleware.process_spider_input)).
7. The [Spider](https://docs.scrapy.org/en/latest/topics/architecture.html#component-spiders) processes the Response and returns scraped items and new Requests (to follow) to the [Engine](https://docs.scrapy.org/en/latest/topics/architecture.html#component-engine), passing through the [Spider Middleware](https://docs.scrapy.org/en/latest/topics/architecture.html#component-spider-middleware) (see [`process_spider_output()`](https://docs.scrapy.org/en/latest/topics/spider-middleware.html#scrapy.spidermiddlewares.SpiderMiddleware.process_spider_output)).
8. The [Engine](https://docs.scrapy.org/en/latest/topics/architecture.html#component-engine) sends processed items to [Item Pipelines](https://docs.scrapy.org/en/latest/topics/architecture.html#component-pipelines), then send processed Requests to the [Scheduler](https://docs.scrapy.org/en/latest/topics/architecture.html#component-scheduler) and asks for possible next Requests to crawl.
9. The process repeats (from step 1) until there are no more requests from the [Scheduler](https://docs.scrapy.org/en/latest/topics/architecture.html#component-scheduler).

## Components



### Scrapy Engine

The engine is responsible for controlling the data flow between all components of the system, and triggering events when certain actions occur. See the [Data Flow](https://docs.scrapy.org/en/latest/topics/architecture.html#data-flow) section above for more details.

控制整个系统组件的数据流，并且在特定动作发生时出发事务。

### Scheduler

The Scheduler receives requests from the engine and enqueues them for feeding them later (also to the engine) when the engine requests them.

用来接收引擎发过来的请求，由过滤器过滤重复url并将其压入队列中，在引擎再次请求的时候返回，可以想象成一个url的优先队列，由他来决定下一个要抓取的网址是什么

### Downloader

The Downloader is responsible for fetching web pages and feeding them to the engine which, in turn, feeds them to the spiders.



### Spiders

Spiders are custom classes written by Scrapy users to parse responses and extract [items](https://docs.scrapy.org/en/latest/topics/items.html#topics-items) from them or additional requests to follow. For more information see [Spiders](https://docs.scrapy.org/en/latest/topics/spiders.html#topics-spiders).

可以生成url，并从特定的url中提取自己需要的信息，用户也可以从中提取出链接，让Scrapy继续抓取下一个页面

### Item Pipeline

The Item Pipeline is responsible for processing the items once they have been extracted (or scraped) by the spiders. Typical tasks include cleansing, validation and persistence (like storing the item in a database). For more information see [Item Pipeline](https://docs.scrapy.org/en/latest/topics/item-pipeline.html#topics-item-pipeline).

负责处理爬虫从网页中抽取的实体，主要的功能是持久化、验证实体的有效性、清除不需要的信息，当页面被爬虫解析后，将被发送到项目管道，并经过几个特定的次序处理数据。

### Downloader middlewares

Downloader middlewares are specific hooks that sit between the Engine and the Downloader and process requests when they pass from the Engine to the Downloader, and responses that pass from Downloader to the Engine.

Use a Downloader middleware if you need to do one of the following:

- process a request just before it is sent to the Downloader (i.e. right before Scrapy sends the request to the website);
- change received response before passing it to a spider;
- send a new Request instead of passing received response to a spider;
- pass response to a spider without fetching a web page;
- silently drop some requests.

For more information see [Downloader Middleware](https://docs.scrapy.org/en/latest/topics/downloader-middleware.html#topics-downloader-middleware).



### Spider middlewares

Spider middlewares are specific hooks that sit between the Engine and the Spiders and are able to process spider input (responses) and output (items and requests).

Use a Spider middleware if you need to

- post-process output of spider callbacks - change/add/remove requests or items;
- post-process start_requests;
- handle spider exceptions;
- call errback instead of callback for some of the requests based on response content.

For more information see [Spider Middleware](https://docs.scrapy.org/en/latest/topics/spider-middleware.html#topics-spider-middleware).

## Event-driven networking

Scrapy is written with [Twisted](https://twistedmatrix.com/trac/), a popular event-driven networking framework for Python. Thus, it’s implemented using a non-blocking (aka asynchronous) code for concurrency.

For more information about asynchronous programming and Twisted see these links:

# 基本概念

## 命令行工具

### 配置设置

Scrapy将查找ini样式的配置参数`scrapy.cfg`标准位置的文件：

1. `/etc/scrapy.cfg`或`c:\scrapy\scrapy.cfg`（全系统）
2. `~/.config/scrapy.cfg`(`$XDG_CONFIG_HOME`)`~/.scrapy.cfg`(`$HOME`)用于全局（用户范围）设置
3. `scrapy.cfg`在一个项目的根目录中

Scrapy还可以理解并通过许多环境变量进行配置。目前有：

- `SCRAPY_SETTINGS_MODULE`
- `SCRAPY_PROJECT`
- `SCRAPY_PYTHON_SHELL`

### 创建项目

```bash
scrapy startproject myproject [project_dir]
cd [project_dir]
```

它将在 `project_dir` 目录。如果 `project_dir` 没有指定， `project_dir` 将与 `myproject` .

```bash
# 创建蜘蛛
scrapy genspider mydomain mydomain.com
```

全局命令

1. startproject
2. genspider
3. settings
4. runspider
5. shell
6. fetch
7. view
8. version

仅Project命令（必须进入项目中才可以执行的命令）：

1. crawl
2. check
3. list
4. edit
5. parse
6. bench

## 蜘蛛

对于蜘蛛来说，抓取周期是这样的：



1. 首先生成对第一个URL进行爬网的初始请求，然后指定一个回调函数，该函数使用从这些请求下载的响应进行调用。

   要执行的第一个请求是通过调用 `start_requests()` 方法，该方法(默认情况下)生成 `Request` 中指定的URL的 `start_urls` 以及 `parse` 方法作为请求的回调函数。

2. 在回调函数中，解析响应(网页)并返回 [item objects](https://www.osgeo.cn/scrapy/topics/items.html#topics-items) ， `Request` 对象，或这些对象的可迭代。这些请求还将包含一个回调(可能相同)，然后由Scrapy下载，然后由指定的回调处理它们的响应。

3. 在回调函数中，解析页面内容，通常使用 [选择器](https://www.osgeo.cn/scrapy/topics/selectors.html#topics-selectors) （但您也可以使用beautifulsoup、lxml或任何您喜欢的机制）并使用解析的数据生成项。

4. 最后，从spider返回的项目通常被持久化到数据库（在某些 [Item Pipeline](https://www.osgeo.cn/scrapy/topics/item-pipeline.html#topics-item-pipeline) ）或者使用 [Feed 导出](https://www.osgeo.cn/scrapy/topics/feed-exports.html#topics-feed-exports) 

