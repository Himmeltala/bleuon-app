package com.bleuon.service;

import com.bleuon.utils.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description:
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/10
 */
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileUtil fileUtil;

    /**
     * @param filepath   写入的目录
     * @param refilename 重命名文件
     * @param file       上传的文件
     * @return 返回一个存储的文件路径
     */
    public String upload(String filepath, String refilename, MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();

            if (filename != null && filename.lastIndexOf(".") != -1) {
                String extension = filename.substring(filename.lastIndexOf(".") + 1);
                if (StringUtils.hasText(refilename)) {
                    filename = refilename + "." + extension;
                }
                boolean success = fileUtil.writeToResources(filepath, filename, file.getInputStream());
                if (success) {
                    return "http://localhost:8080/api/public/file/preview/image?filepath=" + filepath + "&filename=" + filename;
                }
            }

            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] download(String filepath, String filename, HttpServletResponse response) {
        try {
            MediaType contentType = fileUtil.getContentType(filename);
            response.setContentType(contentType.toString());
            return fileUtil.readFileFromResources(filepath, filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String filepath, String filename) {
        try {
            return fileUtil.deleteFileFromResources(filepath, filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
