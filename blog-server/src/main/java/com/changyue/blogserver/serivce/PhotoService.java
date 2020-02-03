package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.dto.PhotoDTO;
import com.changyue.blogserver.model.entity.Photo;
import com.changyue.blogserver.model.params.PhotoParam;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;


import java.util.List;

/**
 * @author : 袁阊越
 * @date : 2020-02-01 09:13
 * @description : 照片业务接口层
 */
public interface PhotoService extends BaseService<Photo, Integer> {

    /**
     * 按分组列出照片。
     *
     * @param team 分组
     * @return 照片列表
     */
    List<PhotoDTO> listByTeam(@NonNull String team);

    /**
     * 通过照片参数创建照片。
     *
     * @param photoParam 不能为空
     * @return 创建照片
     */
    @NonNull
    Photo createBy(@NonNull PhotoParam photoParam);

    /**
     * 查找所有照片分组。
     *
     * @return 分组列表.
     */
    List<String> findAllTeams();

    /**
     * 分页列出所有页面
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @return 分页列表
     */
    @NonNull
    PageInfo<PhotoDTO> pageDtos(@NonNull Integer pageIndex, @NonNull Integer pageSize);




}
