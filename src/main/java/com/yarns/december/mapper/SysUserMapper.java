package com.yarns.december.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yarns.december.entity.base.vo.EnumsVo;
import com.yarns.december.entity.system.SysUser;
import com.yarns.december.entity.system.vo.SysUserPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 Mapper
 *
 * @author Yarns
 * @date 2022-06-03 11:02:47
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询（分页）
     *
     * @param page Page
     * @param sysUser sysUser
     * @return IPage<SysUser>
     */
    IPage<SysUserPageVo> findSysUserDetailPage(Page page, @Param("sysUser")  SysUser sysUser);

    void updateSysUserStateByAgentId(@Param("id") Long id, @Param("agentStatus") Boolean agentStatus);

    void updateSysUserStateByUserIds(@Param("agentIds") List<Long> agentIds, @Param("agentStatus") Boolean agentStatus);

    SysUser getByLoginName(String username);
}
