package com.fastdfs.demo.demo;

import com.fastdfs.demo.ApplicationTests;
import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

public class FastdfsTest extends ApplicationTests {
    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;

    @Autowired
    private FastFileStorageClient storageClient;

    @Test
    public void test1() throws IOException {
        InputStream inputStream = fastDFSClientUtil.download("group1", "M00/00/00/wKgfVF4inUOAenOjAAAA3LAgNvo2536.md");


        byte[] buf = new byte[1024];// 一次读取1024个字节
        int len;
        // buf中包含回车和换行
        len = inputStream.read(buf);
        String str = new String(buf, 0, len);
        System.out.println(str);
    }

    @Test
    public void test2() {
        FileInfo file = storageClient.queryFileInfo("group1", "M00/00/00/wKgfVF4inUOAenOjAAAA3LAgNvo2536.md");
        System.out.println("debug");
    }
}
