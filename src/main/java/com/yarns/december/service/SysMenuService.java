package com.yarns.december.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysMenu;

import java.util.List;

/**
 * 菜单 Service接口
 *
 * @author Yarns
 * @date 2022-06-03 11:25:12
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sysMenu sysMenu
     * @return IPage<SysMenu>
     */
    IPage<SysMenu> findSysMenus(QueryRequest request, SysMenu sysMenu);

    /**
     * 新增菜单
     *
     * @param sysMenu sysMenu
     */
    void createSysMenu(SysMenu sysMenu);

    /**
     * 修改菜单
     *
     * @param sysMenu sysMenu
     */
    void updateSysMenu(SysMenu sysMenu);

    /**
     * 删除菜单
     *
     * @param sysMenuIds 菜单 id数组
     */
    void deleteSysMenus(String[] sysMenuIds);

    List<SysMenu> findUserMenus(Long id);

    List<SysMenu> findAllMenu();

    List<SysMenu> getAdminMenus(Long id);
}
