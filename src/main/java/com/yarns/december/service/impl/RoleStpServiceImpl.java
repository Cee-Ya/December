package com.yarns.december.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.yarns.december.adapter.SysRoleAdapter;
import com.yarns.december.entity.system.SysRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleStpServiceImpl implements StpInterface {
    private final SysRoleAdapter sysRoleAdapter;
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        List<SysRole> roleList = sysRoleAdapter.getUserRoles(Long.valueOf(o.toString()));
        return roleList.stream().map(SysRole::getRoleToken).collect(Collectors.toList());
    }
}
