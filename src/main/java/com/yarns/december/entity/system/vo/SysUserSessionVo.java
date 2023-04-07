package com.yarns.december.entity.system.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Data
@NoArgsConstructor
public class SysUserSessionVo implements Serializable {

    public SysUserSessionVo(String json) throws JsonProcessingException {
        SysUserSessionVo song = new ObjectMapper().readValue(json, SysUserSessionVo.class);
        this.id = song.getId();
        this.username = song.getUsername();
        this.mobile = song.getMobile();
        this.backup = song.getBackup();
        this.adminTag = song.getAdminTag();
    }

    private Long id;
    /**
     * 用户账号
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 备注
     */
    private String backup;

    /**
     * 是否为超级管理员
     */
    private Boolean adminTag = false;

    /**
     * 当前用户角色
     */
    private List<String> roleNames;
}
