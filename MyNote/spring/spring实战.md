## 3.4bean的作用域

默认情况下，Spring应用上下文所有bean都是以单例（singleton）的形式创建的，也就是说，不管给定的一个bean被注入到其他bean多少次，每次所注入的都是同一个实例。

spring定义了多种作用域，可以基于这些作用域创建bena

- 单例（singleton）：在整个应用中，只创建一个bean实例
- 原型（prototype）：每次注入或者通过spring应用上下文获取的时候，都会创建一个新的实例
- 会话（session）：在web应用中，为每个会话创建一个bean实例
- 请求（request）：在web应用中，为每个请求创建一个bean实例

可以通过注解@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)实现指定bean的作用域

## 3.5运行时值注入

### 3.5.1注入外部的值

在spring中，处理外部值的最简单方式就是声明属性源并通过spring的Environment来检索属性

```java
@Configuration
@PropertySource("classpath:/com/soundsystem/app.properties")
public class ExpressiveConfig{
    @Autowired
    Environment env;
    
    @Bean
    public BlankDisc disc(){
        return new BlankDisc(
        env.getProperty("disc.title"),
        env.getProperty("disc.artist"));
    }
}
//app.properties内容如下
disc.title=****
disc.artist=****

//另外一种获取配置文件值的方式
public BlankDisc(@Value("${disc.title}") String title,@Value("${disc.artist}") String artist){
    this.title=title;
    this.artist=artist;
}

//想要使用占位符需要配置PropertySourcesPlaceholderConfigurer，因为它可以基于Spring Environment及其属性源来解析占位符
@Bean
public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
    return new PropertySourcesPlaceholderConfigurer();
}
```

### 3.5.2使用Spring表达式语言进行装配

Spring表达式语言（SpEL）能够以一种强大和简洁的方式将值装配到bean属性和构造器参数中，在这个过程中所使用的表达式会在运行时计算得到的值。其特性：

- 使用bean的id来引用bean
- 调用方法和访问对象的属性
- 对值进行算术、关系和逻辑运算
- 正则表达式匹配
- 集合操作

SpEL样例

SpEL表达式要放到#{...}之中。#{1}此表达式的计算结果为1

#{T(System).currentTimeMillis()}：T()表达式会将java.lang.System视为java中对应的类型，因此可以调用其static修饰的currentTimeMillis()方法。

#{sgtPeppers.artist}:此表达式或计算得到ID为sgtPeppers的bean的artist属性。

#{systemProperties['disc.title']}：通过systemProperties对象引用系统属性。

```java
//运用样例
public BlankDisc(@Value("#{systemProperties['disc.title']}") String title,@Value("#{systemProperties['disc.artist']}") String artist){
    this.title=title;
    this.artist=artist;
}
```

浮点数:#{3.14159}；科学计数法：98，700:#{9.87E4};String：#{'Hello'};Boolean:#{false}。

安全的运算符：#{artistSelector.selectArtist()?.toUpperCase()}：如果select Artist()返回值时null直接返回null不调用toUpperCase方法，返回值不为null则调用toUpperCase方法。

**在表达式中使用类型**

需要访问类作用域的方法和常量的话，要依赖T()这个关键运算符。T(java.lang.Match).PI；获取PI，<u>T()运算符的结果会是一个Class对象</u>

T()运算符的真正价值在于它能够访问目标类型的静态方法和常量。

**SpEl运算符**

算术比较  +、-、*、/、%、^
比较运算  <、>、==、<=、>=、lt、gt、rq、le、ge
逻辑运算  and、or、not、|
条件运算  ?: (ternary)、?:(Elvis)
正则表达式 matches

# 4.面向切面的spring

在软件开发中，散布于应用中多处的功能被称为横切关注点。通常来讲，这些横切关注点从概念上是与应用的业务逻辑相分离的（但是往往会直接嵌入到应用的业务逻辑之中）。把这些横切关注点与业务逻辑相分离正是面向切面编程所要解决的问题。横切关注点可以被模块化为特殊的类，这些类被称为切面。这样有两个好处：首先，每个关注点都集中于一个地方，而不是分散到多出代码中；其次，服务模块更简洁，因为他们只包含主要关注点的代码，而次要关注点的代码被转移到切面中了。

### 4.1.1定义AOP术语

