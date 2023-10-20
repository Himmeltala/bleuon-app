package com.bleuon.utils;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @description: 文件上传工具
 * @package: com.bleuon.utils
 * @author: zheng
 * @date: 2023/10/9
 */
@Component
public class FileUtil {

    public boolean writeToResources(String filepath, String filename, InputStream inputStream) throws IOException {
        File file = getFileFromResources(filepath, filename);

        if (file != null) {
            try (inputStream; FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public byte[] readFileFromResources(String filepath, String filename) throws IOException {
        File file = getFileFromResources(filepath, filename);

        if (file != null && file.exists()) {
            try {
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public File getFileFromResources(String filepath, String filename) throws IOException {
        File resourceDirectory = ResourceUtils.getFile("classpath:");
        File file = new File(resourceDirectory, filepath + File.separator + filename);

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return null;
        }

        return file;
    }

    public boolean deleteFileFromResources(String filepath, String filename) throws IOException {
        boolean success;
        File file = getFileFromResources(filepath, filename);

        if (file.exists()) {
            success = file.delete();
        } else {
            success = false;
        }

        return success;
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
