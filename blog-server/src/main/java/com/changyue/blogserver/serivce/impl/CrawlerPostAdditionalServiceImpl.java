package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.crawler.model.CrawlerParseItem;
import com.changyue.blogserver.crawler.model.ParseItem;
import com.changyue.blogserver.dao.CrawlerPostAdditionalMapper;
import com.changyue.blogserver.model.entity.CrawlerPostAdditional;
import com.changyue.blogserver.serivce.CrawlerPostAdditionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 爬虫文章附加信息的业务接口实现类
 * @date : 2020-03-25 17:14
 */
@Service
@Slf4j
public class CrawlerPostAdditionalServiceImpl implements CrawlerPostAdditionalService {

    @Autowired
    private CrawlerPostAdditionalMapper crawlerPostAdditionalMapper;

    @Override
    public CrawlerPostAdditional getAdditionalByUrl(String url) {
        Assert.notNull(url, "地址不能为空");
        CrawlerPostAdditional crawlerPostAdditional = new CrawlerPostAdditional();
        crawlerPostAdditional.setUrl(url);
        List<CrawlerPostAdditional> list = getList(crawlerPostAdditional);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<CrawlerPostAdditional> getListByNeedUpdate(Date currentDate) {
        Assert.notNull(currentDate, "当前时间不能为空");
        return crawlerPostAdditionalMapper.findListByNeedUpdate(currentDate);
    }

    @Override
    public List<CrawlerPostAdditional> getList(CrawlerPostAdditional crawlerPostAdditional) {
        Assert.notNull(crawlerPostAdditional, "爬虫的附加信息不能为空");
        return crawlerPostAdditionalMapper.findAll(crawlerPostAdditional);
    }

    @Override
    public boolean checkExist(String url) {
        Assert.notNull(url, "地址不能为空");
        CrawlerPostAdditional crawlerPostAdditional = new CrawlerPostAdditional();
        crawlerPostAdditional.setUrl(url);
        List<CrawlerPostAdditional> list = getList(crawlerPostAdditional);
        return null != list && !list.isEmpty();
    }

    @Override
    public boolean isExistUrl(String url) {
        Assert.notNull(url, "地址不能为空");
        CrawlerPostAdditional additionalByUrl = getAdditionalByUrl(url);
        return null != additionalByUrl;
    }

    @Override
    public void saveAdditional(CrawlerPostAdditional crawlerPostAdditional) {
        Assert.notNull(crawlerPostAdditional, "爬虫的附加信息不能为空");
        crawlerPostAdditionalMapper.insertSelective(crawlerPostAdditional);
    }

    @Override
    public void modifyAdditional(CrawlerPostAdditional crawlerPostAdditional) {
        Assert.notNull(crawlerPostAdditional, "爬虫的附加信息不能为空");
        crawlerPostAdditionalMapper.updateByPrimaryKeySelective(crawlerPostAdditional);
    }

    @Override
    public List<ParseItem> convertToParseItem(List<CrawlerPostAdditional> additionalList) {
        Assert.notNull(additionalList, "附加信息列表不能为空");
        //返回转换的列表
        return additionalList.stream().map(this::convertToParseItem).collect(Collectors.toList());
    }

    public ParseItem convertToParseItem(CrawlerPostAdditional crawlerPostAdditional) {
        Assert.notNull(crawlerPostAdditional, "爬虫的附加信息不能为空");
        //返回parseItem的子类
        CrawlerParseItem crawlerParseItem = new CrawlerParseItem();
        crawlerParseItem.setUrl(crawlerParseItem.getUrl());
        return crawlerParseItem;
    }

    @Override
    public List<ParseItem> getIncrementParseItem(Date currentDate) {
        Assert.notNull(currentDate, "当前时间不能为空");
        return getListByNeedUpdate(currentDate).stream().map(this::convertToParseItem).collect(Collectors.toList());
    }
}
