package com.yarns.december.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.yarns.december.adapter.SysUserAdapter;
import com.yarns.december.entity.base.CommonResult;
import com.yarns.december.entity.base.PageRes;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.base.vo.EnumsVo;
import com.yarns.december.entity.system.bo.SysUserBo;
import com.yarns.december.entity.system.bo.SysUserEditBo;
import com.yarns.december.entity.system.bo.SysUserPageBo;
import com.yarns.december.entity.system.bo.SysUserPutPassBo;
import com.yarns.december.entity.system.vo.SysUserInfoVo;
import com.yarns.december.entity.system.vo.SysUserPageVo;
import com.yarns.december.entity.system.vo.SysUserSessionVo;
import com.yarns.december.support.annotation.ControllerEndpoint;
import com.yarns.december.support.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户管理
 *
 * @author Yarns
 * @date 2022-06-03 11:02:47
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sysUser")
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
public class SysUserController {
    private final SysUserAdapter sysUserAdapter;

    /**
     * 获取当前登录人基本信息
     * @return
     */
    @GetMapping("current")
    public CommonResult<SysUserSessionVo> getCurrentUserInfo(){
        return CommonResult.ok().setResult(sysUserAdapter.getCurrentUserInfo());
    }

    /**
     * 用户列表
     * @param request 分页部分
     * @param sysUser 查询对象
     * @return
     */
    @GetMapping("list")
    public CommonResult<PageRes<SysUserPageVo>> sysUserList(QueryRequest request, SysUserPageBo sysUser) {
        return CommonResult.ok().setResult(PageRes.init(this.sysUserAdapter.findSysUsers(request, sysUser)));
    }

    /**
     * 获取代理商员工用户列表
     * @param mobileOrUsername
     * @return
     */
    @GetMapping("list/agent/worker")
    public CommonResult<List<EnumsVo>> getAgentWorkerChooseUsers(String mobileOrUsername){
        return CommonResult.ok().setResult(this.sysUserAdapter.getAgentWorkerChooseUsers(mobileOrUsername));
    }

    /**
     * 获取代理商用户列表
     * @param mobileOrUsername
     * @return
     */
    @GetMapping("list/agent")
    public CommonResult<List<EnumsVo>> getAgentChooseUsers(String mobileOrUsername){
        return CommonResult.ok().setResult(this.sysUserAdapter.getAgentChooseUsers(mobileOrUsername));
    }


    /**
     * 新增用户
     * @param sysUser 用户对象
     * @return
     */
    @PostMapping
    @ControllerEndpoint(operation = "新增用户")
    public CommonResult addSysUser(@Valid @RequestBody SysUserBo sysUser) throws BaseException {
        this.sysUserAdapter.createSysUser(sysUser);
        return CommonResult.ok();
    }

    /**
     * 获取单个用户信息
     * @param userId 用户id
     * @return
     */
    @GetMapping
    public CommonResult<SysUserInfoVo> getInfo(@NotNull(message = "用户id不能为空") Long userId) {
        return CommonResult.ok().setResult(this.sysUserAdapter.getUserInfo(userId));
    }

    /**
     * 修改用户密码
     * @param sysUser 用户对象
     * @return
     */
    @PutMapping("pass")
    @ControllerEndpoint(operation = "修改密码")
    public CommonResult updatePass(@Valid @RequestBody SysUserPutPassBo sysUser) throws BaseException {
        this.sysUserAdapter.updatePass(sysUser);
        return CommonResult.ok();
    }

    /**
     * 修改用户
     * @param sysUser 用户对象
     * @return
     */
    @PutMapping
    @ControllerEndpoint(operation = "修改用户信息")
    public CommonResult updateUser(@Valid @RequestBody SysUserEditBo sysUser) throws BaseException {
        this.sysUserAdapter.updateUser(sysUser);
        return CommonResult.ok();
    }

    /**
     * 删除用户
     * @param sysUserIds 需要删除的id
     * @return
     */
    @DeleteMapping
    @ControllerEndpoint(operation = "删除用户")
    public CommonResult deleteSysUsers(@NotBlank(message = "{required}") String sysUserIds) throws BaseException {
        val ids = sysUserIds.split(StringPool.COMMA);
        this.sysUserAdapter.deleteSysUsers(ids);
        return CommonResult.ok();
    }
}
