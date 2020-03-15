package com.changyue.blogserver.model.rep;

import lombok.Data;
import org.springframework.http.MediaType;

/**
 * @author : 袁阊越
 * @description : 上传
 * @date : 2020-03-12 11:25
 */
@Data
public class UploadResult {
    /**
     * 文件名
     */
    private String filename;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     *
     */
    private String key;

    /**
     * 缩略图路径
     */
    private String thumbPath;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 文件类型
     */
    private MediaType mediaType;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;
}
