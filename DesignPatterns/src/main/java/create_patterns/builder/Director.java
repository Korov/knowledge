package create_patterns.builder;

/**
 * 用Director构建最后的复杂对象，而在上面Builder接口中封装的是如何创建一个个部件，
 * Director的内容是如何将部件最后组装成成品
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    // 将部件partA partB partC 最后组成复杂对象
    // 这里是将车轮 方向盘和发动机组装成汽车的过程
    public void construct() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
    }
}
