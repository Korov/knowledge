package com.korov.springboot.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.tomcat.util.file.ConfigurationSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Log4j2 {
    private static final Logger logger = LogManager.getLogger(Log4j2.class);



    public static void main(String[] args) {
        logger.debug("hello world...{}", "How are you");
        logger.debug("debug");
        logger.info("info");
        logger.error("error");
    }
}
