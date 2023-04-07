package com.yarns.december.entity.system;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * 菜单 Entity
 *
 * @author Yarns
 * @date 2022-06-03 11:25:12
 */
@Data
@TableName("t_sys_menu")
@Alias("sysMenu")
public class SysMenu {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级菜单ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 菜单等级
     */
    @TableField("menu_level")
    private Integer menuLevel;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 图标名称
     */
    @TableField("icon_name")
    private String iconName;

    /**
     * 接口前缀
     */
    @TableField("uri")
    private String uri;

    /**
     * 是否隐藏
     */
    @TableField("hidden")
    private Integer hidden;

    /**
     * 
     */
    @TableField("sort")
    private Integer sort;

    /**
     *
     */
    @TableField("root_id")
    private Integer rootId;

}