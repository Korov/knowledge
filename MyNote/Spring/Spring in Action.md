# 1.spring之旅

## 1.1简化spring开发

为了降低Java开发的复杂性，Spring采取了以下4种关键策略：

基于POJO的轻量级和最小侵入性编程；

通过依赖注入和面向接口实现松耦合；

基于切面和惯例进行声明式编程；

通过切面和模板减少样板式代码。

### 1.1.1依赖注入

DI能够实现松耦合，一个对象只通过接口（而不是具体实现或初始化过程）来表明依赖关系，那么这种依赖就能够在对象本身毫不知情的情况下，用不同的具体实现进行替换。

两种方式实现依赖注入：xml配置，java配置

获取上下文：

Spring通过应用上下文（Application Context）装载bean的定义并把他们组装起来。Spring应用上下文全权负责对象的创建和组装。例如：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="knight" class="generator.BraveKnight">
    </bean>
</beans>
```

```java
public static void main(String[] args) {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
            String.join(File.separator, "src", "main", "resources", "generator", "knights.xml"));
        Knight knight = context.getBean(Knight.class);
        knight.embarkOnQuest();
        context.close();
    }
```



### 1.1.2应用切面

DI能够让相互协作的软件组件保持松散耦合，而面向切面编程（AOP）允许你把遍布应用各处的功能分离出来形成可重用的组件。

诸如日志、事物管理和安全这样的系统服务经常融入到自身具有核心业务逻辑的组件中去，这些系统服务通常被称为横切关注点，因为他们会跨越系统的多个组件。AOP能够使这些服务模块化，并以声明的方式将他们应用到它们需要影响的组件中去。所造成的结果就是这些组件会具有更高的内内聚性并且会更加关注自身业务，完全不需要了解涉及系统服务所带来的复杂性。

例如：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    <bean id="knight" class="generator.BraveKnight"></bean>
    <bean id="minstrel" class="generator.Minstrel"></bean>
    <aop:config>
        <aop:aspect ref="minstrel">
            <!--将切点定义在embarkOnQuest方法上-->
            <aop:pointcut id="embark" expression="execution(* *.embarkOnQuest(..))"></aop:pointcut>
            <!--执行embarkOnQuest方法前执行singBefore方法-->
            <aop:before pointcut-ref="embark" method="singBefore"></aop:before>
            <!--执行embarkOnQuest方法后执行singEnd方法-->
            <aop:after pointcut-ref="embark" method="singEnd"></aop:after>
        </aop:aspect>
    </aop:config>
</beans>

```

```java
public static void main(String[] args) {
    FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
        String.join(File.separator, "src", "main", "resources", "generator", "knights.xml"));
    Knight knight = context.getBean(Knight.class);
    knight.embarkOnQuest();
    context.close();
    /**
    执行结果：
    Minstrel sing before
    I am BraveKnight
    Minstrel sing end
    */
}
```

[^这里使用了Spring的AOP配置命名空间把Minstrel bean声明为一个切面。首先，需要把Minstrel声明为一个bean，然后在<aop:aspect>元素中引用该bean。为了进一步定义切面，声明（使用<aop:before>）在embarkOnQuest()方法执行前调用Minstrel的singBeforeQuest()方法。这种方式被称为前置通知，同时声明（使用<aop:after>）在embarkQuest()方法执行后调用singAfterQuest()方法，这种方式被称为后置通知。]: 



## 1.2 容纳你的bean

Spring容器负责创建对象，装配他们，配置他们并管理他们的整个生命周期，从生存到死亡。

### 1.2.1 使用应用上下文

Spring自带了多种类型的应用上下文：

