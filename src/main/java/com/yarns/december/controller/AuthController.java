package com.yarns.december.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.yarns.december.adapter.SysUserAdapter;
import com.yarns.december.entity.base.CommonResult;
import com.yarns.december.entity.base.bo.LoginBo;
import com.yarns.december.support.annotation.ControllerEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 登录部分
 * @author Yarns
 * @date 2022/6/2
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
public class AuthController {
    private final SysUserAdapter sysUserAdapter;

    /**
     * 登录接口
     * @param bo
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    @ControllerEndpoint(operation = "登录")
    public CommonResult<String> login(@RequestBody @Valid LoginBo bo) throws Exception {
        return CommonResult.ok().setResult(sysUserAdapter.login(bo));
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    @ControllerEndpoint(operation = "退出登录")
    public CommonResult logout(){
        StpUtil.logout();
        return CommonResult.ok();
    }
}
