package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Attachments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 附件数据访问层
 * @date : 2020/3/15
 */
@Repository
@Mapper
public interface AttachmentsMapper extends BaseMapper<Attachments> {
    /**
     * 查询全部媒体类型
     *
     * @return 类型
     */
    List<String> findAllMediaType();

    /**
     * 查询全部类型
     *
     * @return 类型
     */
    List<String> findAllType();

    /**
     * 通过userId查询全部的附件
     *
     * @param userId 用户ID
     * @return 列表
     */
    List<Attachments> listAllByUserId(@NonNull @Param("userId") Integer userId);
}
