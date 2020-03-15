package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.PhotoMapper;
import com.changyue.blogserver.model.dto.PhotoDTO;
import com.changyue.blogserver.model.entity.Photo;
import com.changyue.blogserver.model.params.PhotoParam;
import com.changyue.blogserver.model.rep.UploadResult;
import com.changyue.blogserver.serivce.PhotoService;
import com.changyue.blogserver.utils.LocalImgUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.imageio.plugins.common.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 照片业务接口实现
 * @date : 2020-02-01 10:15
 */
@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoMapper photoMapper;


    @Override
    public UploadResult upload(MultipartFile file) {
        Assert.notNull(file, "上传的文件不能为空");

        log.info("开始上传图片：[{}]", file.getOriginalFilename());

        UploadResult result = LocalImgUtils.upload(file);

        log.info("上传的结果:[{}]", result);

        return null;
    }


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
        Photo photo = photoParam.convertTo();

        photoMapper.insert(photo);

        return photo;
    }

    @Override
    public List<String> findAllTeams() {
        return photoMapper.findAllTeams();
    }

}
