package com.example.rainboot.system.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 上传文件工具类
 *
 * @version 1.0
 * @Author 小熊
 * @Created 2018/11/5 19:53
 */
@Slf4j
public class UploadUtils extends HttpServlet {
    private String filePath = "";

    /**
     * 上传
     *
     * @param file 文件
     * @param path 路径
     * @return 文件路径
     */
    public static String upload(MultipartFile file, String path) {
        if (file.isEmpty()) {
            return "false";
        }
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "false";
        }
        File dest = new File(path + "/" + fileName);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return dest.getAbsolutePath();
        } catch (IllegalStateException | IOException e) {
            log.error("保存失败：", e);
            return "false";
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
