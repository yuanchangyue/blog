package com.changyue.blogserver.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : ip代理池
 * @date : 2020/3/25
 */
@Data
public class CrawlerIpPool {

    private Integer id;


    /**
     * 供应商
     */
    private String supplier;

    /**
     * ip
     */
    private String ip;

    /**
     * 端口号
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 耗时
     */
    private Integer duration;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 是否开启
     */
    private Boolean isEnable;

    /**
     * 可用范围
     */
    private String range;

    /**
     * 创建时间
     */
    private Date createTime;


}
