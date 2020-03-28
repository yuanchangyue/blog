package com.changyue.blogserver.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-28 21:02
 */
@Slf4j
public class JsoupUtils {
    public static String getHtmlData(String url) {
        Connection.Response response = null;
        String htmlData = null;
        try {
            response = Jsoup.connect(url).
                    header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(10000).ignoreContentType(true).execute();
            htmlData = response.body();
        } catch (IOException e) {
            log.info("获取html data出错：[{}]", e.getMessage());
        }
        return htmlData;
    }
}
