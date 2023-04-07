package com.yarns.december.entity.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Data
public class SysUserPageVo {
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
     * 删除状态(0 未删除 1已删除)
     */
    private Integer deleteStatus;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Integer version;
}
