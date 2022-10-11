package com.yarns.december.entity.system.bo;

import lombok.Data;

/**
 * 用户 Entity
 *
 * @author Yarns
 * @date 2022-06-03 11:02:47
 */
@Data
public class SysUserPageBo {
    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

}