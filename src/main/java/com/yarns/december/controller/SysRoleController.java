package com.yarns.december.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.yarns.december.adapter.SysRoleAdapter;
import com.yarns.december.entity.base.CommonResult;
import com.yarns.december.entity.base.PageRes;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysRole;
import com.yarns.december.entity.system.bo.SysRoleAddBo;
import com.yarns.december.entity.system.bo.SysRoleEditBo;
import com.yarns.december.entity.system.vo.SysRoleAllVo;
import com.yarns.december.entity.system.vo.SysRoleMenuVO;
import com.yarns.december.service.SysRoleService;
import com.yarns.december.support.annotation.ControllerEndpoint;
import com.yarns.december.support.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色管理
 *
 * @author Yarns
 * @date 2022-06-03 11:24:29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sysRole")
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
public class SysRoleController {
    private final SysRoleService sysRoleService;
    private final SysRoleAdapter sysRoleAdapter;

    /**
     * 角色列表
     * @param request 分页部分
     * @param sysRole 查询对象
     * @return
     */
    @GetMapping("list")
    public CommonResult<PageRes<SysRole>> sysRoleList(QueryRequest request, SysRole sysRole) {
        return CommonResult.ok().setResult(PageRes.init(this.sysRoleService.findSysRoles(request, sysRole)));
    }

    /**
     * 根据id获取角色
     * @param roleId
     * @return
     */
    @GetMapping
    public CommonResult<SysRoleMenuVO> getRoleById(@NotNull(message = "角色id不能为空") Integer roleId) {
        return CommonResult.ok().setResult(sysRoleAdapter.getRoleById(roleId));
    }

    /**
     * 获取所有角色
     * @return
     */
    @GetMapping("all")
    public CommonResult<List<SysRoleAllVo>> getRoleByAll() {
        return CommonResult.ok().setResult(sysRoleAdapter.getRoleByAll());
    }

    /**
     * 新增角色
     * @param sysRole 角色对象
     * @return
     */
    @PostMapping
    @ControllerEndpoint(operation = "新增角色")
    public CommonResult addSysRole(@Valid @RequestBody SysRoleAddBo sysRole) throws BaseException {
        this.sysRoleAdapter.createSysRole(sysRole);
        return CommonResult.ok();
    }

    /**
     * 修改角色
     * @param sysRole 角色对象
     * @return
     */
    @PutMapping
    @ControllerEndpoint(operation = "修改角色")
    public CommonResult updateSysRole(@Valid @RequestBody SysRoleEditBo sysRole) throws BaseException {
        this.sysRoleAdapter.updateSysRole(sysRole);
        return CommonResult.ok();
    }

    /**
     * 删除角色
     * @param sysRoleIds 需要删除的id
     * @return
     */
    @DeleteMapping
    @ControllerEndpoint(operation = "删除角色")
    public CommonResult deleteSysRoles(@NotBlank(message = "角色ids不能为空") String sysRoleIds) {
        String[] ids = sysRoleIds.split(StringPool.COMMA);
        this.sysRoleAdapter.deleteSysRoles(ids);
        return CommonResult.ok();
    }
}
