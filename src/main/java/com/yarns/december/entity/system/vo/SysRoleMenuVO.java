package com.yarns.december.entity.system.vo;

import lombok.Data;

/**
 * @author 62537
 */
@Data
public class SysRoleMenuVO {
    private Long id;

    private String roleName;

    private String remark;
    /**
     * 角色token
     */
    private String roleToken;

    private transient String menuIds;
}
