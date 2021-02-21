package main;

import util1.StringUtil1;

public class Main {
    public static void main(String[] args) {
        // 只能使用StringUtil1，因为只有util1被exports了，util2并没有被exports
        StringUtil1.print("Hello");
    }
}
