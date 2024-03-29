package com.example.flux.httpd;



import com.example.flux.httpd.json.JSONParser;
import com.example.flux.httpd.json.model.JsonObject;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ContentTypeUtils
 *
 * @author coolblog.xyz
 * @date 2018-03-27 21:57:42
 */
public class ContentTypeUtils {

    private static JsonObject jsonObject;

    private static final String JSON_PATH = "src/test/resources/static/meta/content-type.json";

    static {
        JSONParser jsonParser = new JSONParser();
        try {
            jsonObject = (JsonObject) jsonParser.fromJSON(readFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile() {
        try {
            RandomAccessFile raf = new RandomAccessFile(JSON_PATH, "r");
            FileChannel channel = raf.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            buffer.clear();
            channel.read(buffer);
            buffer.flip();
            return new String(buffer.array());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getContentType(String ext) {
        return (String) jsonObject.get(ext);
    }
}
