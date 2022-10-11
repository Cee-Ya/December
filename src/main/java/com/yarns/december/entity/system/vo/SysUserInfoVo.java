package com.yarns.december.entity.system.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Data
public class SysUserInfoVo {
    private Long id;

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

    /**
     * 备注
     */
    private String backup;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 角色id集合
     */
    private List<Long> roleIds;
}
