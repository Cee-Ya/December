package com.yarns.december.service;


import com.yarns.december.entity.system.SysRoleMenu;

import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
public interface SysRoleMenuService {
    List<SysRoleMenu> getRoleMenusByRoleId(Integer roleId);

    void insertRoleMenuList(List<SysRoleMenu> roleMenus);

    void deleteRoleMenuByRoleId(Long id);

    void deleteRoleMenuByRoleIds(String[] roleIds);

    void deleteRoleMenuByMenuIds(String[] menuIds);

    void notifyAdminInsertMenu(Long menuId);
}
