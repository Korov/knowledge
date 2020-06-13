package com.korov.springboot.StreamIO;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class GetPath {
    @Test
    public void test() {
        String path = System.getProperty("user.dir");//当前的工作目录
        System.out.println(path);
        //获取绝对路径下的文件   new FileInputStream("D:\\MyFile\\Knowledge\\springboot\\src\\test\\resources\\test\\employee.dat")
        try (FileInputStream fin = new FileInputStream("src\\test\\resources\\test\\employee.dat")) {
            int b = fin.read();
            System.out.println("get stream：绝对路径");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reader fin = null;
        try {
            fin = new InputStreamReader(new FileInputStream("src\\test\\resources\\test\\data.txt"), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //默认情况下自动冲刷机制是禁用的
        try (PrintWriter out = new PrintWriter("src\\test\\resources\\test\\datacopy.txt", "UTF-8")) {
            out.print("123\n456");//覆盖写入，将以前的数据都删掉然后写入
            out.append("\7891111");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开启自动冲刷机制
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("src\\test\\resources\\test\\datacopy.txt"), StandardCharsets.UTF_8), true);
            out.print("123\n456\n789");//覆盖写入，将以前的数据都删掉然后写入
            out.append("\7891111");
            out.flush();//不使用的话不会输入到文件中
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\test\\data.txt")), StandardCharsets.UTF_8);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> lists = Files.readAllLines(Paths.get("src\\test\\resources\\test\\data.txt"), StandardCharsets.UTF_8);
            System.out.println("list start");
            lists.stream().forEach(System.out::println);
            System.out.println("list end");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> lines = Files.lines(Paths.get("src\\test\\resources\\test\\data.txt"), StandardCharsets.UTF_8)) {
            System.out.println("stream start");
            lines.forEach(System.out::println);
            System.out.println("stream end");
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("end");
    }
}
