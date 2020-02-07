# Spring中使用了哪些设计模式

## 一、简单工厂模式

```xml
<beans>
  <bean id="singletonBean" class="com.itxxz.HelloItxxz">
     <constructor-arg>
       <value>Hello! 这是singletonBean!value>
     </constructor-arg>
 </ bean>
```

另外一种就是在配置类中实现配置bean。

## 二、工厂方法模式

​        通常由应用程序直接使用new创建新的对象，为了将对象的创建和使用相分离，采用工厂模式,即应用程序将对象的创建及初始化职责交给工厂对象。

　　一般情况下,应用程序有自己的工厂对象来创建bean.如果将应用程序自己的工厂对象交给Spring管理,那么Spring管理的就不是普通的bean,而是工厂Bean。