package com.changyue.blogserver.serivce.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changyue.blogserver.dao.CrawlerSmartisonPostMapper;
import com.changyue.blogserver.model.entity.CrawlerPostCate;
import com.changyue.blogserver.model.entity.CrawlerPostSite;
import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import com.changyue.blogserver.serivce.CrawlerPostCateService;
import com.changyue.blogserver.serivce.CrawlerPostSiteService;
import com.changyue.blogserver.serivce.CrawlerSmartisonService;
import com.changyue.blogserver.utils.DateUtils;
import com.changyue.blogserver.utils.JsoupUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-28 21:00
 */
@Service
@Slf4j
public class CrawlerSmartisonServiceImpl implements CrawlerSmartisonService {

    @Autowired
    private CrawlerPostSiteService crawlerPostSiteService;

    @Autowired
    private CrawlerPostCateService crawlerPostCateService;

    @Autowired
    private CrawlerSmartisonPostMapper crawlerSmartisonPostMapper;

    @Override
    public CrawlerSmartisonPost getById(Integer id) {
        Assert.notNull(id, "文章的ID不能为空");
        return crawlerSmartisonPostMapper.selectByPrimaryKey(id).orElse(null);
    }

    @Override
    public void saveCate(String url) {
        log.info("正在保存smartison分类，url：[{}]", url);
        String htmlData = JsoupUtils.getHtmlData(url);
        CrawlerPostCate crawlerPostCate = (CrawlerPostCate) parseHtmlData(htmlData, "cate");
        if (null != crawlerPostCate && crawlerPostCate.getName() != null) {
            crawlerPostCateService.create(crawlerPostCate);
        }
        log.info("保存smartison分类完成");
    }

    @Override
    public void saveSite(String url) {
        log.info("正在保存smartison 站点，url：[{}]", url);
        String htmlData = JsoupUtils.getHtmlData(url);
        CrawlerPostSite crawlerPostSite = (CrawlerPostSite) parseHtmlData(htmlData, "site");
        if (null != crawlerPostSite && StringUtils.isNotEmpty(crawlerPostSite.getName())) {
            crawlerPostSiteService.create(crawlerPostSite);
        }
        log.info("保存smartison站点完成");
    }

    @Override
    public void saveArticle(String url) {
        log.info("正在保存站点文章，url：[{}]", url);
        String htmlData = JsoupUtils.getHtmlData(url);
        List<CrawlerSmartisonPost> articles = (List<CrawlerSmartisonPost>) parseHtmlData(htmlData, "article");
        if (null != articles && !articles.isEmpty()) {
            for (CrawlerSmartisonPost article : articles) {
                if (null != article && StringUtils.isNotEmpty(article.getContent())) {
                    CrawlerSmartisonPost crawlerSmartisonPost = crawlerSmartisonPostMapper.selectByPrimaryKey(article.getId()).orElse(null);
                    if (crawlerSmartisonPost == null) {
                        crawlerSmartisonPostMapper.insertSelective(article);
                    }
                }
            }
        }
        log.info("保存站点文章完成");
    }

    @Override
    public String parseArticle(String url) {
        String htmlData = JsoupUtils.getHtmlData(url);
        String htmlContent = null;
        if (StringUtils.isNotEmpty(htmlData)) {
            Document document = Jsoup.parse(htmlData);
            Elements content = document.getElementsByClass("content");
            if (null != content && !content.isEmpty()) {
                for (Element element : content) {
                    htmlContent = element.html();
                }
            }
        }
        return htmlContent;
    }

    @Override
    public PageInfo<CrawlerSmartisonPost> getPostList(@Nonnull Integer pageIndex, @Nonnull Integer pageSize, Integer siteId) {
        PageHelper.startPage(pageIndex, pageSize);
        List<CrawlerSmartisonPost> postBySite = crawlerSmartisonPostMapper.findPostBySite(siteId);
        return new PageInfo<>(postBySite, 3);
    }

    /**
     * 随机拿到站点 再拿到站点文章
     */
    @Override
    public List<CrawlerSmartisonPost> randomList() {

        HashSet<Integer> indexs = new HashSet<>();

        for (int i = 0; i < 8; i++) {
            indexs.add(new Random().nextInt(500));
        }

        List<String> siteIds = crawlerPostSiteService.listIds();
        List<String> randomIds = new ArrayList<>();

        for (Integer index : indexs) {
            randomIds.add(siteIds.get(index));
        }

        return randomIds.stream().map(s -> crawlerSmartisonPostMapper.findOnePostBySite(Integer.valueOf(s))).collect(Collectors.toList());

    }

