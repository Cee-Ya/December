package com.yarns.december.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yarns.december.entity.system.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色 Mapper
 *
 * @author Yarns
 * @date 2022-06-03 11:24:29
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询（分页）
     *
     * @param page Page
     * @param sysRole sysRole
     * @return IPage<SysRole>
     */
    IPage<SysRole> findSysRoleDetailPage(Page page,@Param("sysRole")  SysRole sysRole);

    List<SysRole> getUserRoles(Long userId);
}
