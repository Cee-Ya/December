package com.yarns.december.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRole {
    private Long userId;
    private Long roleId;
}
