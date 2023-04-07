package com.yarns.december.mapper;

import com.yarns.december.entity.system.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
public interface SysRoleMenuMapper {
    List<SysRoleMenu> getRoleMenusByRoleId(Integer roleId);

    void insertRoleMenuList(@Param("roleMenus") List<SysRoleMenu> roleMenus);

    void deleteRoleMenuByRoleId(Long id);

    void deleteRoleMenuByRoleIds(@Param("roleIds") String[] roleIds);

    void deleteRoleMenuByMenuIds(@Param("menuIds") String[] menuIds);

    void notifyAdminInsertMenu(Long menuId);
}
