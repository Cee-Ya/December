package com.yarns.december.adapter;

import cn.dev33.satoken.stp.StpUtil;
import com.google.common.collect.Lists;
import com.yarns.december.entity.base.MenuTree;
import com.yarns.december.entity.base.RoleMenuTree;
import com.yarns.december.entity.system.SysMenu;
import com.yarns.december.entity.system.bo.SysMenuAddBo;
import com.yarns.december.entity.system.bo.SysMenuEditBo;
import com.yarns.december.entity.system.vo.MenuTreeVo;
import com.yarns.december.entity.system.vo.PermissionsVO;
import com.yarns.december.entity.system.vo.SysUserSessionVo;
import com.yarns.december.service.SysMenuService;
import com.yarns.december.service.SysRoleMenuService;
import com.yarns.december.support.constant.Constant;
import com.yarns.december.support.utils.CommonUtils;
import com.yarns.december.support.utils.TreeUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Component
@RequiredArgsConstructor
public class SysMenuAdapter {
    private final static String TOP_NODE_ID = "0";
    private final SysMenuService sysMenuService;
    private final SysRoleMenuService sysRoleMenuService;

    public MenuTreeVo getUserMenus() {
        SysUserSessionVo userSessionVo = CommonUtils.getCurrentUserInfo();
        // 构建用户路由对象
        List<SysMenu> menus;
        if(StpUtil.hasRole(Constant.ADMIN_ROLE)){
            menus = this.sysMenuService.getAdminMenus(userSessionVo.getId());
        }else {
            menus = this.sysMenuService.findUserMenus(userSessionVo.getId());
        }
        List<MenuTree> trees = new ArrayList<>();
        buildTrees(trees,menus);
        // 获取用户权限信息
        List<PermissionsVO> userPermissions = Lists.newArrayList();
        return MenuTreeVo.builder().menus((List<MenuTree>) TreeUtil.build(trees)).permissions(userPermissions).build();
    }

    private void buildTrees(List<MenuTree> trees, List<SysMenu> menus) {
        menus.forEach(menu -> {
            MenuTree tree = new MenuTree();
            tree.setId(menu.getId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setLabel(menu.getMenuName());
            tree.setPath(menu.getUri());
            tree.setHidden(menu.getHidden());
            tree.setIcon(menu.getIconName());
            tree.setOrderNum(menu.getSort());
            trees.add(tree);
        });
    }

    public List<RoleMenuTree> findMenusByRoleEdit() {
        List<SysMenu> menus = sysMenuService.findAllMenu();
        List<RoleMenuTree> trees = Lists.newArrayList();
        buildRoleMenuTrees(trees, menus);
        return buildRoleMenu(trees);
    }

    private void buildRoleMenuTrees(List<RoleMenuTree> trees, List<SysMenu> menus) {
        menus.forEach(menu -> {
            RoleMenuTree tree = new RoleMenuTree();
            tree.setId(menu.getId().toString());
            if(menu.getParentId() != null){
                tree.setParentId(menu.getParentId().toString());
            }
            tree.setLabel(menu.getMenuName());
            trees.add(tree);
        });
    }

    private List<RoleMenuTree> buildRoleMenu(List<RoleMenuTree> trees){
        if (trees == null) {
            return null;
        }
        List<RoleMenuTree> topNodes = new ArrayList<>();
        trees.forEach(t ->{
            String pid = t.getParentId();
            if (pid == null || TOP_NODE_ID.equals(pid)) {
                topNodes.add(t);
                return;
            }
            for (RoleMenuTree n : trees) {
                String id = n.getId();

                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null) {
                        n.initChildren();
                    }
                    n.getChildren().add(t);
                    t.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
            }
            if (topNodes.isEmpty()) {
                topNodes.add(t);
            }
        });
        return topNodes;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createSysMenu(SysMenuAddBo sysMenu) {
        val menu = new SysMenu();
        BeanUtils.copyProperties(sysMenu,menu);
        sysMenuService.createSysMenu(menu);
        //直接通知超管账号添加该菜单
        sysRoleMenuService.notifyAdminInsertMenu(menu.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSysMenu(SysMenuEditBo sysMenu) {
        val menu = new SysMenu();
        BeanUtils.copyProperties(sysMenu,menu);
        sysMenuService.updateSysMenu(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSysMenus(String[] menuIds) {
        sysMenuService.deleteSysMenus(menuIds);
        sysRoleMenuService.deleteRoleMenuByMenuIds(menuIds);
    }
}
