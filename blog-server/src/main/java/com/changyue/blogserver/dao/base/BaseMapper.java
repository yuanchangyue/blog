package com.changyue.blogserver.dao.base;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : 袁阊越
 * @date : 2020-01-31 12:24
 * @description : 通用的mapper
 */
@Mapper
@Repository
public interface BaseMapper<DOMAIN> {

    /**
     * 查询全部
     *
     * @return 全部列表
     */
    List<DOMAIN> listAll();

    /**
     * 查找全部(条件)
     *
     * @param domain 实体
     * @return 实体
     */
    List<DOMAIN> listAllBySelective(DOMAIN domain);

    /**
     * 查找一个(条件)
     *
     * @param domain 实体
     * @return 实体
     */
    Optional<DOMAIN> findOneBySelective(DOMAIN domain);

    /**
     * 根据id删除
     *
     * @param id id主键
     * @return 影响行数
     */
    int deleteByPrimaryKey(@NonNull Integer id);

    /**
     * 插入实体
     *
     * @param domain 实体
     * @return 影响行数
     */
    int insert(DOMAIN domain);

    /**
     * 按照条件插入
     *
     * @param domain 实体
     * @return 影响行数
     */
    int insertSelective(DOMAIN domain);

    /**
     * 通过实体id查找实体
     *
     * @param id id主键
     * @return 实体
     */
    Optional<DOMAIN> selectByPrimaryKey(@NonNull Integer id);

    /**
     * 通过主键更新实体(选择性)
     *
     * @param domain 实体
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(DOMAIN domain);

    /**
     * 通过主键更新实体(选择性)
     *
     * @param domain 实体
     * @return 影响行数
     */
    int updateByPrimaryKey(DOMAIN domain);

}
