package com.changyue.blogserver.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * @author : 袁阊越
 * @description : 读取json文件
 * @date : 2020-03-23 13:56
 */
@Slf4j
public class ReadJsonFileUtils {

    public static String readJsonFile() {
        StringBuilder jsonContent = new StringBuilder();
        BufferedReader reader = null;
        FileInputStream fis;
        log.info("正在读取json文件");
        try {
            File jsonFile = ResourceUtils.getFile("classpath:query.json");
            fis = new FileInputStream(jsonFile);
            reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String tempStr = "";
            if ((tempStr = reader.readLine()) != null) {
                jsonContent.append(tempStr);
            }
        } catch (Exception e) {
            log.info("json文件读取失败：[{}]", e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.info("BufferedReader关闭失败：[{}]" + e.getMessage());
                }
                log.info("BufferedReader已经关闭");
            }
        }
        return jsonContent.toString();
    }


}
