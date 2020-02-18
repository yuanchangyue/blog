package com.changyue.blogserver.model.enums;

/**
 * @author : 袁阊越
 * @description : 权限角色
 * @date : 2020-02-18 12:29
 */
public enum AuthorityStatus {
    /**
     * 管理员
     */
    ADMIN(0),
    /**
     * 普通用户（博主）
     */
    ORDINARY_USER(1);

    int authorityCode;

    AuthorityStatus(int authorityCode) {
        this.authorityCode = authorityCode;
    }

    public int getAuthorityCode() {
        return authorityCode;
    }
}
