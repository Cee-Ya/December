package com.yarns.december.entity.system.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 62537
 */
@Data
public class SysRoleAddBo implements Serializable {
    private static final long serialVersionUID = 215637736995523799L;
    @NotBlank(message = "角色名不能为空")
    @Size(max = 10, message = "角色名最长10位字符")
    private String roleName;

    @NotBlank(message = "角色编码不能为空")
    @Size(max = 50, message = "角色编码最长不能超过50位")
    private String roleToken;

    private String menuIds;
}
