package com.yarns.december.entity.system.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 修改密码 Entity
 *
 * @author Yarns
 * @date 2022-06-03 11:02:47
 */
@Data
public class SysUserPutPassBo {
    /**
     * 用户账号
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 用户密码
     */
    @NotBlank(message = "原密码不能为空")
    @Size(message = "最长不能超过16位，最短不能低于6位",max = 16,min = 6)
    private String oldPwd;

    /**
     * 用户密码
     */
    @NotBlank(message = "新密码不能为空")
    @Size(message = "最长不能超过16位，最短不能低于6位",max = 16,min = 6)
    private String newPwd;

}