package com.yarns.december.service.impl;

import com.yarns.december.entity.system.SysRoleMenu;
import com.yarns.december.mapper.SysRoleMenuMapper;
import com.yarns.december.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    private final SysRoleMenuMapper roleMenuMapper;

    @Override
    public List<SysRoleMenu> getRoleMenusByRoleId(Integer roleId) {
        return roleMenuMapper.getRoleMenusByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRoleMenuList(List<SysRoleMenu> roleMenus) {
        roleMenuMapper.insertRoleMenuList(roleMenus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenuByRoleId(Long id) {
        roleMenuMapper.deleteRoleMenuByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenuByRoleIds(String[] roleIds) {
        roleMenuMapper.deleteRoleMenuByRoleIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenuByMenuIds(String[] menuIds) {
        roleMenuMapper.deleteRoleMenuByMenuIds(menuIds);
    }

    @Override
    public void notifyAdminInsertMenu(Long menuId) {
        roleMenuMapper.notifyAdminInsertMenu(menuId);
    }
}
