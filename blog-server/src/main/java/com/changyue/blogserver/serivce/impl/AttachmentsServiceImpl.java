package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.AttachmentsMapper;
import com.changyue.blogserver.model.dto.AttachmentDTO;
import com.changyue.blogserver.model.entity.Attachments;
import com.changyue.blogserver.model.params.AttachmentQuery;
import com.changyue.blogserver.model.rep.UploadResult;
import com.changyue.blogserver.serivce.AttachmentsService;
import com.changyue.blogserver.utils.LocalImgUtils;
import com.changyue.blogserver.utils.PageInfoUtil;
import com.changyue.blogserver.utils.ShiroUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
 * @description : 附件业务实现
 * @date : 2020-03-15 14:49
 */
@Slf4j
@Service
public class AttachmentsServiceImpl implements AttachmentsService {

    @Autowired
    private AttachmentsMapper attachmentsMapper;

    @Override
    public List<String> geyAttachmentType() {
        return attachmentsMapper.listAllMediaType(ShiroUtils.getUser().getId());
    }


    @Override
    public AttachmentDTO getByAttachmentId(Integer id) {
        Assert.notNull(id, "id不能为空");
        Attachments attachments = attachmentsMapper.selectByPrimaryKey(id).orElse(null);
        return convertTo(attachments);
    }

    @Override
    public PageInfo<AttachmentDTO> pageBy(Integer pageIndex, Integer pageSize) {

        Assert.notNull(pageIndex, "页索引不能为空");
        Assert.notNull(pageSize, "页数不能为空");

        //开始分页
        PageHelper.startPage(pageIndex, pageSize);

        //获取全部附件信息
        List<Attachments> attachmentsList = attachmentsMapper.listAllByUserId(ShiroUtils.getUser().getId());
        PageInfo<Attachments> categoryPageInfo = new PageInfo<>(attachmentsList, 5);

        //构造DTO 分页列表
        PageInfo<AttachmentDTO> attachmentDTOPageInfo = PageInfoUtil.PageInfo2PageInfoDTO(categoryPageInfo);

        //查询的列表转化为DTO列表
        List<AttachmentDTO> attachmentDTOList = attachmentsList.stream().map(this::convertTo).collect(Collectors.toList());

        //为新的DTO列表添加数据
        attachmentDTOList.forEach(attachmentDTO -> attachmentDTOPageInfo.getList().add(attachmentDTO));

        return attachmentDTOPageInfo;
    }


    @Override
    public PageInfo<AttachmentDTO> pageByQuery(Integer pageIndex, Integer pageSize, AttachmentQuery attachmentQuery) {
        Assert.notNull(pageIndex, "页索引不能为空");
        Assert.notNull(pageSize, "页数不能为空");

        //开始分页
        PageHelper.startPage(pageIndex, pageSize);

        //获取全部附件信息
        List<Attachments> attachmentsList = attachmentsMapper.listAllByQuery(ShiroUtils.getUser().getId(), attachmentQuery);
        PageInfo<Attachments> categoryPageInfo = new PageInfo<>(attachmentsList, 5);

        //构造DTO 分页列表
        PageInfo<AttachmentDTO> attachmentDTOPageInfo = PageInfoUtil.PageInfo2PageInfoDTO(categoryPageInfo);

        //查询的列表转化为DTO列表
        List<AttachmentDTO> attachmentDTOList = attachmentsList.stream().map(this::convertTo).collect(Collectors.toList());

        //为新的DTO列表添加数据
        attachmentDTOList.forEach(attachmentDTO -> attachmentDTOPageInfo.getList().add(attachmentDTO));

        return attachmentDTOPageInfo;
    }


    @Override
    public Attachments upload(MultipartFile file) {

        Assert.notNull(file, "上传的文件不能为空");

        log.info("开始上传图片：[{}]", file.getOriginalFilename());

        UploadResult result = LocalImgUtils.upload(file);

        log.info("上传的结果:[{}]", result);

        //构造出附件
        Attachments attachment = new Attachments();
        attachment.setName(result.getFilename());
        attachment.setPath(LocalImgUtils.changeFileSeparator2UrlSeparator(result.getFilePath()));
        attachment.setThumbPath(result.getThumbPath());
        attachment.setMediaType(result.getMediaType().toString());
        attachment.setSuffix(result.getSuffix());
        attachment.setWidth(result.getWidth());
        attachment.setHeight(result.getHeight());
        attachment.setSize(result.getSize());
        attachment.setUserId(ShiroUtils.getUser().getId());

        return create(attachment);
    }


    @Override
    public int removeById(Integer id) {

        Assert.notNull(id, "id不能为空");

        //获取删除的对象
        AttachmentDTO attachmentDTO = this.getByAttachmentId(id);

        //删除本地文件
        LocalImgUtils.delete(attachmentDTO.getPath());

        //删除数据库中内容
        return attachmentsMapper.deleteByPrimaryKey(id);
    }


    @Override
    public Attachments create(Attachments attachments) {
        Assert.notNull(attachments, "附件内容不能为空");
        attachmentsMapper.insert(attachments);
        return attachments;
    }

    @Override
    public AttachmentDTO convertTo(Attachments attachments) {
        Assert.notNull(attachments, "附件不能为空");
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        BeanUtils.copyProperties(attachments, attachmentDTO);
        return attachmentDTO;
    }

    @Override
    public List<AttachmentDTO> convertTo(List<Attachments> attachmentsList) {
        return attachmentsList.stream().map(this::convertTo).collect(Collectors.toList());
    }

}
