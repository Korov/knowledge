package com.korov.gradle.knowledge.accumulation.thread.exchanger;

import java.util.concurrent.Exchanger;

/**
 * 用于两个线程之间交换信息
 * 可简单地将Exchanger对象理解为一个包含两个格子的容器，
 * 通过exchanger方法可以向两个格子中填充信息。当两个格子中的均被填充时，
 * 该对象会自动将两个格子的信息交换，然后返回给线程，从而实现两个线程的信息交换。
 *
 * xchanger类仅可用作两个线程的信息交换，当超过两个线程调用同一个exchanger对象时，
 * 得到的结果是随机的，exchanger对象仅关心其包含的两个“格子”是否已被填充数据，
 * 当两个格子都填充数据完成时，该对象就认为线程之间已经配对成功，然后开始执行数据交换操作。
 */
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + exchanger.exchange("string1"));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + exchanger.exchange("string2"));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
