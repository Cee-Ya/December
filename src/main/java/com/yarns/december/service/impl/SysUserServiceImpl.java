package com.yarns.december.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yarns.december.entity.base.vo.EnumsVo;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysUser;
import com.yarns.december.entity.system.vo.SysUserPageVo;
import com.yarns.december.mapper.SysUserMapper;
import com.yarns.december.service.SysUserService;
import com.yarns.december.support.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户 Service实现
 *
 * @author Yarns
 * @date 2022-06-03 11:02:47
 */
@Service("sysUserService")
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Override
    public IPage<SysUserPageVo> findSysUsers(QueryRequest request, SysUser sysUser) {
        Page<SysUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findSysUserDetailPage(page,sysUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void createSysUser(SysUser sysUser) throws BaseException {
        this.save(sysUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateSysUser(SysUser sysUser) {
        this.updateById(sysUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteSysUsers(String[] sysUserIds) {
        List<Long> ids = Lists.newArrayList();
        for (String sysUserId : sysUserIds) {
            ids.add(Long.parseLong(sysUserId));
        }
        this.removeByIds(ids);
    }

    @Override
    public SysUser getUserByLoginName(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateSysUserStateByAgentId(Long id, Boolean agentStatus) {
        baseMapper.updateSysUserStateByAgentId(id,agentStatus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateSysUserStateByUserIds(List<Long> agentIds, Boolean agentStatus) {
        baseMapper.updateSysUserStateByUserIds(agentIds,agentStatus);
    }

    @Override
    public SysUser getUserByMobile(String mobile) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMobile,mobile));
    }
}
