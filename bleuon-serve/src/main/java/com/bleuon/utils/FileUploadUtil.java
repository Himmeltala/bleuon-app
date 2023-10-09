package com.bleuon.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description: 文件上传工具
 * @package: com.bleuon.utils
 * @author: zheng
 * @date: 2023/10/9
 */
@Component
public class FileUploadUtil {

    public boolean writeToResources(String path, String fileName, InputStream inputStream) throws IOException {
        File file = getFileFromResources(path, fileName);

        if (file != null) {
            // 将输入流中的数据写入文件
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return true;
        } else return false;
    }

    private File getFileFromResources(String path, String fileName) throws IOException {
        // 获取资源目录的绝对路径
        File resourceDirectory = ResourceUtils.getFile("classpath:");
        // 创建一个临时文件，可以指定存放的目录
        File file = new File(resourceDirectory + "\\" + path, fileName);
        // 确保父目录存在
        if (!file.getParentFile().exists()) {
            boolean success = file.getParentFile().mkdirs();
            if (!success) return null;
        }
        return file;
    }

}
