package com.yarns.december.entity.base;

import com.yarns.december.entity.system.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author color
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuTree extends Tree<SysMenu> {

}
