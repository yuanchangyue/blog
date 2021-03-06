package com.changyue.blogserver.controller;

import com.changyue.blogserver.annotation.MyLog;
import com.changyue.blogserver.model.dto.AttachmentDTO;
import com.changyue.blogserver.model.params.AttachmentQuery;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.serivce.AttachmentsService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : 袁阊越
 * @description : 附件控制器
 * @date : 2020-03-15 14:51
 */
@Slf4j
@RestController
@RequestMapping("/api/attachment")
public class AttachmentsController {

    @Autowired
    private AttachmentsService attachmentsService;

    @PostMapping(value = "/upload")
    public Result upload(@RequestParam(name = "file") MultipartFile file) {
        return Result.create(attachmentsService.upload(file));
    }

    @MyLog("查询附件列表")
    @GetMapping
    public PageInfo<AttachmentDTO> getBy(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return attachmentsService.pageBy(pageIndex, pageSize);
    }

    @MyLog("查询附件列表（条件）")
    @PostMapping("/query")
    public PageInfo<AttachmentDTO> getByQuery(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                              @RequestBody AttachmentQuery attachmentQuery) {
        return attachmentsService.pageByQuery(pageIndex, pageSize, attachmentQuery);
    }

    @MyLog("查询单条附件信息")
    @GetMapping("/{attachmentId}")
    public Result getById(@PathVariable("attachmentId") Integer attachmentId) {
        return Result.create(attachmentsService.getByAttachmentId(attachmentId));
    }

    @GetMapping("/type")
    public Result getById() {
        return Result.create(attachmentsService.geyAttachmentType());
    }

    @DeleteMapping("/{attachmentId}")
    public Result remove(@PathVariable("attachmentId") Integer attachmentId) {
        return Result.create(attachmentsService.removeById(attachmentId));
    }

}
