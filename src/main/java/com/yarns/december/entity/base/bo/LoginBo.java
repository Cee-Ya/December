package com.yarns.december.entity.base.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @autor Yarns
 * @data 2022/5/25
 */
@Data
public class LoginBo {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
