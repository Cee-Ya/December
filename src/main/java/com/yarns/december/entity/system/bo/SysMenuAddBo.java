package com.yarns.december.entity.system.bo;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 62537
 */
@Data
public class SysMenuAddBo implements Serializable {
    private static final long serialVersionUID = -6147204862907070217L;

    /**
     * 上级资源ID
     */
    @NotNull(message = "父级资源id不能为空")
    private Long parentId;

    /**
     * 资源/按钮名称
     */
    @NotBlank(message = "资源名不能为空")
    private String menuName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * url地址
     */
    private String uri;
    /**
     * 图标
     */
    private String iconName;
    /**
     * 是否隐藏
     */
    private Integer hidden;

}
