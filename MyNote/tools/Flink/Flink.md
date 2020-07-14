# Flink编程模型

## Flink编程接口

Flink根据数据集类型的不同将核心数据接口分为两大类，一类是支持批计算的接口DateSet API，另外一类是支持流计算的接口DataStream API。同时Flink将数据处理接口抽象成四层，由上向下分别为SQL API、Table API、DateStream/DataSet API以及Stateful Stream Processing API，用户可以根据需要选择任意一层抽象接口来开发Flink应用。

SQL API完成对批计算和流计算的处理，当前正在完善中，SQL语言学习成本低，能够让数据分析人员和开发人员更快速上手。

Table API将内存中的DataStream和DateSet数据集在原有的基础之上增加Schema信息，将数据类型统一抽象成表结构，然后通过Table API提供的接口处理对应的数据集。SQL API可以直接查询Table API中注册表中的数据表。Table API构建在DataStream和DataSet之上的同时，提供了大量面向领域语言的接口编程，例如GroupByKey、Join等操作符。除此之外，Table API在转换为DataStream和DataSet的数据处理过程中，也应用了大量的优化规则对处理逻辑进行了优化。同时Table API中的Table可以和DataStream及DataSet之间进行相互转换。

DataStream API和DataSet API主要面向具有开发经验的用户，用户可以使用DataStream API处理无界数据流，使用DataSet API处理批量数据。DataStream API和DataSet API同时提供了各种数据处理接口，例如map、filter、oins、aggregations、window等方法，同时没有接口都支持了Java、Scala和Python等多种开发语言的SDK。

Stateful Stream API是Flink中处理Stateful Stream最底层的接口，用户可以使用Stateful Stream Process接口操作状态、时间等底层数据。使用Stream Process API接口开发应用的灵活性非常强，可以实现复杂的流式计算逻辑，但是相对用户使用成本也比较高，一般企业在使用Flink进行二次开发或深度封装的时候会用到这层接口。

## Flink程序结构

整个Flink程序一共分为5步，分别为：设定Flink执行环境、创建和加载数据集、对数据集指定转换操作逻辑、指定计算结果输出位置、调用execute方法出发程序执行。

### 初始化数据

ExecutionEnvironment提供不同的数据接入接口完成数据的初始化，将外部数据转换成`DataStream<T>`或`DataSet<T>`数据集。Flink中提供了多种从外部读取数据的连接器，包括批量和实时的数据连接器，能够将Flink系统和其他第三方系统连接，直接获取外部数据。

### 执行转换操作

数据从外部系统读取并转换成DataStream或DataSet数据集后，下一步就将数据集进行各种转换操作。Flink中的Transformation操作都是通过不同的Operator来实现的，每个Operator内部通过实现Function接口完成数据处理逻辑的定义。在DataStream API和DataSet API提供了大量的转换算子，例如map、flatMap、filter、keyBy等，用户只需要定义每种算子执行的函数逻辑，然后应用在数据转换操作Operator接口中即可。

### 分区key指定

在DataStream数据经过不同的算子转换过程中，某些算子需要根据指定的key进行转换，常见的有join、coGroup、groupBy类算子，需要先将DataStream或DataSet数据集转换成对应的KeyedStream和GroupedDataSet，主要目的是将相同key值的数据路由到相同的Pipeline中，然后进行下一步的计算操作。在Flink中这种操作并不是真正意义上将数据集转换成Key-Value结构，而是一种虚拟Key，目的仅仅是帮助后面的基于Key的算子使用，分区Key可以通过两种方式指定。

#### 根据字段位置指定

在DataStream API中通过`keyBy()`方法将DataStream数据集根据指定的key转换成重新分区的KeyedStream

```scala
val dataStream:DataStream[(String,Int)]=env.fromElements(("a",1),("c",2))
//根据第一个字段重新分区，然后对第二个字段进行求和运算
Val result=dataStream.keyBy(0).sum(1)
```

在DataSet API中，如果对数据根据某一条件聚合数据，对数据进行聚合时候，也需要对数据进行重新分区。如以下代码所示，使用DataSet API对数据集根据第一个字段作为GroupBy的key，然后对第二个字段进行求和运算。

