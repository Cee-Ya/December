package com.yarns.december.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yarns.december.entity.base.vo.EnumsVo;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysUser;
import com.yarns.december.entity.system.vo.SysUserPageVo;
import com.yarns.december.support.exception.BaseException;

import java.util.List;

/**
 * 用户 Service接口
 *
 * @author Yarns
 * @date 2022-06-03 11:02:47
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sysUser sysUser
     * @return IPage<SysUser>
     */
    IPage<SysUserPageVo> findSysUsers(QueryRequest request, SysUser sysUser);

    /**
     * 新增用户
     *
     * @param sysUser sysUser
     */
    void createSysUser(SysUser sysUser) throws BaseException;

    /**
     * 修改用户
     *
     * @param sysUser sysUser
     */
    void updateSysUser(SysUser sysUser);

    /**
     * 删除用户
     *
     * @param sysUserIds 用户 id数组
     */
    void deleteSysUsers(String[] sysUserIds);

    SysUser getUserByLoginName(String username);

    void updateSysUserStateByAgentId(Long id, Boolean agentStatus);

    void updateSysUserStateByUserIds(List<Long> agentIds, Boolean agentStatus);

    List<EnumsVo> getUserByAgentWorker(String mobileOrUsername);

    List<EnumsVo> getUserByAgent(String mobileOrUsername);

    SysUser getUserByMobile(String mobile);
}
