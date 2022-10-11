package com.yarns.december.entity.system.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 62537
 */
@Data
public class SysRoleEditBo implements Serializable {

    private static final long serialVersionUID = -2118900355245940648L;
    @NotNull(message = "角色id不能为空")
    private Long id;

    @Size(max = 10, message = "角色名最长10位字符")
    private String roleName;

    @Size(max = 50, message = "角色编码最长不能超过50位")
    private String roleToken;

    private String menuIds;
}
