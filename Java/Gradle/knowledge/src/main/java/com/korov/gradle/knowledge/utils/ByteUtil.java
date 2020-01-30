package com.korov.gradle.knowledge.utils;

public class ByteUtil {
    public static byte[] parseIntToBytes(int value) {
        byte[] bytes = new byte[4];
        int offset;
        for (int i = 0; i < 4; i++) {
            offset = 32 - (i + 1) * 8;
            bytes[i] = (byte) ((value >> offset) & 0xff);
        }
        return bytes;
    }

    public static int parseBytesToInt(byte[] values) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value <<= 8;
            value |= (values[i] & 0xff);
        }
        return value;
    }

    public static byte[] parseLongToBytes(long num) {
        byte[] bytes = new byte[8];
        int offset;
        for (int i = 0; i < 8; ++i) {
            offset = 64 - (i + 1) * 8;
            bytes[i] = (byte) ((num >> offset) & 0xff);
        }
        return bytes;
    }

    public static long parseBytesToLong(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < 8; ++i) {
            value <<= 8;
            value |= (bytes[i] & 0xff);
        }
        return value;
    }

    public static byte[] parseStringToBytes(String value) {
        return value.getBytes();
    }

    public static String parseBytesToString(byte[] values) {
        return new String(values);
    }
}
