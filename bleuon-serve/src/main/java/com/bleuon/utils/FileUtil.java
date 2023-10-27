package com.bleuon.utils;

import org.springframework.http.MediaType;

import java.io.*;
import java.nio.file.Files;

/**
 * @description: 文件上传工具
 * @package: com.bleuon.utils
 * @author: zheng
 * @date: 2023/10/9
 */
public class FileUtil {

    public static boolean writeTo(InputStream inputStream, String filepath, String filename) throws IOException {
        File file = new File(filepath, filename);

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return false;
        }

        return write(inputStream, file);
    }

    public static byte[] readFrom(String filepath, String filename) throws IOException {
        File file = new File(filepath, filename);

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return null;
        }

        return Files.readAllBytes(file.toPath());
    }

    public static boolean deleteFrom(String filepath, String filename) throws IOException {
        File file = new File(filepath, filename);

        if (!file.exists()) {
            return false;
        }

        return file.delete();
    }

    public static boolean write(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        return true;
    }

    public static MediaType getContentType(String filename) {
        if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

}
