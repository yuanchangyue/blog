package com.changyue.blogserver.serivce;


import com.changyue.blogserver.crawler.model.ParseItem;
import com.changyue.blogserver.model.entity.CrawlerPostAdditional;

import java.util.Date;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫文章附加信息的业务接口层
 * @date : 2020/3/25
 */
public interface CrawlerPostAdditionalService {

    /**
     * 根据url获取图片附加信息
     *
     * @param url 地址
     * @return 附件信息
     */
    CrawlerPostAdditional getAdditionalByUrl(String url);

    /**
     * 根据当前需要更新的时间查询列表
     *
     * @param currentDate 当前时间
     * @return 附加信息列表
     */
    List<CrawlerPostAdditional> getListByNeedUpdate(Date currentDate);

    /**
     * 根据条件查询
     *
     * @param crawlerPostAdditional 爬虫文章的附加信息
     * @return 附加信息列表
     */
    List<CrawlerPostAdditional> getList(CrawlerPostAdditional crawlerPostAdditional);


    /**
     * 检查是否存在
     *
     * @param url 地址
     * @return 存在？
     */
    boolean checkExist(String url);

    /**
     * 是否是已存在的url
     *
     * @param url 地址
     * @return 存在？
     */
    boolean isExistUrl(String url);

    /**
     * 保存
     *
     * @param crawlerPostAdditional 爬虫文章的附加信息
     */
    void saveAdditional(CrawlerPostAdditional crawlerPostAdditional);

    /**
     * 更新
     *
     * @param crawlerPostAdditional 爬虫文章的附加信息
     */
    void modifyAdditional(CrawlerPostAdditional crawlerPostAdditional);

    /**
     * 转换数据为parseItem
     *
     * @param additionalList 爬虫文章的附加信息列表
     * @return 解析数据列表
     */
    List<ParseItem> convertToParseItem(List<CrawlerPostAdditional> additionalList);

    /**
     * 查询增量的统计数据
     *
     * @param currentDate 当前时间
     * @return 解析数据列表
     */
    List<ParseItem> getIncrementParseItem(Date currentDate);

}
