package com.korov.springboot.BaseTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

@Slf4j
public class ActionListenerImpl implements ActionListener {
    private Logger logger = LoggerFactory.getLogger(ActionListenerImpl.class);

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        logger.info("time is start");
    }

    @Test
    public void test() {
        ActionListener listener = new ActionListenerImpl();
        Timer timer = new Timer(1000, listener);
        timer.start();
        JOptionPane.showMessageDialog(null, "quit");
        String[] words = new String[]{};
        Arrays.sort(words, (first, second) -> first.length() - second.length());

        Timer timer1 = new Timer(1000, event -> {
            logger.info("time to to");
        });
        System.exit(0);
    }

}
