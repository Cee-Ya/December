package com.yarns.december.entity.system.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Data
public class SysUserEditBo {
    @NotNull(message = "id不能为空")
    private Long id;
    private String username;
    private String nickname;
    private String mobile;
    private String backup;
    private List<Long> roleIds;
}
