# MyBatis

## mybatis 中 #{}和 ${}的区别是什么

- \#{}是预编译处理，${}是字符串替换；
- Mybatis在处理#{}时，会将sql中的#{}替换为?号，调用PreparedStatement的set方法来赋值；
- Mybatis在处理时，就是把{}时，就是把时，就是把{}替换成变量的值；
- 使用#{}可以有效的防止SQL注入，提高系统安全性。

## mybatis 有几种分页方式？

分页方式：逻辑分页和物理分页。

逻辑分页：使用MyBatis自带的RowBounds进行分页，它是一次性查询很多数据，然后在数据中进行检索。

物理分页：自己手写SQL分页或使用分页插件PageHelper，去数据库查询指定条数的分页数据形式。

## RowBounds 是一次性查询全部结果吗？为什么？

RowBounds表面是在“所有”数据中检索数据，其实并非是一次性查询出所有的数据。因为MyBatis是对jdbc的封装，在jdbc驱动中有一个Fetch Size的配置，它规定了每次最多从数据库查询多少条数据，假如你要查询更多数据，它会在你执行next()的时候，去查询更多的数据。就好比你去自动取款机取10000元，但是取款机每次只能取2500元，所以你需要取4次才能把钱取完。对于jdbc来说，当你调用next()的时候会自动帮你完成查询工作。这样的好处可以有效的防止内存溢出。

## mybatis 是否支持延迟加载？延迟加载的原理是什么？

MyBatis支持延迟加载，设置lazyLoadingEnabled=true即可。    延迟加载的原理是调用的时候触发加载，而不是在初始化的时候就加载信息。比如调用a.getB().getName(),这个时候发现a.get()的值为null,此时会单独触发事先保存好的关联B对象的SQL,先查询出来B,然后在调用a.setB(b),而这个时候在调用a.getB().getName()就有值了，这就是延迟加载的基本原理。

## 说一下 mybatis 的一级缓存和二级缓存

一级缓存: 基于 PerpetualCache 的 HashMap 本地缓存，其存储作用域为 Session，当 Session flush 或 close 之后，该 Session 中的所有 Cache 就将清空，默认打开一级缓存。

二级缓存与一级缓存其机制相同，默认也是采用 PerpetualCache，HashMap 存储，不同在于其存储作用域为 Mapper(Namespace)，并且可自定义存储源，如 Ehcache。默认不打开二级缓存，要开启二级缓存，使用二级缓存属性类需要实现Serializable序列化接口(可用来保存对象的状态),可在它的映射文件中配置 ；

对于缓存数据更新机制，当某一个作用域(一级缓存 Session/二级缓存Namespaces)的进行了C/U/D 操作后，默认该作用域下所有 select 中的缓存将被 clear。

## mybatis 和 hibernate 的区别有哪些？

- 灵活性：MyBatis更加灵活，自己可以写sql语句，使用起来比较方便。
- 可移植性：MyBatis有很多自己写的sql，因为每个数据库的sql可以不相同，所以可移植性比较差。
- 学习和使用门槛：MyBatis入门比较简单，使用门槛也更低。
- 二级缓存：hibernate拥有更好的二级缓存，它的二级缓存可以自行更换第三方的二级缓存。

## mybatis 有哪些执行器（Executor）？

Mybatis有三种基本的执行器（Executor）：

SimpleExecutor：每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。

ReuseExecutor：执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map内，供下一次使用。简言之，就是重复使用Statement对象。

BatchExecutor：执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。

## mybatis 分页插件的实现原理是什么？

分页插件的基本原理是使用Mybatis提供的插件接口，实现自定义插件，在插件的拦截方法内拦截待执行的sql，然后重写sql，根据dialect方言，添加对应的物理分页语句和物理分页参数。

## mybatis 如何编写一个自定义插件

Mybatis自定义插件针对Mybatis四大对象（Executor、StatementHandler 、ParameterHandler 、ResultSetHandler ）进行拦截，具体拦截方式为：

Executor：拦截执行器的方法(log记录)

StatementHandler ：拦截Sql语法构建的处理

ParameterHandler ：拦截参数的处理

ResultSetHandler ：拦截结果集的处理

Mybatis自定义插件必须实现Interceptor接口：

```java
public interface Interceptor {

    Object intercept(Invocation invocation) throws Throwable;

    Object plugin(Object target);

    void setProperties(Properties properties);

}
```

intercept方法：拦截器具体处理逻辑方法

plugin方法：根据签名signatureMap生成动态代理对象

setProperties方法：设置Properties属性

自定义插件demo：

```java
// ExamplePlugin.java

@Intercepts({@Signature(

  type= Executor.class,

  method = "update",

  args = {MappedStatement.class,Object.class})})

public class ExamplePlugin implements Interceptor {

  public Object intercept(Invocation invocation) throws Throwable {

  Object target = invocation.getTarget(); //被代理对象

  Method method = invocation.getMethod(); //代理方法

  Object[] args = invocation.getArgs(); //方法参数

  // do something ...... 方法拦截前执行代码块

  Object result = invocation.proceed();

  // do something .......方法拦截后执行代码块

  return result;

  }

  public Object plugin(Object target) {

    return Plugin.wrap(target, this);

  }

  public void setProperties(Properties properties) {

  }

}
```

一个@Intercepts可以配置多个@Signature，@Signature中的参数定义如下：

- type：表示拦截的类，这里是Executor的实现类；
- method：表示拦截的方法，这里是拦截Executor的update方法；
- args：表示方法参数。