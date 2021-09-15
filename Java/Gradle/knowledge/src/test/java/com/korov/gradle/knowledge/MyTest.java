package com.korov.gradle.knowledge;

import com.fasterxml.uuid.Generators;
import com.github.f4b6a3.uuid.UuidCreator;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MyTest {

    public static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    public static String toString(UUID uuid) {
        return (digits(uuid.getLeastSignificantBits(), 12)) +
                digits(uuid.getMostSignificantBits() >> 16, 4) +
                digits(uuid.getMostSignificantBits(), 4) +
                digits(uuid.getLeastSignificantBits() >> 48, 4) +
                digits(uuid.getMostSignificantBits() >> 32, 8);
    }

    @Test
    // https://stackoverflow.com/questions/18244897/how-to-generate-time-based-uuids
    public void test() {
        int count = 1000000;
        // https://github.com/cowtowncoder/java-uuid-generator
        Set<String> uuidSet = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UUID uuid = Generators.timeBasedGenerator().generate();
            String uid = toString(uuid);
            //System.out.println("UUID : " + uid);
            uuidSet.add(uid);
        }

        if (uuidSet.size() != count) {
            System.out.println("bad " + uuidSet.size());
        }

        System.out.println();

        // https://github.com/f4b6a3/uuid-creator
        uuidSet.clear();
        for (int i = 0; i < count; i++) {
            UUID uuid = UuidCreator.getTimeBased();
            System.out.println(uuid.toString());
            String uid = toString(uuid);
            //System.out.println(uid);
        }
        if (uuidSet.size() != count) {
            System.out.println("bad " + uuidSet.size());
        }

    }

    @Test
    public void test1() {
        int count = 1000000;
        String value = "7f61a06f70a818e411eb847a6c42369b";
        System.out.println(value.length());
        // https://github.com/cowtowncoder/java-uuid-generator
        Set<String> uuidSet = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UUID uuid = Generators.timeBasedGenerator().generate();
            System.out.println(uuid.toString());
            String uid = toString(uuid);
            //System.out.println("UUID : " + uid);
        //    uuidSet.add(uid);
        }
    }

    @Test
    public void test11() {

    }
}
