package com.ouroboros.toolutils.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TestFileUtil {

    public static void main(String[] args) throws Exception {
        testFile();
    }

    @Data
    static class Test {
        String testName;
        Integer times;
    }

    private static void testFile() throws Exception {
        InputStream inputStream = new FileInputStream("C:\\Users\\3970\\Desktop\\test\\test_report_data.txt");
        File file = new File("C:\\Users\\3970\\Desktop\\test\\test_report_data_out1.txt");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        do {
            line = reader.readLine();
            if (line != null) {
                out.write(line);
            }
            // Process line by line.
        } while (line != null);
        out.flush();
        out.close();
    }
}
