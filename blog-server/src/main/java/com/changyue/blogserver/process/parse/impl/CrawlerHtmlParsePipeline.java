package com.changyue.blogserver.process.parse.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changyue.blogserver.config.CrawlerConfig;
import com.changyue.blogserver.crawler.model.CrawlerParseItem;
import com.changyue.blogserver.model.entity.CrawlerCsdnPost;
import com.changyue.blogserver.model.entity.CrawlerPostAdditional;
import com.changyue.blogserver.model.entity.CrawlerPostComment;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.model.parse.HtmlLabel;
import com.changyue.blogserver.process.parse.AbstractHtmlParsePipeline;
import com.changyue.blogserver.process.thread.CrawlerThreadPool;
import com.changyue.blogserver.serivce.*;
import com.changyue.blogserver.utils.DateUtils;
import com.changyue.blogserver.utils.ZipUtils;
import com.changyue.blogserver.utils.crawler.HtmlParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-25 21:21
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class CrawlerHtmlParsePipeline extends AbstractHtmlParsePipeline<CrawlerParseItem> {

    @Autowired
    private CrawlerConfig crawlerConfig;

    @Autowired
    private CrawlerCsdnPostService crawlerCsdnPostService;

    @Autowired
    private CrawlerPostAdditionalService crawlerPostAdditionalService;

    @Autowired
    private CrawlerPostLabelService crawlerPostLabelService;

    @Autowired
    private CrawlerPostCommentService crawlerPostCommentService;

    @Autowired
    private CrawlerPostSiteService crawlerPostSiteService;

    /**
     * 获取CSDN评论url
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("crawler");
    private static final String CSDN_COMMENT_URL = resourceBundle.getString("csdn.comment.url");

    /**
     * 处理数据
     */
    @Override
    public void handleHtmlData(CrawlerParseItem parseItem) {
        long currentTimeMillis = System.currentTimeMillis();
        log.info("启动多线程,处理数据：url:[{}]", parseItem.getUrl());
        CrawlerThreadPool.submit(() -> {
            if (CrawlerStatus.HandelType.FORWARD.name().equals(parseItem.getHandelType())) {
                log.info("正在处理数据。url:[{}],handType:[{}]", parseItem.getUrl(), parseItem.getHandelType());
                saveParseItem(parseItem);
            } else {
                log.info("正在反向爬虫,更新附加信息。url:[{}],handType:[{}]", parseItem.getUrl(), parseItem.getHandelType());
                updateCrawlerAdditional(parseItem);
            }
        });
        log.info("多线程处理数据完成。url:[{}],handType:[{}],用时:[{}]", parseItem.getUrl(), parseItem.getHandelType(), System.currentTimeMillis() - currentTimeMillis);
    }

    /**
     * 反向爬虫 更新附加信息
     *
     * @param parseItem 解析的数据
     */
    private void updateCrawlerAdditional(CrawlerParseItem parseItem) {
        long currentTimeMillis = System.currentTimeMillis();

        if (null != parseItem) {
            CrawlerPostAdditional additionalByUrl = crawlerPostAdditionalService.getAdditionalByUrl(parseItem.getUrl());
            if (null != additionalByUrl) {

                //设置为null，目的是为了不更新这两个字段。（mybatis动态更新sql null为不赋值更新）
                additionalByUrl.setNewsId(null);
                additionalByUrl.setUrl(null);

                //需要更新的字段
                additionalByUrl.setReadCount(parseItem.getReadCount());
                additionalByUrl.setComment(parseItem.getCommentCount());
                additionalByUrl.setLikes(parseItem.getLikes());

                //动态下次更新的时间和更新次数
                additionalByUrl.setNextUpdateTime(DateUtils.addHours(new Date(), 48));
                additionalByUrl.setUpdateNum(additionalByUrl.getUpdateNum() + 1);

                crawlerPostAdditionalService.modifyAdditional(additionalByUrl);
            }
        }
        log.info("反向爬虫,更新附加信息完成，url：[{}],handelType:[{}],用时:[{}]", parseItem.getUrl(), parseItem.getHandelType(), System.currentTimeMillis() - currentTimeMillis);
    }

    /**
     * 保存数据
     *
     * @param parseItem 解析的数据
     */
    private void saveParseItem(CrawlerParseItem parseItem) {

        long currentTimeMillis = System.currentTimeMillis();

        if (null != parseItem) {
            String url = parseItem.getUrl();
            String handelType = parseItem.getHandelType();

            log.info("正在保存数据，url：[{}],handelType:[{}]", url, handelType);
            CrawlerCsdnPost crawlerCsdnPost = savePost(parseItem);

            if (null != crawlerCsdnPost) {

                //保存附加信息
                savePostAdditional(parseItem, crawlerCsdnPost);

                //保存评论
                if (null != parseItem.getCommentCount() && parseItem.getCommentCount() > 0) {
                    //赋值ID
                    parseItem.setId(getUrlId(parseItem.getUrl()));
                    //保存评论
                    savePostComment(parseItem, crawlerCsdnPost);
                }

                //发送消息
            }

            log.info("保存数据完成，url：[{}],handelType:[{}],用时:[{}]", url, handelType, System.currentTimeMillis() - currentTimeMillis);
        }
    }

    /**
     * 保存评论
     *
     * @param parseItem       爬虫文章的解析对象
     * @param crawlerCsdnPost 爬虫文章（数据库）
     */
    private void savePostComment(CrawlerParseItem parseItem, CrawlerCsdnPost crawlerCsdnPost) {

        List<CrawlerPostComment> crawlerPostComments = getCrawlerCommentData(parseItem);

        if (null != crawlerPostComments && !crawlerPostComments.isEmpty()) {
            for (CrawlerPostComment crawlerPostComment : crawlerPostComments) {
                crawlerPostComment.setNewsId(crawlerCsdnPost.getId());
                //保存到数据库
                crawlerPostCommentService.saveCrawlerPostComment(crawlerPostComment);
            }
        }
    }

    /**
     * 获取爬虫文章的评论信息
     *
     * @param parseItem 爬虫文章的解析对象
     * @return 爬虫文章的评论信息列表
     */
    private List<CrawlerPostComment> getCrawlerCommentData(CrawlerParseItem parseItem) {
        //1.创建评论的url
        String commentUrl = createComment(parseItem.getUrl());
        //2.发送请求
        String commentData = getOriginalRequestHtmlData(commentUrl, null);
        //3.解析数据
        return parseCommentData(commentData);
    }

    /**
     * 创建评论的url
     * 例如：
     * https://blog.csdn.net/${author}/phoenix/comment/list/${id}/?page=1&size=100&tree_type=1
     * https://blog.csdn.net/abc/phoenix/comment/list/123/?page=1&size=100&tree_type=1
     */
    private String createComment(String url) {

        String author = getUrlUserName(url);
        String userlId = getUrlId(url);
        String commentUrl = CSDN_COMMENT_URL;

        if (StringUtils.isNotEmpty(author) && StringUtils.isNotEmpty(userlId)) {
            String tempAuthor = "${author}";
            String tempUserlId = "${id}";
            commentUrl = commentUrl.replace(tempAuthor, author);
            commentUrl = commentUrl.replace(tempUserlId, userlId);
        }

        return commentUrl;
    }

    /**
     * 解析评论的数据  格式如：CSDNComment.json
     *
     * @param commentData 评论的数据（json）
     */
    private List<CrawlerPostComment> parseCommentData(String commentData) {

        //为空返回
        if (StringUtils.isEmpty(commentData)) {
            return null;
        }

        List<CrawlerPostComment> crawlerPostComments = new ArrayList<>();
        //解析 commentData josn
        JSONObject jsonObject = JSON.parseObject(commentData);

        //获取路径  data->list->info->具体的字段
        Map<String, Object> data = jsonObject.getObject("data", Map.class);
        JSONArray list = (JSONArray) data.get("list");

        if (null != list && !list.isEmpty()) {
            List<Map> mapList = list.toJavaList(Map.class);
            for (Map<String, Object> map : mapList) {
                JSONObject info = (JSONObject) map.get("info");
                Map<String, Object> infoMap = info.toJavaObject(Map.class);
                CrawlerPostComment comment = new CrawlerPostComment();
                comment.setContent(infoMap.get("Content").toString());
                comment.setUsername(infoMap.get("UserName").toString());
                comment.setCommentDate(DateUtils.stringToDate(infoMap.get("PostTime").toString(), "yyyy-MM-dd HH:mm:ss"));
                comment.setCreateTime(new Date());
                crawlerPostComments.add(comment);
            }
        }

        return crawlerPostComments;
    }

    /**
     * 保存附加信息
     *
     * @param parseItem       爬虫文章的解析对象
     * @param crawlerCsdnPost 爬虫文章（数据库）
     */
    private void savePostAdditional(CrawlerParseItem parseItem, CrawlerCsdnPost crawlerCsdnPost) {
        CrawlerPostAdditional crawlerPostAdditional;
        if (null != parseItem && null != crawlerCsdnPost) {
            crawlerPostAdditional = new CrawlerPostAdditional();
            crawlerPostAdditional.setNewsId(crawlerCsdnPost.getId());//文章id
            crawlerPostAdditional.setReadCount(parseItem.getReadCount());//阅读数
            crawlerPostAdditional.setComment(parseItem.getCommentCount());//回复数
            crawlerPostAdditional.setLikes(parseItem.getLikes());//点赞
            crawlerPostAdditional.setUrl(parseItem.getUrl());//url
            crawlerPostAdditional.setUpdateTime(new Date());
            crawlerPostAdditional.setCreateTime(new Date());
            crawlerPostAdditional.setUpdateNum(0);
            //设置下次更新时间 默认时间设置为48小时
            crawlerPostAdditional.setNextUpdateTime(DateUtils.addHours(new Date(), 48));

            //保存到数据库
            crawlerPostAdditionalService.saveAdditional(crawlerPostAdditional);
        }
    }

    /**
     * 保存爬虫文章
     *
     * @param parseItem 爬虫文章的解析对象
     * @return 爬虫文章（数据库）
     */
    private CrawlerCsdnPost savePost(CrawlerParseItem parseItem) {

        CrawlerCsdnPost crawlerCsdnPost = new CrawlerCsdnPost();

        if (null != parseItem) {

            //=====================1.类型转换===================
            //Html的格式转换
            HtmlParser htmlParser = HtmlParser.getHtmlParser(getParseExpression(), getDefHtmlStyleMap());
            //转换后的对象列表
            List<HtmlLabel> htmlLabels = htmlParser.parseHtml(parseItem.getContent());
            //设置类型
            parseItem.setDocType(getImagType(htmlLabels));
            //装换成json格式
            String htmlLabelsJson = JSON.toJSONString(htmlLabels);
            //装换后的值，压缩返回
            parseItem.setCompressContent(ZipUtils.gzip(htmlLabelsJson));

            //=====================2.保存数据=====================
            //判断是否存在
            CrawlerPostAdditional additionalByUrl = crawlerPostAdditionalService.getAdditionalByUrl(parseItem.getUrl());
            if (null == additionalByUrl) {

                //赋值
                crawlerCsdnPost.setName(parseItem.getAuthor());
                crawlerCsdnPost.setLabels(parseItem.getLabels());
                crawlerCsdnPost.setContent(parseItem.getCompressContent());
                crawlerCsdnPost.setLabelIds(crawlerPostLabelService.getPostLabelIdByLabelName(parseItem.getLabels()));
                Integer channelId = crawlerPostLabelService.getPostChannelByLabelIds(crawlerCsdnPost.getLabelIds());
                crawlerCsdnPost.setChannelId(channelId);
                crawlerCsdnPost.setTitle(parseItem.getTitle());
                crawlerCsdnPost.setType(parseItem.getDocType());
                crawlerCsdnPost.setStatus((byte) 1);
                crawlerCsdnPost.setCreateTime(new Date());
                crawlerCsdnPost.setFrom(CrawlerStatus.FormType.CSDN.name());
                String releaseDate = parseItem.getReleaseDate();
                if (StringUtils.isNotEmpty(releaseDate)) {
                    crawlerCsdnPost.setOriginalTime(DateUtils.stringToDate(releaseDate, "yyyy-MM-dd"));
                }

                //保存到数据库中
                crawlerCsdnPostService.savePost(crawlerCsdnPost);

            } else {
                log.info("保存文章已经存在数据库中");
            }
        }

        return crawlerCsdnPost;
    }

    /**
     * 获取图文的类型（单图，多图，无图等）
     *
     * @param htmlLabels 转换格式后的文章对象列表
     * @return 图文的类型
     */
    private int getImagType(List<HtmlLabel> htmlLabels) {

        int imageNum = 0;

        if (null != htmlLabels && !htmlLabels.isEmpty()) {
            for (HtmlLabel htmlLabel : htmlLabels) {
                if (CrawlerStatus.HtmlType.IMG_TAG.equals(htmlLabel.getType())) {
                    imageNum++;
                }
            }
        }
        //无图：0  单图：1 多图：2
        return imageNum == 0 ? 0 : imageNum == 1 ? 1 : imageNum > 1 ? 2 : 0;
    }

    /**
     * 获取用户id
     * 如：https://blog.csdn.net/zhaojiweiwin/article/details/91038061 （91038061）
     */
    private String getUrlId(String url) {
        String urlId = "";
        if (StringUtils.isNotEmpty(url)) {
            int i = url.lastIndexOf("/");
            int length = url.length();
            urlId = url.substring(i + 1, length);
        }
        return urlId;
    }

    /**
     * 获取用户名（url）
     * 如：https://blog.csdn.net/zhaojiweiwin/article/details/91038061 （zhaojiweiwin）
     */
    private String getUrlUserName(String url) {
        String username = "";
        if (StringUtils.isNotEmpty(url)) {
            //用户名第三个/到第四个/之间
            int startIndex = url.indexOf("/", 21);
            int endIndex = url.indexOf("/", 22);
            username = url.substring(startIndex + 1, endIndex);
        }
        return username;
    }


    /**
     * 处理前置一些不规则参数
     * 如：阅读量 123
     * 如：最后发布于2020-03-23 00:24:13 03-1509-0410-2411-1405-2612-1411-2204-1412-16
     */
    @Override
    protected void handleParameter(Map<String, Object> itemsAll) {
        if (itemsAll != null && !itemsAll.isEmpty()) {
            String readCount = itemsAll.get("readCount").toString();
            String releaseDate = itemsAll.get("releaseDate").toString();

            readCount = readCount.replaceAll("[^0-9]", "");
            itemsAll.put("readCount", readCount);

            String timeRex = "\\d{4}-\\d{2}-\\d{2}";
            Pattern compile = Pattern.compile(timeRex);
            Matcher matcher = compile.matcher(releaseDate);
            while (matcher.find()) {
                itemsAll.put("releaseDate", matcher.group());
            }

        }
    }

    @Override
    public int getPriority() {
        return 180;
    }
}
