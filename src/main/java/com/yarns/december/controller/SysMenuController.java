package com.yarns.december.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.yarns.december.adapter.SysMenuAdapter;
import com.yarns.december.entity.base.CommonResult;
import com.yarns.december.entity.base.RoleMenuTree;
import com.yarns.december.entity.system.bo.SysMenuAddBo;
import com.yarns.december.entity.system.bo.SysMenuEditBo;
import com.yarns.december.entity.system.vo.MenuTreeVo;
import com.yarns.december.service.SysMenuService;
import com.yarns.december.support.annotation.ControllerEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 菜单管理
 *
 * @author Yarns
 * @date 2022-06-03 11:25:12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sysMenu")
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
public class SysMenuController {
    private final SysMenuAdapter sysMenuAdapter;

    /**
     * 获取当前用户的菜单
     * @return
     */
    @GetMapping("user")
    public CommonResult<MenuTreeVo> getUserSystems() {
        return CommonResult.ok().setResult(sysMenuAdapter.getUserMenus());
    }

    /**
     * 角色选择时需要展示的资源信息
     * @return
     */
    @GetMapping("roleTree")
    public CommonResult<List<RoleMenuTree>> menuRoleTree() {
        return CommonResult.ok().setResult(this.sysMenuAdapter.findMenusByRoleEdit());
    }

    /**
     * 新增菜单
     * @param sysMenu 菜单对象
     * @return
     */
    @PostMapping
    @ControllerEndpoint(operation = "新增菜单", exceptionMessage = "新增菜单失败")
    public CommonResult addSysMenu(@Valid @RequestBody SysMenuAddBo sysMenu) {
        this.sysMenuAdapter.createSysMenu(sysMenu);
        return CommonResult.ok();
    }

    /**
     * 修改菜单
     * @param sysMenu 菜单对象
     * @return
     */
    @PutMapping
    @ControllerEndpoint(operation = "修改菜单", exceptionMessage = "修改菜单失败")
    public CommonResult updateSysMenu(@Valid @RequestBody SysMenuEditBo sysMenu) {
        this.sysMenuAdapter.updateSysMenu(sysMenu);
        return CommonResult.ok();
    }

    /**
     * 删除菜单
     * @param sysMenuIds 需要删除的id
     * @return
     */
    @DeleteMapping
    @ControllerEndpoint(operation = "删除菜单", exceptionMessage = "删除菜单失败")
    public CommonResult deleteSysMenus(@NotBlank(message = "菜单ids不能为空") String sysMenuIds) {
        String[] ids = sysMenuIds.split(StringPool.COMMA);
        this.sysMenuAdapter.deleteSysMenus(ids);
        return CommonResult.ok();
    }
}