    @Override
    public CrawlerSmartisonPost getSimplyPost(Integer id) {
        return crawlerSmartisonPostMapper.findSimplyById(id);
    }

    /**
     * 解析json
     *
     * @param htmlData json
     * @return CrawlerPostCate
     */
    private Object parseHtmlData(String htmlData, String type) {
        if (StringUtils.isEmpty(htmlData)) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(htmlData);
        Map<String, Object> map = jsonObject.toJavaObject(Map.class);
        String code = map.get("code").toString();
        if ("0".equals(code)) {
            JSONObject data = (JSONObject) map.get("data");
            if (null != data && !data.isEmpty()) {
                if ("cate".equals(type)) {
                    return parseCate(data);
                } else if ("site".equals(type)) {
                    return parseSite(data);
                } else if ("article".equals(type)) {
                    return parseArticle(data);
                }
            }
        }
        return null;
    }

    private List<CrawlerSmartisonPost> parseArticle(JSONObject data) {
        List<CrawlerSmartisonPost> crawlerSmartisonPosts = new ArrayList<>();
        JSONArray list = (JSONArray) data.get("list");
        for (Object obj : list) {
            CrawlerSmartisonPost csp = new CrawlerSmartisonPost();
            JSONObject post = (JSONObject) obj;
            csp.setId(post.getInteger("id"));
            csp.setBrief(post.getString("brief"));
            csp.setHeadpic(post.getString("headpic"));
            csp.setOriginalUrl(post.getString("origin_url"));
            csp.setUrl(post.getString("url"));
            csp.setTitle(post.getString("title"));
            csp.setPubTime(DateUtils.stampToDate(post.getString("pub_date")));
            String prepic1 = post.getString("prepic1");
            if (StringUtils.isNotEmpty(prepic1)) {
                csp.setPrePic1(prepic1);
            }
            String prepic2 = post.getString("prepic2");
            if (StringUtils.isNotEmpty(prepic2)) {
                csp.setPrePic2(prepic2);
            }
            String prepic3 = post.getString("prepic3");
            if (StringUtils.isNotEmpty(prepic3)) {
                csp.setPrePic3(prepic3);
            }
            csp.setContent(parseArticle(post.getString("origin_url")));
            JSONObject siteInfo = ((JSONObject) obj).getJSONObject("site_info");
            if (null != siteInfo && !siteInfo.isEmpty()) {
                csp.setSiteId(siteInfo.getInteger("id"));
                csp.setSiteName(siteInfo.getString("name"));
            }
            crawlerSmartisonPosts.add(csp);
        }
        return crawlerSmartisonPosts;
    }

    private CrawlerPostCate parseCate(JSONObject data) {
        CrawlerPostCate crawlerPostCate = new CrawlerPostCate();
        JSONArray list = (JSONArray) data.get("list");
        for (Object obj : list) {
            JSONObject listJson = (JSONObject) obj;
            JSONArray cateInfo = (JSONArray) listJson.get("cate_info");
            if (!cateInfo.isEmpty()) {
                for (Object o : cateInfo) {
                    JSONObject cate = (JSONObject) o;
                    crawlerPostCate.setId(cate.getInteger("id"));
                    crawlerPostCate.setName(cate.getString("name"));
                    crawlerPostCate.setIcon(cate.getString("icon"));
                    crawlerPostCate.setBrief(cate.getString("brief"));
                    crawlerPostCate.setSiteIds(cate.getString("site_ids"));
                }
            }
        }
        return crawlerPostCate;
    }

    private CrawlerPostSite parseSite(JSONObject data) {
        CrawlerPostSite crawlerPostSite = new CrawlerPostSite();
        crawlerPostSite.setBrief(data.getString("brief"));
        crawlerPostSite.setPic(data.getString("pic"));
        crawlerPostSite.setId(data.getString("id"));
        crawlerPostSite.setRssUrl(data.getString("rss_url"));
        crawlerPostSite.setName(data.getString("name"));
        crawlerPostSite.setArticleNum(data.getInteger("article_num"));
        JSONArray cateInfo = (JSONArray) data.get("cate_info");
        if (!cateInfo.isEmpty()) {
            for (Object o : cateInfo) {
                JSONObject cate = (JSONObject) o;
                crawlerPostSite.setCateId(cate.getInteger("id"));
            }
        }
        return crawlerPostSite;
    }


}
