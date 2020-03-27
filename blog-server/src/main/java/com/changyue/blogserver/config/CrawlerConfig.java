package com.changyue.blogserver.config;

import com.changyue.blogserver.crawler.callback.DataValidateCallBack;
import com.changyue.blogserver.crawler.helper.CookieHelper;
import com.changyue.blogserver.crawler.helper.CrawlerHelper;
import com.changyue.blogserver.crawler.model.CrawlerConfigProperty;
import com.changyue.blogserver.crawler.parse.ParseRule;
import com.changyue.blogserver.crawler.proxy.CrawlerProxyProvider;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.scheduler.RedisAndDBScheduler;
import com.changyue.blogserver.utils.crawler.SeleniumClient;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫
 * @date : 2020-03-23 21:04
 */
@Getter
@Setter
@Configuration
@PropertySource("classpath:crawler.properties")
@ConfigurationProperties(prefix = "crawler.init.url")
public class CrawlerConfig {

    /**
     * url 前缀
     */
    private String prefix;
    /**
     * url 后缀
     */
    private String suffix;

    /**
     * cookie 名称
     */
    @Value("${crux.cookie.name}")
    private String cookieName;

    /**
     * 是否使用代理
     */
    @Value("${proxy.isUsedProxyIp}")
    private boolean isUsedProxyIp;

    /**
     * csdn 初始化页面抓取的规则
     */
    @Value("${csdn.initCrawlerXpath}")
    private String initCrawlerXpath;

    /**
     * csdn 下一个页面的抓取规则
     */
    @Value("${csdn.helpCrawlerXpath}")
    private String helpCrawlerXpath;

    /**
     * #csdn 分页范围
     */
    @Value("${csdn.crawlerHelpNextPagingSize}")
    private Integer crawlerHelpNextPagingSize;

    /**
     * 获得爬虫的url
     *
     * @return url list
     */
    public List<String> getCrawlerUrlList() {
        List<String> crawlerUrlList = new ArrayList<>();

        if (StringUtils.isNotEmpty(suffix) && StringUtils.isNotEmpty(prefix)) {
            String[] suffixStrArr = suffix.split(",");
            String tempUrl;
            if (suffixStrArr.length > 0) {
                for (String suffix : suffixStrArr) {
                    tempUrl = prefix + suffix;
                    crawlerUrlList.add(tempUrl);
                }
            }
        }
        return crawlerUrlList;
    }

    /**
     * 注册SeleniumClient到Spring中
     *
     * @return SeleniumClient
     */
    @Bean
    public SeleniumClient getSeleniumClient() {
        return new SeleniumClient();
    }

    /**
     * 设置Cookie辅助类
     *
     * @return Cookie辅助类
     */
    @Bean
    public CookieHelper getCookieHelper() {
        return new CookieHelper(cookieName);
    }

    /**
     * 数据校验匿名内部类
     *
     * @param cookieHelper cookieHelper
     * @return DataValidateCallBack
     */
    private DataValidateCallBack getDataValidateCallBack(CookieHelper cookieHelper) {
        return content -> {
            boolean flag = true;
            if (StringUtils.isEmpty(content)) {
                flag = false;
            } else {
                boolean isContains_acw_sc_v2 = content.contains("acw_sc__v2");
                boolean isContains_location_reload = content.contains("document.location.reload()");
                if (isContains_acw_sc_v2 && isContains_location_reload) {
                    flag = false;
                }
            }
            return flag;
        };
    }

    /**
     * CrawlerHelper 辅助类
     *
     * @return CrawlerHelper
     */
    @Bean
    public CrawlerHelper getCrawlerHelper() {
        CookieHelper cookieHelper = getCookieHelper();
        CrawlerHelper crawlerHelper = new CrawlerHelper();
        DataValidateCallBack dataValidateCallBack = getDataValidateCallBack(cookieHelper);
        crawlerHelper.setDataValidateCallBack(dataValidateCallBack);
        return crawlerHelper;
    }


    /**
     * 代理提供者
     *
     * @return CrawlerProxyProvider
     */
    @Bean
    public CrawlerProxyProvider getCrawlerProxyProvider() {
        CrawlerProxyProvider crawlerProxyProvider = new CrawlerProxyProvider();
        crawlerProxyProvider.setUsedProxyIp(isUsedProxyIp);
        return crawlerProxyProvider;
    }


    @Bean
    public CrawlerConfigProperty getCrawlerConfigProperty() {
        CrawlerConfigProperty property = new CrawlerConfigProperty();
        //初始化url列表
        property.setInitCrawlerUrlList(getCrawlerUrlList());
        //初始化url解析规则定义
        property.setInitCrawlerXpath(initCrawlerXpath);
        //用户空间下的解析规则  url
        property.setHelpCrawlerXpath(helpCrawlerXpath);
        //抓取用户空间下的页大小
        property.setCrawlerHelpNextPagingSize(crawlerHelpNextPagingSize);
        //目标页的解析规则
        property.setTargetParseRuleList(getTargetParseRuleList());
        return property;
    }

    /**
     * 目标页面的解析规则
     *
     * @return 规则
     */
    private List<ParseRule> getTargetParseRuleList() {

        List<ParseRule> parseRules = new ArrayList<>();

        //标题
        parseRules.add(new ParseRule("title", CrawlerStatus.ParseRuleType.XPATH, "//h1[@class='title-article']/text()"));
        //作者
        parseRules.add(new ParseRule("author", CrawlerStatus.ParseRuleType.XPATH, "//a[@class='follow-nickName']/text()"));
        //发布日期
        parseRules.add(new ParseRule("releaseDate", CrawlerStatus.ParseRuleType.XPATH, "//span[@class='time']/text()"));
        //标签
        parseRules.add(new ParseRule("labels", CrawlerStatus.ParseRuleType.XPATH, "//div[@class='tags-box']/a/text()"));
        //个人空间
        parseRules.add(new ParseRule("personalSpace", CrawlerStatus.ParseRuleType.XPATH, "//a[@class='follow-nickName']/@href"));
        //阅读量
        parseRules.add(new ParseRule("readCount", CrawlerStatus.ParseRuleType.XPATH, "//span[@class='read-count']/text()"));
        //点赞量
        parseRules.add(new ParseRule("likes", CrawlerStatus.ParseRuleType.XPATH, "//div[@class='tool-box']/ul[@class='meau-list']/li[@class='btn-like-box']/button/p/text()"));
        //回复次数
        parseRules.add(new ParseRule("commentCount", CrawlerStatus.ParseRuleType.XPATH, "//div[@class='tool-box']/ul[@class='meau-list']/li[@class='to-commentBox']/a[@class='btn-comments']/p/text()"));
        //html内容
        parseRules.add(new ParseRule("content", CrawlerStatus.ParseRuleType.XPATH, "//div[@id='content_views']/html()"));

        return parseRules;
    }

    private Spider spider;

    public Spider getSpider() {
        return spider;
    }

    public void setSpider(Spider spider) {
        this.spider = spider;
    }

    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int reidsPort;
    @Value("${redis.timeout}")
    private int reidsTimeout;
    @Value("${redis.password}")
    private String reidsPassword;

    @Bean
    public RedisAndDBScheduler getDbAndRedisScheduler() {
        JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig(), redisHost, reidsPort, reidsTimeout, null, 0);
        return new RedisAndDBScheduler(jedisPool);
    }

}
