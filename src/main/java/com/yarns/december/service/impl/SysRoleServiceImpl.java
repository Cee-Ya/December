package com.yarns.december.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysRole;
import com.yarns.december.mapper.SysRoleMapper;
import com.yarns.december.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.List;

/**
 * 角色 Service实现
 *
 * @author Yarns
 * @date 2022-06-03 11:24:29
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public IPage<SysRole> findSysRoles(QueryRequest request, SysRole sysRole) {
        Page<SysRole> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findSysRoleDetailPage(page,sysRole);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void createSysRole(SysRole sysRole) {
        sysRole.setRootId(2);
        this.save(sysRole);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateSysRole(SysRole sysRole) {
        this.updateById(sysRole);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteSysRoles(String[] sysRoleIds) {
        this.removeByIds(Arrays.asList(sysRoleIds));
    }

    @Override
    public List<SysRole> getAllRole() {
        return baseMapper.selectList(new LambdaQueryWrapper<SysRole>());
    }

    @Override
    public List<SysRole> getUserRoles(Long userId) {
        return baseMapper.getUserRoles(userId);
    }
}
