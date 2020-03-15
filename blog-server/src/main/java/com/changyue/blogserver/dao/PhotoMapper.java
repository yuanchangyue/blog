package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

@Mapper
@Repository
public interface PhotoMapper extends BaseMapper<Photo> {

    /**
     * 按分组查询照片
     *
     * @param team 分组
     * @return 照片列表
     */
    List<Photo> findByTeam(@Param("team") String team);

    /**
     * 查找所有照片分组。
     *
     * @return 分组列表.
     */
    List<String> findAllTeams();


}
