package com.liuyang.code.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Liuyang 2016/2/11.
 */
public class FileUtils {

    /**
     * 一行一行读取文本, 取出第一行匹配给定pattern的数据.
     */
    public static String retrivePattern(String fileName, String pattern) {
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            Pattern p = Pattern.compile(pattern);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.find()) return m.group();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(bufferedReader);
            close(fileReader);
        }
        return null;
    }

    private static void close(Closeable c) {
        if (c != null) try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
