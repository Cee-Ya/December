package com.yarns.december.entity.system;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统参数表 Entity
 *
 * @author yarns
 * @date 2023-05-08 20:28:36
 */
@Data
@TableName("t_sys_params")
@Alias("sysParams")
public class SysParams implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 参数名称
     */
    @TableField("name")
    private String name;

    /**
     * 是否系统参数(0否 1是)
     */
    @TableField("system_param_flag")
    private Integer systemParamFlag;

    /**
     *
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 参数值
     */
    @TableField("param_value")
    private String paramValue;

    /**
     * 
     */
    @TableField(value = "version",fill = FieldFill.INSERT)
    @Version
    private Integer version;

}