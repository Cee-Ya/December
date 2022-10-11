package com.yarns.december.entity.system.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yarns.december.entity.base.MenuTree;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Data
@Builder
public class MenuTreeVo {
    /**
     * 菜单
     */
    private List<MenuTree> menus;

    /**
     * 权限
     */
    @JsonIgnore
    private List<PermissionsVO> permissions;

}
