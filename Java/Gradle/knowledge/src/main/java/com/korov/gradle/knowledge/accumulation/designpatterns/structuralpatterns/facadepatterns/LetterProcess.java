package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.facadepatterns;

/**
 * 写信需要分为四个步骤
 */
public interface LetterProcess {
    // 首先要写信的内容
    public void writeContext(String context);

    // 其次在信封上写地址
    public void fillEnvelope(String address);

    // 把信放到信封
    public void letterIntoEnvelope();

    // 邮递
    public void sendLetter();
}
