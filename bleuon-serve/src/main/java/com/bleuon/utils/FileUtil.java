package com.bleuon.utils;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @description: 文件上传工具
 * @package: com.bleuon.utils
 * @author: zheng
 * @date: 2023/10/9
 */
@Component
public class FileUtil {

    public boolean writeTo(InputStream inputStream, String filepath, String filename) throws IOException {
        File file = new File(filepath, filename);

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return false;
        }

        return write(inputStream, file);
    }

    public byte[] readFrom(String filepath, String filename) throws IOException {
        File file = new File(filepath, filename);

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return null;
        }

        return Files.readAllBytes(file.toPath());
    }

    public boolean deleteFrom(String filepath, String filename) throws IOException {
        File file = new File(filepath, filename);

        if (!file.exists()) {
            return false;
        }

        return file.delete();
    }

    public boolean write(InputStream inputStream, File file) throws IOException {
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

    public MediaType getContentType(String filename) {
        if (filename.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

}
