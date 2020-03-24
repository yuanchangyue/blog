package com.changyue.blogserver.utils;

import com.changyue.blogserver.model.elsatic.QueryJson;
import com.changyue.blogserver.model.params.FullTextQuery;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;


/**
 * @author : 袁阊越
 * @description : 构造查询的json
 * @date : 2020-03-23 13:45
 */
public class EsQueryUtils {

    /**
     * 创建查询的条件json
     *
     * @param fullTextQuery 全文查询的条件
     * @return json str
     */
    public static String createQuery(FullTextQuery fullTextQuery) {
        Assert.notNull(fullTextQuery, "查询的条件不能为空");
        if (StringUtils.isEmpty(fullTextQuery.getOriginalContent())) {
            Assert.hasText(fullTextQuery.getTitle(), "查询的标题不能为空");
            return createQueryJsonByTitle(fullTextQuery.getTitle());
        } else {
            Assert.hasText(fullTextQuery.getTitle(), "查询的内容不能为空");
            return createQueryJsonByContent(fullTextQuery.getOriginalContent());
        }
    }

    /**
     * 创建查询的标题转json字符串
     *
     * @param title 标题
     * @return json
     */
    public static String createQueryJsonByTitle(String title) {

        QueryJson.QueryBean.MatchPhraseBean match_phrase = new QueryJson.QueryBean.MatchPhraseBean();
        match_phrase.setTitle(title);

        QueryJson.HighlightBean.FieldsBean fieldsBean = new QueryJson.HighlightBean.FieldsBean();
        QueryJson.HighlightBean.FieldsBean.TitleBean titleBean = new QueryJson.HighlightBean.FieldsBean.TitleBean();
        fieldsBean.setTitleBean(titleBean);

        return combinationOfJson(match_phrase, fieldsBean);
    }

    /**
     * 创建查询的内容转json字符串
     *
     * @param content 内容
     * @return json
     */
    public static String createQueryJsonByContent(String content) {

        QueryJson.QueryBean.MatchPhraseBean match_phrase = new QueryJson.QueryBean.MatchPhraseBean();
        match_phrase.setOriginalContent(content);

        QueryJson.HighlightBean.FieldsBean fieldsBean = new QueryJson.HighlightBean.FieldsBean();
        QueryJson.HighlightBean.FieldsBean.OriginalContentBean titleBean = new QueryJson.HighlightBean.FieldsBean.OriginalContentBean();
        fieldsBean.setOriginalContent(titleBean);

        return combinationOfJson(match_phrase, fieldsBean);
    }

    /**
     * 组装json对象 并转换成json字符串
     *
     * @param match_phrase 匹配的短语
     * @param fieldsBean   字段
     * @return json str
     */
    private static String combinationOfJson(QueryJson.QueryBean.MatchPhraseBean match_phrase, QueryJson.HighlightBean.FieldsBean fieldsBean) {

        QueryJson queryJson = new QueryJson();

        QueryJson.QueryBean query = new QueryJson.QueryBean();
        query.setMatch_phrase(match_phrase);

        QueryJson.HighlightBean highlightBean = new QueryJson.HighlightBean();
        highlightBean.setFields(fieldsBean);

        queryJson.setQuery(query);
        queryJson.setHighlight(highlightBean);

        return new Gson().toJson(queryJson);
    }


}
