package com.yarns.december.entity.system;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * 用户 Entity
 *
 * @author Yarns
 * @date 2022-06-03 11:02:47
 */
@Data
@TableName("t_sys_user")
@Alias("sysUser")
public class SysUser {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户账号
     */
    @TableField("username")
    private String username;

    /**
     * 用户密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 备注
     */
    @TableField("backup")
    private String backup;


    /**
     * 用户状态(1启动 0冻结)
     */
    @TableField("user_status")
    private Boolean userStatus;

    /**
     * 删除状态(0 未删除 1已删除)
     */
    @TableField(value = "delete_status",fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleteStatus;

    /**
     * 
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 
     */
    @TableField(value = "version",fill = FieldFill.INSERT)
    @Version
    private Integer version;

}