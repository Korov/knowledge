package com.korov.springboot.exception;

public class TestException extends ArrayIndexOutOfBoundsException {

    //默认构造器
    public TestException() {
    }

    //带有详细信息的构造器
    public TestException(String msg) {
        super(msg);
    }

}
