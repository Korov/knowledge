package org.designpatterns.example.structural.facade.facade;

import org.designpatterns.example.structural.facade.letter.CheckLetter;
import org.designpatterns.example.structural.facade.letter.LetterProcess;
import org.designpatterns.example.structural.facade.letter.impl.LetterProcessImpl;

public class FacadeDemo {
    private static LetterProcess letterProcess = new LetterProcessImpl();
    private static CheckLetter checkLetter = new CheckLetter();

    // 将写信的流程封装成一步
    public static void sendLetter(String context, String address) {
        letterProcess.writeContext(context);
        letterProcess.fillEnvelope(address);

        // 寄信的同时对信件进行检查，以后有其他变动也可以很容易实现
        checkLetter.checkLetter(letterProcess);

        letterProcess.letterIntoEnvelope();
        letterProcess.sendLetter();
    }
}
