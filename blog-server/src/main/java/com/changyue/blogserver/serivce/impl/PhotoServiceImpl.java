package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.PhotoMapper;
import com.changyue.blogserver.model.dto.PhotoDTO;
import com.changyue.blogserver.model.entity.Photo;
import com.changyue.blogserver.model.params.PhotoParam;
import com.changyue.blogserver.serivce.PhotoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 照片业务接口实现
 * @date : 2020-02-01 10:15
 */
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoMapper photoMapper;


    @Override
    public List<PhotoDTO> listByTeam(String team) {

        List<Photo> photoList = photoMapper.findByTeam(team);

        //装换为List<PhotoDdTO>
        return photoList.stream().map(photo -> {
            PhotoDTO photoDTO = new PhotoDTO();
            BeanUtils.copyProperties(photo, photoDTO);
            return photoDTO;
        }).collect(Collectors.toList());

    }

    @Override
    public PageInfo<PhotoDTO> pageDtos(Integer pageIndex, Integer pageSize) {

        Assert.notNull(pageIndex, "pageIndex 不能为空");
        Assert.notNull(pageSize, "pageSize 不能为空");

        PageHelper.startPage(pageIndex, 5);
        List<Photo> photoList = photoMapper.listAll();

        List<PhotoDTO> photoDTOList = photoList.stream().map(photo -> {
            PhotoDTO photoDTO = new PhotoDTO();
            BeanUtils.copyProperties(photo, photoDTO);
            return photoDTO;
        }).collect(Collectors.toList());

        return new PageInfo<>(photoDTOList, 3);
    }

    @Override
    public Photo createBy(PhotoParam photoParam) {

        Assert.notNull(photoParam, "photoParam的参数不能为空");

        //转换为DO
        Photo photo = photoParam.converTo();

        photoMapper.insert(photo);

        return photo;
    }

    @Override
    public List<String> findAllTeams() {
        return photoMapper.findAllTeams();
    }


}
