package com.korov.gradle.knowledge.accumulation.designpatterns.demo.dispatch;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {
    static class ClassA {
        public void println(String s) {
            System.out.println("ClassA:" + s);
        }
    }

    public static void main(String[] args) throws Throwable {
        // 运行的时候确定使用自己定义的println方法还是系统定义的方法
        Object object = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
        // 无论obj最终是哪个实现类，下面这句都能正确调用到println方法
        getPrintlnMH(object).invokeExact("icyfenix");
    }

    private static MethodHandle getPrintlnMH(Object reveiver) throws
            Throwable {
        // MethodType：代表“方法类型”，包含了方法的返回值（methodType()的第一个参数和具体的参数methodType()的第二个及以后的参数
        MethodType mt = MethodType.methodType(void.class, String.class);
        /**
         * lookup()方法来自于MethodHandleslookup，这句的作用是在指定类中查找符合给定的
         * 方法名称、方法类型，并且符合调用权限的方法句柄
         * 因为这里调用的是一个虚方法，按照Java语言的规则，方法第一个参数是隐式的，代表该方法的接收者，
         * 也即是this指向的对象，这个参数以前是放在参数列表中进行传递的，而现在提供了bindTo()方法来完成则这件事情
         */
        return MethodHandles.lookup().findVirtual(reveiver.getClass(), "println", mt).bindTo(reveiver);
    }
}
