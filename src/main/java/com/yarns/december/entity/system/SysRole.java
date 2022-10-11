package com.yarns.december.entity.system;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * 角色 Entity
 *
 * @author Yarns
 * @date 2022-06-03 11:24:29
 */
@Data
@TableName("t_sys_role")
@Alias("sysRole")
public class SysRole {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 
     */
    @TableField("role_token")
    private String roleToken;

    /**
     * 删除状态(0 未删除 1已删除)
     */
    @TableField(value = "delete_status",fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleteStatus;

    /**
     * 系统id
     */
    @TableField("root_id")
    private Integer rootId;

}