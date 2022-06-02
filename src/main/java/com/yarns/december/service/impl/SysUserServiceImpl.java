package com.yarns.december.service.impl;

import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysUser;
import com.yarns.december.service.SysUserService;
import com.yarns.december.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;

/**
 * 用户 Service实现
 *
 * @author yarns
 * @date 2022-06-03 00:00:23
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public IPage<SysUser> findSysUsers(QueryRequest request, SysUser sysUser) {
        Page<SysUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findSysUserDetailPage(page,sysUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void createSysUser(SysUser sysUser) {
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
        this.removeByIds(Arrays.asList(sysUserIds));
    }
}
