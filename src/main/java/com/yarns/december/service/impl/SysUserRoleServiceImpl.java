package com.yarns.december.service.impl;

import com.yarns.december.entity.system.SysUserRole;
import com.yarns.december.mapper.SysUserRoleMapper;
import com.yarns.december.service.SysUserRoleService;
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
public class SysUserRoleServiceImpl implements SysUserRoleService {
    private final SysUserRoleMapper userRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRoleByRoleIds(String[] roleIds) {
        userRoleMapper.deleteUserRoleByRoleIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserRoles(List<SysUserRole> userRoles) {
        userRoleMapper.insertUserRoles(userRoles);
    }

    @Override
    public void deleteUserRoleByUserId(Long userId) {
        userRoleMapper.deleteUserRoleByUserId(userId);
    }

    @Override
    public void deleteUserRoleByUserIds(String[] userIds) {
        userRoleMapper.deleteUserRoleByUserIds(userIds);
    }

    @Override
    public List<SysUserRole> getRolesByUserId(Long userId) {
        return userRoleMapper.getRolesByUserId(userId);
    }
}
