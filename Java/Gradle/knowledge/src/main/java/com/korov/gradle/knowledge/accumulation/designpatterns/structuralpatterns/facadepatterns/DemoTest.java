package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.facadepatterns;

public class DemoTest {
    public static void main(String[] args) {
        // 以前寄信
        LetterProcess letterProcess = new LetterProcessImpl();
        letterProcess.writeContext("写信了");
        letterProcess.fillEnvelope("填写地址");
        letterProcess.letterIntoEnvelope();
        letterProcess.sendLetter();

        /**
         * 现在写信只要一个方法就可以
         *
         * 外观模式为子系统中的一组接口提供一个一致的界面
         */
        FacadeDemo facadeDemo = new FacadeDemo();
        facadeDemo.sendLetter("写信", "地址");
    }
}
