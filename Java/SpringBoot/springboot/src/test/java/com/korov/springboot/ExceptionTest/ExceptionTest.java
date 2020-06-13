package com.korov.springboot.ExceptionTest;

import com.korov.springboot.SpringbootApplicationTests;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ExceptionTest extends SpringbootApplicationTests {

    @Test
    public void test() throws Throwable {
        int[] a = {1, 1, 1};
        try {
            a[-1] = 8;
        } catch (ArrayIndexOutOfBoundsException e) {
            Throwable se = new ArrayIndexOutOfBoundsException("下标越界");

            //将原始异常设置为新异常的原因
            //当捕获到异常时，就可以使用   Throwable e = se.getCause();  重新得到原始异常
            //强烈推荐这种包装技术，这样可以让用户抛出子系统中的高级异常，而不会丢失原始异常的细节
            se.initCause(e);
            throw se;
        }

    }

    /**
     * 注释的三种情况
     */
    @Test
    public void test1() throws Exception {

        //代码2
        //若一段代码前有异常抛出，并且这个异常被try...catch所捕获，若此时catch语句中没有抛出新的异常，则这段代码能够被执行，否则，下面不被执行
        try {
            throw new Exception("参数越界");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("异常后");//可以执行

        //代码2
        //若在一个条件语句中抛出异常，则程序能被编译，但后面的语句不会被执行。如代码3
        if (true) {
            throw new Exception("参数越界");
        }
        System.out.println("异常后"); //抛出异常，不会执行

        //代码1
        //若一段代码前有异常抛出，并且这个异常没有被捕获，这段代码将产生编译时错误「无法访问的语句」。如代码1
        throw new Exception("参数越界");
        //System.out.println("异常后"); //编译错误，「无法访问的语句」
    }

    @Test
    public void test3() throws Throwable {
        String value = "test";
        List<String> list = new ArrayList<>();
        try {
            list.get(4);
        } catch (ArrayIndexOutOfBoundsException e) {
            Throwable se = new FileFormatException("get Exception");
            se.initCause(e);
            throw se;
        }
    }
}
