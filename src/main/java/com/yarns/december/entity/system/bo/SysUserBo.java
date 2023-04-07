package com.yarns.december.entity.system.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 用户 Entity
 *
 * @author Yarns
 * @date 2022-06-03 11:02:47
 */
@Data
public class SysUserBo {
    /**
     * 用户账号
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(message = "最长不能超过16位，最短不能低于6位",max = 16,min = 6)
    private String pwd;

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 备注
     */
    private String backup;

    /**
     * 角色
     */
    @NotNull(message = "角色不能为空")
    private List<Long> roleIds;

}