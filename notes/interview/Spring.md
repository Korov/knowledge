# Spring

## Spring bean的生命周期

首先是实例化bean，然后将bean的各种引用注入到bean中，如果实现了某些指定的接口，则会调用这些接口中要求实现的方法，之后就是使用bean，最后销毁bean。

## SpringMVC，其处理流程

![image-20191116183355508](http://korov.myqnapcloud.cn:19000/images/image-20191116183355508.png)

1. 发起请求到前端控制器（DispatcherServlet）
2. 前端控制器请求HandlerMapping查找Handler（可以根据XML配置、注解进行查找）
3. 处理器映射器HandlerMapping向前端控制器返回Handler，HandlerMapping会把请求映射为 HandlerExecutionChain对象（包含一个Handler处理器（页面控制）对象，多个HandlerInterceptor拦截器对象），通过这种策略模式，很容易添加新的映射策略
4. 前端控制器调用处理器适配器去执行Handler
5. 处理器适配器HandlerAdapter将会根据适配的结果去执行Handler
6. Handler执行完成给适配器返回ModelAndView
7. 处理器适配器向前端控制器返回ModelAndView （ModelAndView是springmvc框架的一个底层对象，包括 Model和view）
8. 前端控制器请求视图解析器去进行视图解析 （根据逻辑视图名解析成真正的视图(jsp)），通过这种策略很容易更换其他视图技术，只需要更改视图解析器即可 
9. 视图解析器向前端控制器返回View 
10. 前端控制器进行视图渲染 （视图渲染将模型数据(在ModelAndView对象中)填充到request域） 
11. 前端控制器向用户响应结果 

1.Spring mvc先将请求发送给DispatcherServlet。2.DispatcherServlet 查询一个或多个 HandlerMapping，找到处理请求的 Controller。3.DispatcherServlet 再把请求提交到对应的 Controller。4.Controller 进行业务逻辑处理后，会返回一个ModelAndView。5.Dispathcher 查询一个或多个 ViewResolver 视图解析器，找到 ModelAndView 对象指定的视图对象。6.视图对象负责渲染返回给客户端

## Spring mvc有哪些组件

- 前置控制器 DispatcherServlet
- 映射控制器 HandlerMapping
- 处理器 Controller
- 模型和视图 ModelAndView
- 视图解析器 ViewResolver

## @RequestMapping 的作用是什么

将http请求映射到相应的类/方法上

## @Autowired 的作用是什么

@Autowired它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作，通过@Autowired的使用来消除set/get方法。

## SpringMVC怎么样设定重定向和转发

转发在返回值前面加forward，重定向在返回值前面加redirect。

## springmvc怎么和ajax相互调用

通过jackson或者其他框架吧java里面的对象直接转化成json对象

## SpringMvc 的控制器是不是单例模式,如果是,有什么问题,怎么解决

是单例模式,所以在多线程访问的时候有线程安全问题,不要用同步,会影响性能的,解方案是在控制器里面不能写字段。

## sprinmvc中中文乱码

post乱码可以在web.xml中配置一个CharacterEncodingFilter过滤器。

get乱码：对参数进行重新编码，或者在tomcat配置文件中修改编码与项目的编码一致。

## 怎么样从springmvc中得到request或者session

直接在方法的形参中声明HttpServletRequest，springmvc就会自动把request对象传入。

## SpringMvc 用什么对象从后台向前台传递数据的

通过 ModelMap 对象,可以在这个对象里面用 put 方法,把对象加到里面,前台就可以过 el 表达式拿到

## 怎么样把ModelMap里面的数据放入Session中

可以在类上面加上@SessionAttributes注解,里面包含的字符串就是要放入session里面的key

## SpringMvc 怎么和 AJAX 相互调用的

通过 Jackson 框架就可以把 Java 里面的对象直接转化成 Js 可以识别的 Json 对象具体步骤如下 ：

1）加入 Jackson.jar

2）在配置文件中配置 json 的映射

3）在接受 Ajax 方法里面可以直接返回 Object,List 等,但方法前面要加上@ResponseB注解

当一个方法想ajax返回特殊对象，如Object，List时，需要在方法上加上@ResponseBody 注解

## SpringMvc 里面拦截器是怎么写的

有两种写法,一种是实现HandlerInterceptor接口，另外一种是继承适配器类，接着在接口方法当中，实现处理逻辑；然后在SpringMvc的配置文件中配置拦截器即可

## 为什么要使用spring

1. 降低了组件之间的耦合性，实现了软件各层之间的解耦
2. 可以使用容易提供的众多服务，如事物管理，消息服务等
3. 容器提供了单例模式支持
4. 容器提供了AOP技术，利用容器很容易实现权限拦截，运行期监控等功能
5. 容器提供了众多的辅助类，能加快应用的开发
6. spring对与主流的应用框架提供了集成支持
7. spring属于低侵入式设计，代码污染极低
8. 独立于各种应用服务器
9. spring的DI机制降低了也业务对象替换的复杂性
10. spring的高度开放性，并不强制应用完全依赖于spring，开发者可以自由选择spring的部分或全部

## 解释一下Spring AOP

AOP即面向切面编程，是OOP编程的有效补充。使用AOP技术，可以将一些系统性相关的编程工作，独立提取出来，独立实现，然后通过切面切入进系统。从而避免了在业务逻辑的代码中混入很多的系统相关的逻辑——比如权限管理，事物管理，日志记录等等。

AOP分为静态AOP和动态AOP：

- 静态AOP是指AspectJ实现的AOP，他是将切面代码直接编译到Java类文件中。
- 动态AOP是指将切面代码进行动态织入实现的AOP，JDK动态代理。

## 解释一下spring ioc

即“控制反转”，不是什么技术，而是一种设计思想。在Java开发中，Ioc意味着将你设计好的对象交给容器控制，而不是传统的在你的对象内部直接控制。

　　IoC很好的体现了面向对象设计法则之一—— 好莱坞法则：“别找我们，我们找你”；即由IoC容器帮对象找相应的依赖对象并注入，而不是由对象主动去找。

## Spring有哪些主要模块

core模块、aop模块、data access模块、web模块、test模块

- spring core：框架的最基础部分，提供 ioc 和依赖注入特性。
- spring context：构建于 core 封装包基础上的 context 封装包，提供了一种框架式的对象访问方法。
- spring dao：Data Access Object 提供了JDBC的抽象层。
- spring aop：提供了面向切面的编程实现，让你可以自定义拦截器、切点等。
- spring Web：提供了针对 Web 开发的集成特性，例如文件上传，利用 servlet listeners 进行 ioc 容器初始化和针对 Web 的 ApplicationContext。
- spring Web mvc：spring 中的 mvc 封装包提供了 Web 应用的 Model-View-Controller（MVC）的实现。

## spring 常用的注入方式有哪些

Spring通过DI（依赖注入）实现IOC（控制反转），常用的注入方式主要有三种：构造方法注入，setter注入，基于注解的注入。

## spring 中的 bean 是线程安全的吗

不是。spring 中的 bean 默认是单例模式，spring 框架并没有对单例 bean 进行多线程的封装处理。

## spring 支持几种 bean 的作用域

singleton、prototype、request、session、globalSession五中作用域。

- singleton：spring ioc 容器中只存在一个 bean 实例，bean 以单例模式存在，是系统默认值；
- prototype：每次从容器调用 bean 时都会创建一个新的示例，既每次 getBean()相当于执行 new Bean()操作；
- Web 环境下的作用域：
  - request：每次 http 请求都会创建一个 bean；
  - session：同一个 http session 共享一个 bean 实例；
  - global-session：用于 portlet 容器，因为每个 portlet 有单独的 session，globalsession 提供一个全局性的 http session

## spring 自动装配 bean 有哪些方式

- byName：按照bean的属性名称来匹配要装配的bean
- byType：按照bean的类型来匹配要装配的bean
- constructor：按照bean的构造器入参的类型来进行匹配 
- autodetect（自动检测）：先使用constructor进行装配，如果不成功就使用byType来装配

## spring 事务实现方式有哪些

- 声明式事务：声明式事务也有两种实现方式，基于 xml 配置文件的方式和注解方式（在类上添加 @Transaction 注解）。
- 编码方式：提供编码的形式管理和维护事务。

## Spring事务原理

Spring事务本质是数据库对事务的支持，没有数据库的事务支持，spring时无法提供事务功能的。

## 说一下 spring 的事务隔离

spring 有五大隔离级别，默认值为 ISOLATION_DEFAULT（使用数据库的设置），其他四个隔离级别和数据库的隔离级别一致：

- ISOLATION_DEFAULT：用底层数据库的设置隔离级别，数据库设置的是什么我就用什么；
- ISOLATIONREADUNCOMMITTED：未提交读，最低隔离级别、事务未提交前，就可被其他事务读取（会出现幻读、脏读、不可重复读）；
- ISOLATIONREADCOMMITTED：提交读，一个事务提交后才能被其他事务读取到（会造成幻读、不可重复读），SQL server 的默认级别；
- ISOLATIONREPEATABLEREAD：可重复读，保证多次读取同一个数据时，其值都和事务开始时候的内容是一致，禁止读取到别的事务未提交的数据（会造成幻读），MySQL 的默认级别；
- ISOLATION_SERIALIZABLE：序列化，代价最高最可靠的隔离级别，该隔离级别能防止脏读、不可重复读、幻读。
- 脏读 ：表示一个事务能够读取另一个事务中还未提交的数据。比如，某个事务尝试插入记录 A，此时该事务还未提交，然后另一个事务尝试读取到了记录 A。
- 不可重复读 ：是指在一个事务内，多次读同一数据。
- 幻读 ：指同一个事务内多次查询返回的结果集不一样。比如同一个事务 A 第一次查询时候有 n 条记录，但是第二次同等条件下查询却有 n+1 条记录，这就好像产生了幻觉。发生幻读的原因也是另外一个事务新增或者删除或者修改了第一个事务结果集里面的数据，同一个记录的数据内容被修改了，所有数据行的记录就变多或者变少了。

## @transactional注解在什么情况下会失效，为什么。

@Transactional注解事务的特性：

- service类标签(一般不建议在接口上)上添加@Transactional，可以将整个类纳入spring事务管理，在每个业务方法执行时都会开启一个事务，不过这些事务采用相同的管理方式。
- @Transactional 注解只能应用到 public 可见度的方法上。 如果应用在protected、private或者 package可见度的方法上，也不会报错，不过事务设置不会起作用。
- 默认情况下，Spring会对unchecked异常进行事务回滚；如果是checked异常则不回滚。 
  辣么什么是checked异常，什么是unchecked异常

解决Transactional注解不回滚：

- 检查你方法是不是public的
- 你的异常类型是不是unchecked异常，注解上面写明异常类型即可`@Transactional(rollbackFor=Exception.class) `
- 数据库引擎要支持事务
- 是否开启了对注解的解析`<tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true"/>`
- spring是否扫描到你这个包，如下是扫描到org.test下面的包`<context:component-scan base-package="org.test" ></context:component-scan>`