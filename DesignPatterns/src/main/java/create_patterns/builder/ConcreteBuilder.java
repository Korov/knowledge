package create_patterns.builder;

public class ConcreteBuilder implements Builder {
    Part partA;
    Part partB;
    Part partC;

    @Override
    public void buildPartA() {
        // 构建部件A
    }

    @Override
    public void buildPartB() {
// 构建部件B
    }

    @Override
    public void buildPartC() {
// 构建部件C
    }

    @Override
    public Product getResult() {
        return null;
    }
}
