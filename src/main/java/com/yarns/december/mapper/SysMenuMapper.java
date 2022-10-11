package com.yarns.december.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yarns.december.entity.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单 Mapper
 *
 * @author Yarns
 * @date 2022-06-03 11:25:12
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询（分页）
     *
     * @param page Page
     * @param sysMenu sysMenu
     * @return IPage<SysMenu>
     */
    IPage<SysMenu> findSysMenuDetailPage(Page page,@Param("sysMenu")  SysMenu sysMenu);

    List<SysMenu> findUserMenus(Long id);
}
