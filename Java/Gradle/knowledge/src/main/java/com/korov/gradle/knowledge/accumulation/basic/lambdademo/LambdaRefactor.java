package com.korov.gradle.knowledge.accumulation.basic.lambdademo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 使用lambda表达式优化代码，
 * 可以将函数式接口的引用存在某个地方，整整执行的时候执行的是传递过来的代码块
 *
 * @author korov
 */
public class LambdaRefactor {
    private void delete(String value) {
        System.out.println("delete" + value);
    }

    private void add(String value) {
        System.out.println("add" + value);
    }

    private void update(String value) {
        System.out.println("update" + value);
    }

    public void operator(String operation) throws Exception {
        switch (operation) {
            case "delete":
                delete("demo");
                break;
            case "add":
                add("demo");
                break;
            case "update":
                update("demo");
                break;
            default:
                throw new Exception();
        }
    }

    /**
     * 扫操作map中可以直接村代码块
     *
     * @param operation
     */
    public void operator1(String operation) {
        // 接收一个参数没有返回值，Consumer函数式接口最适合
        // 如果返回值和参数数量不一致可以去java提供的基本函数式接口中寻找适配的，没有的话就自己定义一个
        Map<String, Consumer<String>> operators = new HashMap<>(16);
        operators.put("delete", this::delete);
        operators.put("add", this::add);
        operators.put("update", this::update);
        if (operators.containsKey(operation)) {
            operators.get(operation).accept("demo");
        }
    }

    public void operator2(Operator operator) {
        operator.operator("demo");
    }
}

/**
 * 选择合适的函数式接口，将代码块存储在枚举值的构造函数中
 */
enum Operator {
    /**
     * 添加操作
     */
    ADD(x -> System.out.println("add" + x)),
    /**
     * 删除操作
     */
    DELETE(x -> System.out.println("delete" + x)),
    /**
     * 更新操作
     */
    UPDATE(x -> System.out.println("update" + x));

    private Consumer<String> function;

    Operator(Consumer<String> operation) {
        function = operation;
    }

    public void operator(String operation) {
        function.accept(operation);
    }
}