```scala
val dataSet=env.fromElements(("hello",1),("flink",3))
//根据第一个字段进行数据重分区
val groupedDataSet:GroupedDataSet[(String,Int)]=dataSet.groupBy(0)
//求取相同key值下第二个字段的最大值
groupedDataSet.max(1)
```

#### 根据字段名称指定

KeyBy和GroupBy的key除了能够通过字段位置来指定之外，也可以根据字段的名称来指定。使用字段名需要DataStream中的数据结构类型必须是Tuple类或者POJOs类。

```scala
val personDataSet=env.fromElements(newPersion("Alex",18),newPersion("Peter",43))
//指定name字段名称来确定groupby字段
personDataSet.groupBy("name").max(1)

//如果程序中使用Tuple数据类型，通常情况下字段名称从1开始计算，字段位置索引从0开始计算
val personDataStream=env.fromElements(("Alex",18),("Peter",43))
//通过名称指定第一个字段名称
personDataStream.keyBy("_1")
//通过位置指定第一个字段
personDataStream.keyBy(0)
```

如果在Flink中使用嵌套的负载数据结构，可以通过字段名称指定key，

```scala
class CompelexClass(var nested:NestedClass,var tag:String){
  defthis(){this(null,"")}
}
class NestedClass(var id:Int,
                  tuple:(Long,Long,String)){
  defthis(){this(0,(0,0,""))}
}
```

通过调用nested获取整个NestedClass对象里所有的字段，调用tag获取CompelexClass中tag字段，调用nested.id获取NestedClass中的id字段，调用nested.tuple._1获取NestedClass中的tuple元祖的第一个字段。由此可以看出，Flink能够支持在复杂数据结构中灵活的获取字段信息，这也是非key-value的数据结构所具有的优势。

#### 通过key选择器指定

定义KeySelector，然后复写getKey方法，从Person对象中获取name为指定key

```scala
case class Person(name:String,age:Int)
val person=env.fromElements(Person("hello",1),Person("flink",4))
//定义KeySelector,实现getKey方法从caseclass中获取Key
val keyed:KeyedStream[WC]=person.keyBy(new KeySelector[Person,String](){
  override def getKey(person:Person):String=person.word
})
```

### 输出结果

数据集经过转换操作之后，形成最终的结果数据集，在DataStream和DataSet接口中定义了基本的数据输出方法，`writeAsText()`输出到文件，`print()`输出到控制台，同时Flink中定义了大量的Connector，方便用户和外部系统交互，用户可以直接通过调用`addSink()`添加输出系统定义的DataSink类算子，这样就能将数据输出到外部系统。

### 程序触发

所有的计算逻辑全部操作定义好之后，需要调用ExecutionEnvironment的`execution()`方法来触发应用程序的执行，其中`execution()`方法返回的结果类型为JobExecutionResult，里面包含了程序执行的时间和累加器等指标。需要注意的是，execute方法调用会因为应用的类型有所不同，DataStream流式应用需要显性地指定execute方法运行程序，如果不调用则Flink流式程序不会执行，但对于DataSet API输出算子中已经包含对execute方法的调用，则不需要显性调用execute方法，否则会出现程序异常。

## Flink数据类型

### 数据类型支持

Flink支持非常完善的数据类型，数据类型的描述信息都是由TypeInformation定义，比较常用的TypeInformation有BasicTypeInfo、TupleTypeInfo、CaseClassTypeInfo、PojoTypeInfo类等。TypeInformation主要作用好似为了在Flink系统内有效的对数据结构类型进行管理，能够在分布式计算过程中对数据的类型进行管理和推断。同时基于对数据的类型信息管理，Flink内部对数据存储也进行了相应的性能优化。Flink能够支持任意的Java或Scala数据类型，另外使用TypeInformation管理数据类型信息，能够在数据处理之前将数据类型推断出来，而不是真正在出发计算后才识别出来，这样能够及时有效地避免用户在使用Flink编写应用的过程中的数据类型问题。

#### 原生数据类型

Flink通过实现BasicTypeInfo数据类型，能够支持任意Java原生基本类型（装箱）或String类型，例如Integer、String、Double等，如以下代码所示，通过从给定的元素集中创建DataStream数据集。

