package com.yarns.december.adapter;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.yarns.december.entity.system.SysRole;
import com.yarns.december.entity.system.SysRoleMenu;
import com.yarns.december.entity.system.bo.SysRoleAddBo;
import com.yarns.december.entity.system.bo.SysRoleEditBo;
import com.yarns.december.entity.system.vo.SysRoleAllVo;
import com.yarns.december.entity.system.vo.SysRoleMenuVO;
import com.yarns.december.service.SysMenuService;
import com.yarns.december.service.SysRoleMenuService;
import com.yarns.december.service.SysRoleService;
import com.yarns.december.service.SysUserRoleService;
import com.yarns.december.support.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Component
@RequiredArgsConstructor
public class SysRoleAdapter {

    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysUserRoleService sysUserRoleService;
    private final SysMenuService sysMenuService;

    /**
     * 根据角色id获取角色
     * @param roleId
     * @return
     */
    public SysRoleMenuVO getRoleById(Integer roleId){
        SysRole r = this.sysRoleService.getById(roleId);
        SysRoleMenuVO vo = new SysRoleMenuVO();
        BeanUtils.copyProperties(r,vo);
        List<SysRoleMenu> roleMenus = this.sysRoleMenuService.getRoleMenusByRoleId(roleId);
        StringBuilder sb = new StringBuilder();
        for (SysRoleMenu roleMenu : roleMenus) {
            if(sb.length() > 0){
                sb.append(StringPool.COMMA).append(roleMenu.getMenuId());
            }else {
                sb.append(roleMenu.getMenuId());
            }
        }
        vo.setMenuIds(sb.toString());
        return vo;
    }

    public List<SysRoleAllVo> getRoleByAll() {
        var allRoles = sysRoleService.getAllRole();
        List<SysRoleAllVo> roleAllVOS = new ArrayList<>();
        allRoles.forEach(role -> {
            var roleAllVO = new SysRoleAllVo();
            BeanUtils.copyProperties(role,roleAllVO);
            roleAllVOS.add(roleAllVO);
        });
        return roleAllVOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createSysRole(SysRoleAddBo sysRole) throws BaseException {
        var role = new SysRole();
        BeanUtils.copyProperties(sysRole,role);
        sysRoleService.createSysRole(role);
        if (StringUtils.isNotBlank(sysRole.getMenuIds())) {
            String[] menuIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(sysRole.getMenuIds(), StringPool.COMMA);
            setRoleMenus(role, menuIds);
        }
    }

    public void setRoleMenus(SysRole role, String[] menuIds) throws BaseException {
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        for (String menuId : menuIds) {
            //确定当前角色的角色类型后再判断传入的资源的菜单类型
            var menu = sysMenuService.getById(menuId);
            if(Objects.isNull(menu)){
                throw new BaseException(String.format("菜单id[%d]不合法",menu.getId()));
            }
            var roleMenu = new SysRoleMenu();
            if (StringUtils.isNotBlank(menuId)) {
                roleMenu.setMenuId(Long.valueOf(menuId));
            }
            roleMenu.setRoleId(role.getId());
            roleMenus.add(roleMenu);
        }
        sysRoleMenuService.insertRoleMenuList(roleMenus);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSysRole(SysRoleEditBo sysRole) throws BaseException {
        var role = new SysRole();
        BeanUtils.copyProperties(sysRole,role);
        sysRoleService.updateSysRole(role);
        sysRoleMenuService.deleteRoleMenuByRoleId(role.getId());
        if (StringUtils.isNotBlank(sysRole.getMenuIds())) {
            String[] menuIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(sysRole.getMenuIds(), StringPool.COMMA);
            setRoleMenus(role, menuIds);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSysRoles(String[] roleIds) {
        sysRoleService.deleteSysRoles(roleIds);
        this.sysUserRoleService.deleteUserRoleByRoleIds(roleIds);
        this.sysRoleMenuService.deleteRoleMenuByRoleIds(roleIds);
    }

    public List<SysRole> getUserRoles(Long userId) {
        return sysRoleService.getUserRoles(userId);
    }
}
