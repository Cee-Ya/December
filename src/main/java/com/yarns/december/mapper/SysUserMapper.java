package com.yarns.december.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yarns.december.entity.system.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper
 *
 * @author yarns
 * @date 2022-06-03 00:00:23
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询（分页）
     *
     * @param page Page
     * @param sysUser sysUser
     * @return IPage<SysUser>
     */
    IPage<SysUser> findSysUserDetailPage(Page page,@Param("sysUser")  SysUser sysUser);
}