```scala
//创建Int类型的数据集
val intStream:DataStream[Int]=env.fromElements(3,1,2,1,5)
//创建String类型的数据集
val dataStream:DataStream[String]=env.fromElements("hello","flink")
```

Flink实现另外一种TypeInfomation是BasicArrayTypeInfo，对应的是Java基本类型数组（装箱）或String对象的数组，如下代码通过使用Array数组和List集合创建DataStream数据集。

```scala
//通过从数组中创建数据集
val dataStream:DataStream[Int]=env.fromCollection(Array(3,1,2,1,5))
//通过List集合创建数据集
val dataStream:DataStream[Int]=env.fromCollection(List(3,1,2,1,5))
```

#### Java Tuples类型

通过定义TupleTypeInfo来描述Tuple类型数据，Flink在Java接口中定义了元祖类（Tuple）供用户使用。Flink Tuples是固定长度固定类型的Java Tuple实现，不支持空值存储。目前支持任意的Flink Java Tuple类型字段数量上限为25，如果字段数量超过上限，可以童工继承Tuple类的方式进行拓展。

```scala
//通过实例化Tuple2创建具有两个元素的数据集
val tupleStream2:DataStream[Tuple2[String,Int]]=env.fromElements(newTuple2("a",1),newTuple2("c",2))
```

#### Scala Case Class类型

Flink通过实现CaseClassTypeInfo支持任意的Scala Case Class，包括Scala Tuples类型，支持的字段数量上限为22，支持通过字段名称和位置索引获取指标，不支持存储空值。如下代码实例所示，定义WordCount Case Class数据类型，然后通过fromElements方法创建input数据集，调用keyBy()方法对数据集根据word字段重新分区。

```scala
//定义WordCountCaseClass数据结构
case class WordCount(word:String,count:Int)
//通过fromElements方法创建数据集
val input=env.fromElements(WordCount("hello",1),WordCount("world",2))
val keyStream1=input.keyBy("word")//根据word字段为分区字段，
val keyStream2=input.keyBy(0)//也可以通过指定position分区
```

通过使用Scala Tuple创建DataStream数据集，其他的使用方式和Case Class相似。需要注意的是，如果根据名称获取字段，可以使用Tuple中的默认字段名称

```scala
//通过scalaTuple创建具有两个元素的数据集
val tupleStream:DataStream[Tuple2[String,Int]]=env.fromElements(("a",1),("c",2))
//使用默认字段名称获取字段，其中_1表示tuple这种第一个字段
tupleStream.keyBy("_1")
```

#### POJOs类型

POJOs类可以完成复杂数据结构的定义，Flink通过实现PojoTypeInfo来描述任意的POJOs，包括Java和Scala类。在Flink中使用POJOs类可以通过字段名称来获取字段，如果在Flink中使用POJOs数据类型，需要遵循以下要求：

·POJOs类必须是Public修饰且必须独立定义，不能是内部类；

·POJOs类中必须含有默认空构造器；

·POJOs类中所有的Fields必须是Public或者具有Public修饰的getter和setter方法；

·POJOs类中的字段类型必须是Flink支持的。

```java
//定义JavaPerson类，具有public修饰符
public class Person{
//字段具有public修饰符
public String name;
public int age;
//具有默认空构造器
public Person(){
}
public Person(Stringname,intage){
  this.name=name;this.age=age;
}
}

val persionStream=env.fromElements(newPerson("Peter",14),newPerson("Linda",25))
  //通过Person.name来指定Keyby字段
  persionStream.keyBy("name")
  //ScalaPOJOs数据结构定义如下，使用方式与JavaPOJOs相同。
  class Person(varname:String,varage:Int){
    //默认空构造器
    defthis(){this(null,1)}
  }
```

#### Flink Value类型

Value数据类型实现了org.apache.flink.types.Value，其中包括read()和write()两个方法完成序列化和反序列化操作，相对于通用的序列化工具会有着比较高效的性能。目前Flink提供了內建的Value类型有IntValue、DoubleValue以及StringValue等，用户可以结合原生数据类型和Value类型使用。

#### 特殊数据类型