- AnnotationConfigApplicationContext：从一个或多个基于Java的配置类中加载Spring应用上下文
- AnnotationConfigWebApplicationContext：从一个或多个基于Java的配置类中加载Spring Web应用上下文
- ClassPathXmlApplicationContext：从类路径下的一个或多个XML配置文件中加载上下文定义，把应用上下文的定义文件作为类资源
- FileSystemXmlapplicationcontext：从文件系统的一个或多个XML配置文件中加载上下文定义
- XmlWebApplicationContext：从Web应用下的一个或多个XML配置文件中加载上下文定义。

### 1.2.2 bean的生命周期

1. Spring对bean进行实例化
2. Spring将值和bean的引用注入到bean对应的属性中
3. 如果bean实现了BeanNameAware接口，Spring将bean的ID传递给setBeanName()方法
4. 如果bean实现了BeanFactoryAware接口，Spring将调用setBeanFactory()方法，将BeanFactory容器实例传入
5. 如果bean实现ApplicationContextAware接口，Spring将调用setApplicationContext()方法，将bean所在的应用上下文的引用传进来
6. 如果bean实现了BeanPostProcessor接口，Spring将调用他们的postProcessBeforeInitialization()方法
7. 如果bean实现了InitializingBean接口，Spring将调用他们的afterPropertiesSet()方法。类似的，如果bean使用init-method声明了初始化方法，该方法也会被调用
8. 如果bean实现了BeanPostProcessor接口，Spring将调用他们的postProcessAfterInitization()方法
9. 此时，bean已经准备就绪，可以被应用程序使用了，他们将一直驻留在应用上下文中，知道该应用上下文被销毁
10. 如果bean实现了DisposableBean接口，Spring将调用destroy()接口方法。同样，如果bean使用destroy-method声明了销毁方法，该方法也会被调用。

# 2.装配bean

## 2.1spring配置的可选方案

spring提供了三种主要的装配机制：

1. 在xml中进行显示的配置
2. 在java中进行显示的配置
3. 隐式的bean发现机制和自动装配

建议：尽可能的使用自动配置的机制，显示配置的越少越好，当必须使用显示配置的时候推荐使用类型安全并且比xml更加强大的JavaConfig。

## 2.2自动化装配bean

spring从两个角度来实现自动化装配：

1. 组件扫描（component scanning）：spring会自动发现应用上下文中所创建的bean
2. 自动装配（autowiring）：spring自动满足bean之间的依赖

### 2.2.1创建可被发现的bean

在普通的类文件上面加上@Component注解，然后在配置类文件上面加上@Configuration和@ComponentScan两个注解。

如果没有自他配置，@ComponentScan默认会扫描与配置类相同的包及其子包。

也可以使用xml的<context:component-scan base-packae="***"></context:component>实现组件扫描。

### 2.2.2为组件扫描的bean命名

spring会为bean设置一个默认ID，类名首字母小写。若想指定bean的ID可以使用@Component("yourName")，双引号中的内容为指定的ID。也可以使用@Named("yourName")。两者有细微差别，但是大多数场景中，他们可以相互替换。

### 2.2.3设置扫描的基础包

默认的@ComponentScan会以配置文件所在的包为基础包，可以@ComponentScan("soundsystem")将基础包设置为soundsystem。

@ComponentScan(basePackages={"soundsystem","viedo"})设置多个基础包，缺陷：重构时，指定的基础包可能出错。

@ComponentScan(basePackageClasses={CDPlayer.class,DVDPlayer.class})，为basePackageClasses属性所设置的数组中包含了类，这些类所在的包将会作为组件扫描的基础。可以考虑在包中创建一个用来进行扫描的空标记接口。

### 2.2.4为bean添加注解实现自动装配

```java
@Autowired
public CDPlayer(CompactDisc cd){this.cd = cd;}

@Autowired
public void insertDisc(CompactDisc cd){this.cd = cd;}
```

@Autowired可以用在类的任何方法上。

## 2.3通过java代码装配bean

要将第三方库中的组件装配到你的应用中，在这种情况下，是没有办法在他的类上添加@Component和@Autowired注解的。可以使用java和xml，优先选择java。