描述切面常用的术语有通知（advice）、切点（pointcut）和连接点（jionpoint）。

**通知**

切面有自己必须要完成的工作，在AOP术语中，切面的工作被称为通知。通知定义了切面是什么以及何时使用。

spring切面可以应用5种类型的通知：

1. 前置通知（Before）：在目标方法被调用之前调用通知功能
2. 后置通知（After）：在目标方法完成之后调用通知，此时不会关心方法的输出是什么
3. 返回通知（After-returning）：在目标方法成功执行之后调用通知
4. 异常通知（After-throwing）：在目标方法抛出异常后调用通知
5. 环绕通知（Around）：通知包裹了被通知的方法，在被通知的方法调用之前和调用之后指定自定的行为

**连接点**

我们的应用可能有数以千计的时机应用通知。这些时机被称为连接点。连接点是在应用执行过程中能够插入切面的一个点。这个点可以是调用方法时、抛出异常时、甚至修改一个字段时。切面代码可以利用这些点插入到应用的正常流程之中，并添加新的行为。

**切点**

切点的定义会匹配通知所要织入的一个或多个连接点。我们通常使用明确的类和方法名称，或是利用正则表达式定义所匹配的类和方法名称来指定这些切点。

**切面**

切面时通知和切点的结合。通知和切点共同定义了切面的全部的内容---它是什么，在何时和何处完成其功能。

**引入**

引入允许我们向现有的类添加新方法或属性。

**织入**

织入是把切面应用到目标对象并创建新的代理对象的过程。切面在指定的连接点被织入到目标对象中。在目标对象的生命周期里有多个点可以进行织入：

1. 编译期：切面在目标类编译时被织入。这种方式需要特殊的编译器。AspectJ的织入编译器就是以这种方式织入切面的
2. 类加载期：切面在目标类加载到jvm时被织入。这种方式需要特殊的类加载器，它可以在目标类被引用之前增强该目标类的字节码。AspectJ5的加载时织入就支持以这种方式织入切面
3. 运行时期：切面在应用运行的某个时刻被织入。一般情况下，在织入切面时，AOP容器会为目标对象动态地创建一个代理对象。SpringAOP就是以这种方式织入切面的

### 4.1.2Spring对AOP的支持

spring提供了4种类型的AOP支持

1. 基于代理的经典SpringAOP
2. 纯POJO切面
3. @AspectJ注解驱动的切面
4. 注入式AspectJ切面（适用于Spring各版本）

前三种都是SpringAOP实现的变体，SpringAOP构建在动态代理基础之上，因此，Spring对AOP的支持局限于方法拦截

spring在运行时通知对象。通过在代理类中包裹切面，spring在运行时期把切面织入到spring管理的bean中。代理类封装了目标类，并拦截被通知方法的调用，再把调用转发给真正的目标bean。当代理拦截到方法调用时，在调用目标bean方法之前，会执行切面逻辑。

## 4.2通过切点来选择连接点

关于spring AOP的Aspectj切点，最重要的一点就是spring仅支持Aspectj切面指示器的一个子集。

1.Spring AOP支持的AspectJ指示器：

1. arg（）：限制连接点匹配参数为制定类型的执行方法。
2. @args（）：限制连接点匹配参数由指定注解标注的执行方法。
3. execution（）：用于匹配时连接点的执行方法。
4. this（）：限制连接点匹配AOP代理的bean引用为制定类型的类。
5. target：限制连接点匹配目标对象为制定类型的类。
6. @target：限制连接点匹配特定的执行对象，这些对象对应的类要有指定类型的注解。
7. within（）：限制连接点匹配指定的类型。
8. @within（）：限制连接点匹配指定注解所标注的类型，当使用Spring AOP时，方法定义在由指定的注解所标注的类里。
9. @annotation：限制匹配带有指定注解的连接点。

　　在Spring中尝试使用其他的AspectJ其他指示器时，会抛出IllegalArgumentException异常。executation指示器是实际执行匹配，其他的指示器都是用来限制匹配的

　　2.除了上述的AspectJ指示器外，Spring还引入了一个新的指示器：

　　bean（）：使用bean ID或bean 名称作为参数来限定切点只匹配特定的bean。

2、编写切点：

