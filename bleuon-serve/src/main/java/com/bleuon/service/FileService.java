package com.bleuon.service;

import com.bleuon.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/10
 */
@Service
@RequiredArgsConstructor
public class FileService {

    /**
     * @param filepath 写入的目录
     * @param rename   重命名文件
     * @param file     上传的文件
     * @return 返回一个存储的文件路径
     */
    public String upload(String filepath, String rename, MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();

            if (filename != null && filename.lastIndexOf(".") != -1) {
                String extension = filename.substring(filename.lastIndexOf(".") + 1);
                String uuid = UUID.randomUUID().toString();
                if (StringUtils.hasText(rename)) {
                    filename = rename + "_" + uuid + "." + extension;
                } else {
                    String prefix = filename.substring(0, filename.lastIndexOf('.'));
                    filename = prefix + "_" + uuid + "." + extension;
                }
                boolean success = FileUtil.writeTo(file.getInputStream(), filepath, filename);
                if (success) {
                    return "http://localhost:8080/api/public/file/preview/image?filepath=" + filepath + "&filename=" + filename;
                }
            }

            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] download(String filepath, String filename) {
        try {
            return FileUtil.readFrom(filepath, filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String filepath, String filename) {
        try {
            return FileUtil.deleteFrom(filepath, filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
