避免大量拥有相同内容的小类的开销，使大家共享一个类
轻量级模式是池技术的重要实现方式。

轻量级模式角色名称：
1. Flyeweight（抽象享元角色）：它简单的说就是一个产品的抽象类，同时定义出对象的外部状态和内部状态的接口实现
2. ConcreteFlyweight（具体享元角色）：具体的一个产品类，实现抽象角色定义的业务。该角色中需要注意的是内部状态处理应该与环境无关，不应该出现一个操作改变了内部状态，同时修改了外部状态，这是绝对不允许的
3. unsharedConcreteFlyweight（不可共享的享元角色）：不存在外部状态或者安全要求（如线程安全）不能够使用共享技术的对象，该对象一般不会出现在享元工厂中
4. FlyweightFactory（享元工厂）：职责非常简单，就是构造一个池容器，同时提供从池中获得对象的方法

享元模式的目的在于运用共享技术，使得一些细粒度的对象共享，我们的设计确实也应该这样，多使用细粒度的对象，便于重用或重构。

内部状态：内部状态是对象可共享出来的信息， 存储在享元对象内部并且不会随环境改变而改变，
     如我们例子中的id、 postAddress等， 它们可以作为一个对象的动态附加信息， 不必直接储存
     在具体某个对象中， 属于可以共享的部分。

外部状态：外部状态是对象得以依赖的一个标记， 是随环境改变而改变的、 不可以共享的状态， 如
     我们例子中的考试科目+考试地点复合字符串， 它是一批对象的统一标识， 是唯一的一个索
     引值。