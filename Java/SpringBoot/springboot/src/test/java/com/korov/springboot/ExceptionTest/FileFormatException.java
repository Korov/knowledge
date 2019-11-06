package com.korov.springboot.ExceptionTest;

import java.io.IOException;

public class FileFormatException extends IOException {
    public FileFormatException() {
    }

    public FileFormatException(String grip) {
        super(grip);
    }
}
