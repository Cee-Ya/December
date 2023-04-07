package com.yarns.december.entity.system.vo;

import lombok.Data;

/**
 * @author color
 */
@Data
public class PermissionsVO {
    /**
     * 资源名称
     */
    private String menuName;
    /**
     * 权限标识
     */
    private String perms;
    /**
     * 路由名称
     */
    private String path;

}
