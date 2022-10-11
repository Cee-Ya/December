package com.yarns.december.service;


import com.yarns.december.entity.system.SysUserRole;

import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
public interface SysUserRoleService {
    void deleteUserRoleByRoleIds(String[] roleIds);

    void insertUserRoles(List<SysUserRole> userRoles);

    void deleteUserRoleByUserId(Long userId);

    void deleteUserRoleByUserIds(String[] userIds);

    List<SysUserRole> getRolesByUserId(Long userId);
}
