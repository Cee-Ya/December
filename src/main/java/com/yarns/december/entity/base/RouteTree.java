package com.yarns.december.entity.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yarns.december.entity.system.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 62537
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteTree extends Tree<SysMenu> {


    private String routeName;

    private String path;

    private String perms;

}
