package com.changyue.blogserver.config.properties;

import lombok.Data;

import java.io.File;

/**
 * @author : 袁阊越
 * @description : 项目常量属性
 * @date : 2020-03-15 13:54
 */
@Data
public class BlogProperties {

    public static final String USER_HOME = System.getProperties().getProperty("user.home");

    public static final String FILE_SEPARATOR = File.separator;

    public static final String WORK_DIR = USER_HOME + FILE_SEPARATOR + "blog" + FILE_SEPARATOR;

    public static final String PREFIX = "upload";

    public static final String URL_SEPARATOR = "/";

    public static final String FILE_PROTOCOL = "file:///";

    public static final String ES_INDEX = "blog";

    public static final String ES_TYPE = "article";

    public static final int ES_TAG_POST = 0;


}
