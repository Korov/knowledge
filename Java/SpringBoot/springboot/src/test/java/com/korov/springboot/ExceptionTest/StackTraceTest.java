package com.korov.springboot.ExceptionTest;


import java.util.Scanner;

/**
 * display a trace feature of a recursive method all
 * 显示所有递归方法的跟踪特性。
 * @author aaa
 *
 */
public class StackTraceTest {
    //factorial阶乘
    public static int  factorial(int n) {
        System.out.println("factorial("+ n +"):");
        Throwable t = new Throwable();
        //使用getStackTrace方法 ，得到StackTraceElement对象一个数组，可以进行分析
        StackTraceElement[] frames  = t.getStackTrace();
        for (StackTraceElement f : frames) {
            System.out.println(f);
        }
        int r;
        if (n <= 1) {
            r = 1;
        }else {
            r=n*factorial(n-1);
        }
        System.out.println("return"+r);
        return r;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter n :");
        int n = in.nextInt();
        factorial(n);
    }
}
