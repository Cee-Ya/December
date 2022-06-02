package com.yarns.december.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.base.ResponseBo;
import com.yarns.december.entity.system.SysUser;
import com.yarns.december.support.annotation.ControllerEndpoint;
import com.yarns.december.support.utils.CommonUtils;
import com.yarns.december.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 用户 Controller
 *
 * @author yarns
 * @date 2022-06-03 00:00:23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sysUser")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 用户列表
     * @param request 分页部分
     * @param sysUser 查询对象
     * @return
     */
    @GetMapping("list")
    public ResponseBo sysUserList(QueryRequest request, SysUser sysUser) {
        Map<String, Object> dataTable = CommonUtils.getDataTable(this.sysUserService.findSysUsers(request, sysUser));
        return ResponseBo.result(dataTable);
    }

    /**
     * 新增用户
     * @param sysUser 用户对象
     * @return
     */
    @PostMapping
    @ControllerEndpoint(operation = "新增用户", exceptionMessage = "新增用户失败")
    public ResponseBo addSysUser(@Valid @RequestBody SysUser sysUser) {
        this.sysUserService.createSysUser(sysUser);
        return ResponseBo.ok();
    }

    /**
     * 修改用户
     * @param sysUser 用户对象
     * @return
     */
    @PutMapping
    @ControllerEndpoint(operation = "修改用户", exceptionMessage = "修改用户失败")
    public ResponseBo updateSysUser(@Valid @RequestBody SysUser sysUser) {
        this.sysUserService.updateSysUser(sysUser);
        return ResponseBo.ok();
    }

    /**
     * 删除用户
     * @param sysUserIds 需要删除的id
     * @return
     */
    @DeleteMapping
    @ControllerEndpoint(operation = "删除用户", exceptionMessage = "删除用户失败")
    public ResponseBo deleteSysUsers(@NotBlank(message = "{required}") String sysUserIds) {
        String[] ids = sysUserIds.split(StringPool.COMMA);
        this.sysUserService.deleteSysUsers(ids);
        return ResponseBo.ok();
    }
}
