package com.yarns.december.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysMenu;
import com.yarns.december.mapper.SysMenuMapper;
import com.yarns.december.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 菜单 Service实现
 *
 * @author Yarns
 * @date 2022-06-03 11:25:12
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public IPage<SysMenu> findSysMenus(QueryRequest request, SysMenu sysMenu) {
        Page<SysMenu> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findSysMenuDetailPage(page,sysMenu);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void createSysMenu(SysMenu sysMenu) {
        sysMenu.setRootId(2);
        this.save(sysMenu);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateSysMenu(SysMenu sysMenu) {
        this.updateById(sysMenu);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteSysMenus(String[] sysMenuIds) {
        this.removeByIds(Arrays.asList(sysMenuIds));
    }

    @Override
    public List<SysMenu> findUserMenus(Long id) {
        return this.baseMapper.findUserMenus(id);
    }

    @Override
    public List<SysMenu> findAllMenu() {
        return baseMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getRootId,2));
//        return baseMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public List<SysMenu> getAdminMenus(Long id) {
        return baseMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getRootId,2).
                orderByAsc(SysMenu::getSort));
    }

}