### 2.3.1创建配置类

在类上面加上@Configuration

### 2.3.2声明简单的bean

```java
//bean的ID为sgtPeppers
@Bean
public CompactDisc sgtPeppers(){return new SgtPeppers();}

//bean的ID为lonelyHeartsClubBand
@Bean(name="lonelyHeartsClubBand")
public CompactDisc sgtPeppers(){return new SgtPeppers();}

// 返回一个已经注入CompactDisc的CDPlayer的bean
@Bean
public CDPlayer cdPlayer(CompactDisc compactDisc){
    return new CDPlayer(compactDisc);
}
```

带有@Bean注解的方法可以采用任何必要的java功能来产生bean实例。

### 2.3.3使用xml装配bean

<bean id ="compactDisc" class="soundsystem.SgtPeppers">

注入bean

```xml
<bean id ="cdPlayer" class="soundsystem.CDPlayer"></bean>
    <constructor-arg ref="compactDisc"></constructor>
</bean>

#使用c-命名空间
<bean id="cdPlayer" class="soundsystem.CDPlayer" c:cd-ref="compactDisc"></bean>
```

可以使用xml声明一个bean，也可以在xml中通过构造器初始化一个bean，可以在构造器中注意一个bean引用也可以把字面量注入构造器中。

## 2.4导入和混合配置

### 2.4.1在javaConfig中引用配置文件

```java
@Configuration
@Import({CDPlayerConfig.class})
@ImportResource("classpath:cd-config.xml")
public class SoundSystemConfig{}
```

使用@Import引用javaconfig文件，使用@ImportResource引用xml配置文件。使用一个配置文件将散乱的配置文件整合起来。

### 2.4.2在xml配置中引用javaconfig

使用import引入xml文件使用bean引入javaConfig文件

# 3.高级装配

## 3.1环境与profile

### 3.1.1配置profile bean

Spring为环境相关的bean所提供的解决方案起始与构建时的方案没有太大差别。当然，在这个过程中需要根据环境决定该创建哪个bean和不创建哪个bean。spring是在运行时确定的。

要使用profile，首先要将所有不同的bean定义整理到一个或多个profile中，在将应用部署到每个环境时，要确保对应的profile处于激活状态。

在java配置中，可以使用@Profile注解指定某个bean属于哪一个profile。此注解可以使用在类上也可以使用在方法上

也可以在xml中配置profile。

### 3.1.2激活profile

spring使用spring.profiles.active和spring.profiles.default，如果设置了active就会激活指定的，否则激活默认的。

有多种方式来设置这两个属性：

1. 作为DispatcherServlet的初始化参数
2. 作为Web应用的上下文
3. 作为JNDI条目
4. 作为环境变量
5. 作为JVM的系统属性
6. 在集成测试类上，使用@ActiveProfiles注解设置

## 3.2条件化bean

spring4引入了一个新的@Conditional注解，它可以用到带有@Bean注解的方法上。如果给定的条件计算结果为true，就会创建这个bean，否则的话，这个bean会被忽略。

### 3.2.1创建自定义的限定符

@Qualifier与@Component一起使用可以自定义bean的ID，使用的时候用@Autowired与@Qualifier配合使用可以获取指定的bean。@Qualifier也可以与@Bean一起使用指定bean的ID。

使用自定义的限定符注解

```java
@Target({ElementType.CONSTRUCTOR,ElementType.FIELD,
        ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Creamy{}

//Creamy是类名，可以使用@Creamy来注入bean
```

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

# 个人总结

## 1. 控制反转

以前一个类使用另外一个类需要自己new，控制权在自己手里，现在new一个实例的工作由一个专门的IoC容器创建进行的，控制权交给了Spring，这就是控制反转。

## 2. 依赖注入

由容器动态的将某个依赖关系注入到组件之中。构造器注入，xml注入等方式。

