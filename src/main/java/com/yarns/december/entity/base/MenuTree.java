package com.yarns.december.entity.base;

import com.yarns.december.entity.system.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author color
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends Tree<SysMenu>{
    /**
     * 图标
     */
    private String icon;
    /**
     * url地址
     */
    private String path;
    /**
     * 排序标识
     */
    private Integer orderNum;
    /**
     * 是否隐藏
     */
    private Integer hidden;
}
