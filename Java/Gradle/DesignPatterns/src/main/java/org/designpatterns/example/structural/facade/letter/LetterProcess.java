package org.designpatterns.example.structural.facade.letter;

/**
 * 写信需要分为四个步骤
 *
 * @author korov
 */
public interface LetterProcess {
    /**
     * 首先要写信的内容
     *
     * @param context
     */
    void writeContext(String context);

    /**
     * 其次在信封上写地址
     *
     * @param address
     */
    void fillEnvelope(String address);

    /**
     * 把信放到信封
     */
    void letterIntoEnvelope();

    /**
     * 邮递
     */
    void sendLetter();
}