在Flink中也支持一些比较特殊的数据数据类型，例如Scala中的List、Map、Either、Option、Try数据类型，以及Java中Either数据类型，还有Hadoop的Writable数据类型。如下代码所示，创建Map和List类型数据集。这种数据类型使用场景不是特别广泛，主要原因是数据中的操作相对不像POJOs类那样方便和透明，用户无法根据字段位置或者名称获取字段信息，同时要借助TypesHint帮助Flink推断数据类型信息，关于TyepsHmt介绍可以参考下一小节。

# DataStream API的介绍与使用

## DataStream编程模型

DataStream API主要可以分为三个部分，DataSource模块、Transformation模块以及DataSink模块，DataSource模块主要定义了数据接入功能，主要是将各种外部数据接入至Flink系统中，并将接入数据转换成对应的DataStream数据集。在Transformation模块定义了对DataSource数据集的各种转换操作，例如进行map、filter、windows等操作。最后，将结果数据通过DataSink模块写出到外部存储介质中，例如将数据输出到文件或Kafka消息中间件等。

## 时间概念与Watermark

Flink根据时间产生的位子不同，将时间区分为三种时间概念，分别为事件生成时间（Event Time）、事件接入时间（Ingestion Time）和事件处理时间（Processing Time）。数据从终端产生，或者从系统中产生的过程中生成的时间为事件生成时间，当数据经过消息中间件传入到Flink系统中，在DataSource中接入的时候会产生事件接入时间，当数据在Flink系统中通过各个算子实例执行转换操作的过程中，算子实例所在系统的时间为数据处理时间。用户能够根据需要选择时间类型作为对流式数据的依据，这种情况极大地增强了对事件数据处理的灵活性和准确性。

### 时间概念指定

在Flink中默认情况下使用的是Process Time时间概念，如果用户选择使用Event Time或者Ingestion Time概念，则需要在创建的StreamExectionEnvironment中调用setStream-TimeCharacteristic()方法设定系统的时间概念，如下代码使用TimeCharacteristic.EventTime作为系统的时间概念，这样对当前的StreamExecutionEnvironment会全局生效。对应的，如果使用IngestionTime概念，则通过传入TimeCharacteristic.IngestionTime参数指定。

```scala
val env=StreamExecutionEnvironment.getExecutionEnvironment()
//在系统中指定EventTime概念
env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
```

### EventTime和Watermark

通常情况下，由于网络或系统等外部因素影响，事件数据往往不能及时传输至Flink系统中，导致数据乱序到达或者延迟到达等问题，因此，需要有一种机制能够控制数据处理的过程和进度，比如基于事件时间的Window创建后，具体该如何确定属于该Window的数据元素已经全部到达。如果确定全部到达，就可以对Window的所有数据做窗口计算操作（如汇总、分组等），如果数据没有全部到达，则继续等待该窗口中的数据全部到达才开始处理。这种情况下就需要用到水位线（WaterMarks）机制，它能够衡量数据处理进度（表达数据到达的完整性），保证事件数据（全部）到达Flink系统，或者在乱序及延迟到达时，也能够像预期一样计算出正确并且连续的结果。Flink会将用读取进入系统的最新事件时间减去固定的时间间隔作为Watermark，该时间间隔为用户外部配置的支持最大延迟到达的时间长度，也就是说理论上认为不会有事件超过该间隔到达，否则就认为是迟到事件或异常事件。

简单来讲，当事件接入到Flink系统时，会在SourcesOperator中根据当前最新事件时间产生Watermarks时间戳，记为X，进入到Flink系统中的数据事件时间，记为Y，如果Y<X，则代表WatermarkX时间戳之前的所有事件均已到达，同时Window的EndTime大于Watermark，则触发窗口计算结果并输出。从另一个角度讲，如果想触发对Window内的数据元素的计算，就必须保证对所有进入到窗口的数据元素满足其事件时间Y>=X，否则窗口会继续等待Watermark大于窗口结束时间的条件满足。可以看出当有了Watermarks机制后，对基于事件时间的流数据处理会变得特别灵活，可以有效地处理乱序事件的问题，保证数据在流式统计中的结果的正确性。
