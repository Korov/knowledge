package com.korov.springboot.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ExecCalc {
    private static final Logger log = LoggerFactory.getLogger(ExecCalc.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Process proc = Runtime.getRuntime().exec("cmd.exe /c calc");
        boolean waitFor = proc.waitFor(5, TimeUnit.SECONDS);
        if (!waitFor) {
            log.error(" {}  command execute faild.", "cmd.exe /k dir");

        } else {
            log.info("执行成功");
        }
    }
}
