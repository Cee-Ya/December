package com.yarns.december.entity.base.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Yarns
 * @data 2022/5/25
 */
@Data
public class LoginBo {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "验证码Key不能为空")
    private String key;
    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
