package com.example.rainboot.application.aurora.pojo.vo;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * 号码包上传参数
 *
 * @author 小熊
 */
@Data
@ToString
public class UploadNumberPackageVO implements Serializable {
    /**
     * 号码包名称
     */
    private String name;
    /**
     * 自定义外部id
     */
    private String sourceId;
    /**
     * 号码包数据类型；1 IMEI, 2 PHONE, 3 MAC, 4 IMEI-MD5, 5 PHONE-MD5, 6 MAC-MD5
     */
    private Integer dataType;
    /**
     * ⽀付类型，0 测试 1 正式
     */
    private Integer payType;
    /**
     * 标签 id 集合
     */
    private List<String> tagsIds;
    /**
     * 号码包⽂件 (只⽀持 txt 格式 ⼀条数据⼀⾏)
     */
    private MultipartFile file;
}
