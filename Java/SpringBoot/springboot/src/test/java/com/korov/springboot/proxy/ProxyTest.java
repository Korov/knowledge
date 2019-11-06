package com.korov.springboot.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

public class ProxyTest {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];
        for (int i = 0; i < elements.length; i++) {
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            // 在运行时定义类，这个类实现了Comparable接口，但他的comparTo方法调用了代理对象处理器的invoke方法
            Object proxy = Proxy.newProxyInstance(null, new Class[]{Comparable.class}, handler);
            elements[i] = proxy;
        }
        Integer key = new Random().nextInt(elements.length) + 1;
        // 二分查找会使用compareTo方法进行对比
        int result = Arrays.binarySearch(elements, key);
        if (result >= 0) System.out.println(elements[result]);

        // proxy对象实例中的compareTo方法为invoke方法
        Integer value = 4;
        Comparable comparable = (Comparable) elements[3];
        comparable.compareTo(value);
    }

    static class TraceHandler implements InvocationHandler {
        private Object target;

        public TraceHandler(Object value) {
            target = value;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            System.out.printf("%s.%s(", target, method.getName());
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    System.out.printf("%s", args[i]);
                    if (i < args.length - 1) System.out.printf(",");
                }
            }
            System.out.printf(")\n");
            return method.invoke(target, args);
        }
    }
}
