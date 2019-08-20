package com.example.rainboot.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Author 小熊
 * @Created 2019-08-20
 */
@Slf4j
public class GzipStringUtil {

    private GzipStringUtil() {
    }

    /**
     * 压缩字符串
     *
     * @param src
     * @return
     */
    public static byte[] gzip(String src) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
            gzipOutputStream.write(src.getBytes("utf-8"));
            gzipOutputStream.flush();
            gzipOutputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 解压缩
     *
     * @param bytes
     * @return
     */
    public static String ungzip(byte[] bytes) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
             InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, "utf-8");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            bufferedReader.close();
            return builder.toString();
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

}
