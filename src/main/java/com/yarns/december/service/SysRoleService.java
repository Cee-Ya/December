package com.yarns.december.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysRole;

import java.util.List;

/**
 * 角色 Service接口
 *
 * @author Yarns
 * @date 2022-06-03 11:24:29
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sysRole sysRole
     * @return IPage<SysRole>
     */
    IPage<SysRole> findSysRoles(QueryRequest request, SysRole sysRole);

    /**
     * 新增角色
     *
     * @param sysRole sysRole
     */
    void createSysRole(SysRole sysRole);

    /**
     * 修改角色
     *
     * @param sysRole sysRole
     */
    void updateSysRole(SysRole sysRole);

    /**
     * 删除角色
     *
     * @param sysRoleIds 角色 id数组
     */
    void deleteSysRoles(String[] sysRoleIds);

    List<SysRole> getAllRole();

    List<SysRole> getUserRoles(Long userId);
}
