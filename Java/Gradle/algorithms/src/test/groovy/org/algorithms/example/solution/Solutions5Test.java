package org.algorithms.example.solution;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Solutions5Test {
    private static final Logger log = LoggerFactory.getLogger(Solutions5Test.class);

    @Test
    void longestPalindrome() {
        String value;
        String result;
        value = "babad";
        result = Solutions5.longestPalindrome(value);
        log.info("result:{}", result);
    }

    @Test
    void longestPalindrome1() {
        String value;
        String result;
        value = "cbbd";
        result = Solutions5.longestPalindrome(value);
        log.info("result:{}", result);
    }
}