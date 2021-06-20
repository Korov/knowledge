package org.algorithms.example.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileIterable implements Iterable<int[]> {
    private final String fileName;

    public FileIterable(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Iterator<int[]> iterator() {
        File file = new File(fileName);
        List<int[]> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                int[] array = Arrays.stream(line.split(",")).mapToInt(Integer::valueOf).toArray();
                result.add(array);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.iterator();
    }
}