假设有一个业务接口，取名为Performance（这个类在com.mfc.annotation.noparameter包下），接口里有一个方法perform()，现在要写一个切点表达式，这个切点表达式能设置当前的perform()方法执行时处罚通知的调用，那么这个表达式是： execution(* com.mfc.annotation.noparameter.Performance.perform(..))

对这个表达式解释一下：表达式以“*”开始，表名不关心返回值类型，然后使用了全限定类名和方法名，对于方法参数列表，使用了两个点号（..）表明其诶单要选择任意的perform()方法，无论该方法的参数是什么。

```java
// 定义接口
public interface Performance {
    public void perform();
}


// 实现类
@Component
public class Dancer implements Performance {
    @Override
    public void perform() {
        System.out.println(this.getClass().getName() + ":dancing...");
    }
}

// 定义切面
@Component
@Aspect
public class Audience {
    @Before("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void silenceCellPhones() {
        System.out.println(this.getClass().getName() + ":Silencing cell phones");
    }

    @Before("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void takeSeats() {
        System.out.println(this.getClass().getName() + ":Taking seats");
    }

    @AfterReturning("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void applause() {
        System.out.println(this.getClass().getName() + ":CLAP...");
    }

    @AfterThrowing("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void demandRefund() {
        System.out.println(this.getClass().getName() + ":Demanding a refund");
    }
}

@Component
@Aspect
public class AudiencePointcut {
    @Pointcut("execution(* com.korov.springboot.aspect.Performance.perform(..))")
    public void performance() {
    }

    @Before("performance()")
    public void silenceCellPhones() {
        System.out.println(this.getClass().getName() + ":Silencing cell phones");
    }

    @Before("performance()")
    public void takeSeats() {
        System.out.println(this.getClass().getName() + ":Taking seats");
    }

    @AfterReturning("performance()")
    public void applause() {
        System.out.println(this.getClass().getName() + ":CLAP...");
    }

    @AfterThrowing("performance()")
    public void demandRefund() {
        System.out.println(this.getClass().getName() + ":Demanding a refund");
    }
}

```

执行perform时会自动触发切面的执行。

### 4.3.4通过注解引入新功能

```java
//接口
public interface Encoreable {
    void performEncore();
}

//实现
@Component
public class DefaultEncoreable implements Encoreable {
    @Override
    public void performEncore() {
        System.out.println(this.getClass().getName() + ":DefaultEncoreable");
    }
}

//切面
@Aspect
public class EncoreableIntroducer {
    @DeclareParents(value="com.korov.springboot.aspect.Performance+",defaultImpl = DefaultEncoreable.class)
    public static Encoreable encoreable;
}
```

@DeclareParents注解由三部分组成：

1. value属性指定了哪种类型的bean要引入该接口。在本例中，也就是所有实现Performance的类型。（+号表示是Performance的所有子类）
2. defaultImpl属性指定了为引入功能提供实现的类。
3. @DeclareParents注解所标注的静态属性指明了要引入的接口

## 4.4在xml中声明切面

如果你需要声明切面，但是又不能为通知类添加注解的时候，就必须转向xml配置了。

```xml
AOP配置元素                              用 途
<aop:advisor>                           定义AOP通知器
<aop:after>                             定义AOP后置通知（不管被通知的方法是否执行成功）
<aop:after-returning>                   定义AOP返回通知
<aop:after-throwing>                    定义AOP异常通知
<aop:around>                            定义AOP环绕通知
<aop:aspect>                            定义一个切面
<aop:aspectj-autoproxy>                 启用 @AspectJ注解驱动的切面
<aop:before>                            定义一个AOP前置通知
<aop:config>                            顶层的AOP配置元素。 大多数的<aop:*>元素必须包含在<aop:config>元素内
<aop:declare-parents>                   以透明的方式为被通知的对象引入额外的接口
<aop:pointcut>                          定义一个切点
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:aop="http://www.springframework.org/schema/aop"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  <aop:config>
    <aop:aspect ref="audience">
        <aop:pointcut 
            id="performance" 
            expression="execution(** aopdemo.Performance.perform(..))" />
        <aop:around
             pointcut-ref="performance"
             method="watchPerformance" />
    </aop:aspect>
  </aop:config>
</beans>
```

## 4.5注入AspectJ切面

已经在springboot中实践，需要先安装aspectj环境，然后编译器选择ajc编译器，好像和lombok的日志注解冲突，需要手动修改

