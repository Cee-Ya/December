package com.yarns.december.mapper;

import com.yarns.december.entity.system.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
public interface SysUserRoleMapper {
    void deleteUserRoleByRoleIds(@Param("roleIds") String[] roleIds);

    void insertUserRoles(@Param("userRoles") List<SysUserRole> userRoles);

    void deleteUserRoleByUserId(Long userId);

    void deleteUserRoleByUserIds(@Param("userIds") String[] userIds);

    List<SysUserRole> getRolesByUserId(Long userId);
}
