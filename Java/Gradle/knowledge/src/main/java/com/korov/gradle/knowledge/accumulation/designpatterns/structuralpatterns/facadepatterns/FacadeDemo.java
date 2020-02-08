package com.korov.gradle.knowledge.accumulation.designpatterns.structuralpatterns.facadepatterns;

public class FacadeDemo {
    private LetterProcess letterProcess = new LetterProcessImpl();
    private CheckLetter checkLetter = new CheckLetter();

    // 将写信的流程封装成一步
    public void sendLetter(String context, String address) {
        letterProcess.writeContext(context);
        letterProcess.fillEnvelope(address);

        // 寄信的同时对信件进行检查，以后有其他变动也可以很容易实现
        checkLetter.checkLetter(letterProcess);

        letterProcess.letterIntoEnvelope();
        letterProcess.sendLetter();
    }
}
