package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CrawlerPostChannelLabelMapper;
import com.changyue.blogserver.dao.CrawlerPostLabelMapper;
import com.changyue.blogserver.model.entity.CrawlerPostChannelLabel;
import com.changyue.blogserver.model.entity.CrawlerPostLabel;
import com.changyue.blogserver.serivce.CrawlerPostLabelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author : 袁阊越
 * @description : 爬虫文章标签业务接口层实现
 * @date : 2020-03-25 12:48
 */
@Service
@Slf4j
public class CrawlerPostLabelServiceImpl implements CrawlerPostLabelService {

    @Autowired
    private CrawlerPostLabelMapper crawlerPostLabelMapper;

    @Autowired
    private CrawlerPostChannelLabelMapper crawlerPostChannelLabelMapper;

    @Override
    public String getPostLabelIdByLabelName(@Nonnull String labels) {

        List<CrawlerPostLabel> crawlerPostLabelByLabels = new ArrayList<>();
        StringBuffer ids = new StringBuffer();
        List<Integer> postLabelIds;

        if (StringUtils.isNotEmpty(labels)) {

            labels = labels.toLowerCase();
            List<String> labelNameList = Arrays.asList(labels.split(","));
            labelNameList = new ArrayList<>(labelNameList);
            crawlerPostLabelByLabels = crawlerPostLabelMapper.findPostLabelByLabelsName(labelNameList);

            if (null != crawlerPostLabelByLabels && !crawlerPostLabelByLabels.isEmpty()) {
                //数据库中有数据
                crawlerPostLabelByLabels = addPostLabelList(labelNameList, crawlerPostLabelByLabels);
            } else {
                //数据库中不存在
                crawlerPostLabelByLabels = addPostLabelList(labelNameList);
            }
        }

        //转换成 ID Str
        if (null != crawlerPostLabelByLabels && !crawlerPostLabelByLabels.isEmpty()) {
            postLabelIds = crawlerPostLabelByLabels.stream().map(CrawlerPostLabel::getId).collect(Collectors.toList());
            postLabelIds.forEach(id -> ids.append(id).append(","));
        }

        return ids.toString();
    }

    /**
     * 过滤+保存（列表）
     *
     * @param labelNameList     标签名称列表
     * @param crawlerPostLabelByLabels 通过标签名称列表查询的文章标签列表
     * @return 文章标签列表
     */
    private List<CrawlerPostLabel> addPostLabelList(List<String> labelNameList, List<CrawlerPostLabel> crawlerPostLabelByLabels) {

        //过滤
        if (labelNameList != null && !labelNameList.isEmpty()) {
            for (CrawlerPostLabel label : crawlerPostLabelByLabels) {
                for (int i = 0; i < labelNameList.size(); i++) {
                    if (labelNameList.get(i).contains(label.getName())) {
                        labelNameList.remove(i);
                    }
                }
            }
        }

        //过滤后将剩余的保存
        if (labelNameList != null && !labelNameList.isEmpty()) {
            crawlerPostLabelByLabels.addAll(addPostLabelList(labelNameList));
        }

        return crawlerPostLabelByLabels;
    }

    /**
     * 保存（列表）
     *
     * @param labelNameList 标签名称列表
     * @return 文章标签列表
     */
    private List<CrawlerPostLabel> addPostLabelList(List<String> labelNameList) {
        List<CrawlerPostLabel> crawlerPostLabels = new ArrayList<>();
        for (String name : labelNameList) {
            crawlerPostLabels.add(addPostLabel(name));
        }
        return crawlerPostLabels;
    }

    /**
     * 保存
     *
     * @param name 标签的名称
     * @return post label
     */
    private CrawlerPostLabel addPostLabel(String name) {
        CrawlerPostLabel crawlerPostLabel = new CrawlerPostLabel();
        crawlerPostLabel.setName(name);
        crawlerPostLabel.setType(true);
        crawlerPostLabel.setCreateTime(new Date());
        crawlerPostLabelMapper.insert(crawlerPostLabel);
        return crawlerPostLabel;
    }

    @Override
    public Integer getPostChannelByLabelIds(@Nonnull String labels) {
        Integer channelId = 0;

        //label -> 1,2,3,
        if (StringUtils.isNotEmpty(labels)) {

            List<String> labelIds = Arrays.asList(labels.split(","));
            List<CrawlerPostLabel> crawlerPostLabelByLabelIds = crawlerPostLabelMapper.findPostLabelByLabelIds(labelIds);

            if (null != crawlerPostLabelByLabelIds && !crawlerPostLabelByLabelIds.isEmpty()) {
                //多个label 只取第一个作为channel
                CrawlerPostChannelLabel byLabelId = crawlerPostChannelLabelMapper.findByLabelId(crawlerPostLabelByLabelIds.get(0).getId());
                if (null != byLabelId) {
                    channelId = byLabelId.getChannelId();
                }
            }
        }

        return channelId == null ? 0 : channelId;
    }
}
