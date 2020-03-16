package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.dto.AttachmentDTO;
import com.changyue.blogserver.model.entity.AttachmentType;
import com.changyue.blogserver.model.entity.Attachments;
import com.changyue.blogserver.model.params.AttachmentQuery;
import com.changyue.blogserver.serivce.base.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 附件业务接口
 * @date : 2020-03-15 14:47
 */
public interface AttachmentsService extends BaseService<Attachments, Integer> {

    /**
     * 获得附件类型
     *
     * @return 列表
     */
    List<String> geyAttachmentType();

    /**
     * 通过ID查询
     *
     * @param id id
     * @return DTO
     */
    AttachmentDTO getByAttachmentId(@NonNull Integer id);

    /**
     * 分页全部
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @return 分页类别
     */
    @NonNull
    PageInfo<AttachmentDTO> pageBy(@NonNull Integer pageIndex, @NonNull Integer pageSize);

    /**
     * 分页全部
     *
     * @param pageIndex       页索引
     * @param pageSize        页数
     * @param attachmentQuery 附件查询
     * @return 分页类别
     */
    @NonNull
    PageInfo<AttachmentDTO> pageByQuery(@NonNull Integer pageIndex, @NonNull Integer pageSize, @NonNull AttachmentQuery attachmentQuery);

    /**
     * 上传附件
     *
     * @param file 文件
     * @return 附件
     */
    @NonNull
    Attachments upload(@NonNull MultipartFile file);

    /**
     * 转换为DIO
     *
     * @param attachments 附件
     * @return 附件DTO
     */
    @NonNull
    AttachmentDTO convertTo(@NonNull Attachments attachments);

    /**
     * 转换为DIO
     *
     * @param attachmentsList 附件别表
     * @return 附件DTO
     */
    @NonNull
    List<AttachmentDTO> convertTo(@NonNull List<Attachments> attachmentsList);
}
