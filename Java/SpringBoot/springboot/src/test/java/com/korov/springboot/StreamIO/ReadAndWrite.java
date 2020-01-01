package com.korov.springboot.StreamIO;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadAndWrite {
    @Test
    public void test() {
        File inFile = new File("src\\test\\resources\\test\\error1.txt");
        File outFile = new File("src\\test\\resources\\test\\error1_out.txt");
        String[] filters = new String[]{"App_*", "123"};
        filterError(inFile, filters, outFile);
    }

    private void filterError(File inFile, String[] filters, File outFile) {
        Map<String, List<String>> resultMap = generateResultMap(filters);
        List<ErrorMsg> errorMsgs = generateErrorMsg(inFile);
        generateMatchMsg(filters, errorMsgs, resultMap);
        writeToFile(resultMap, outFile);
    }


    private Map<String, List<String>> generateResultMap(String[] filters) {
        Map<String, List<String>> resultMap = new LinkedHashMap<>(10);
        List<String> erroeNos = new ArrayList<String>(10);
        resultMap.put("NotMatch", erroeNos);
        for (String filter : filters) {
            erroeNos = new ArrayList<String>(10);
            resultMap.put(filter, erroeNos);
        }
        return resultMap;
    }

    private List<ErrorMsg> generateErrorMsg(File inFile) {
        List<ErrorMsg> result = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(inFile.getAbsolutePath()), StandardCharsets.UTF_8)) {
            List<String> allLines = lines.filter(line -> !isMatch(" *", line)).collect(Collectors.toList());
            Iterator<String> iterator = allLines.iterator();
            while (iterator.hasNext()) {
                String value = iterator.next();
                if (isMatch("^Error +#\\d *", value)) {
                    ErrorMsg errorMsg = new ErrorMsg(value, iterator.next());
                    result.add(errorMsg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void generateMatchMsg(String[] filters, List<ErrorMsg> errorMsgs, Map<String, List<String>> resultMap) {
        Iterator<ErrorMsg> iterator = errorMsgs.iterator();
        boolean matched = false;
        while (iterator.hasNext()) {
            ErrorMsg errorMsg = iterator.next();
            matched = false;
            for (String filter : filters) {
                if (isMatch(filter.replace("*", ".*"), errorMsg.getErrorMsg().trim())) {
                    resultMap.get(filter).add(errorMsg.getResultNo());
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                resultMap.get("NotMatch").add(errorMsg.getResultNo());
            }
        }
    }

    private void writeToFile(Map<String, List<String>> resultMap, File outFile) {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outFile.getAbsolutePath()), StandardCharsets.UTF_8), true);
            out.print("");//覆盖写入，将以前的数据都删掉然后写入
            StringBuffer stringBuffer = new StringBuffer();
            for (Map.Entry<String, List<String>> entry : resultMap.entrySet()) {
                stringBuffer.append(entry.getKey()).append(":").append(entry.getValue().size());
                if (entry.getValue().size() == 0) {
                    stringBuffer.append("\n");
                    out.append(stringBuffer.toString());
                } else {
                    stringBuffer.append(",").append(String.join(",", entry.getValue())).append("\n");
                    out.append(stringBuffer.toString());
                }
                stringBuffer.setLength(0);
            }
            out.flush();//不使用的话不会输入到文件中
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isMatch(String regex, String content) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }
}
