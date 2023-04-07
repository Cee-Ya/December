package com.yarns.december.support.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yarns.december.entity.system.vo.SysUserSessionVo;
import com.yarns.december.support.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        SysUserSessionVo userInfo = CommonUtils.getCurrentUserInfo();
        this.setFieldValByName("version", 1, metaObject);
        String deleteStatus = metaObject.findProperty("deleteStatus",false);
        String createTime = metaObject.findProperty("createTime",false);
        String updateTime = metaObject.findProperty("updateTime",false);
        if(StringUtils.isNotEmpty(deleteStatus)){
            if(Objects.isNull(metaObject.getValue("deleteStatus"))){
                this.setFieldValByName("deleteStatus", 0, metaObject);
            }
        }
        if(StringUtils.isNotEmpty(createTime)){
            if(Objects.isNull(metaObject.getValue("createTime"))){
                this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
            }
        }
        if(StringUtils.isNotEmpty(updateTime)){
            if(Objects.isNull(metaObject.getValue("updateTime"))){
                this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            }
        }
        this.strictInsertFill(metaObject, "createUserName", userInfo::getUsername, String.class);
        this.strictInsertFill(metaObject, "createUserId", userInfo::getId, Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        SysUserSessionVo userInfo = CommonUtils.getCurrentUserInfo();
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updateUserName", userInfo::getUsername, String.class);
        this.strictUpdateFill(metaObject, "updateUserId", userInfo::getId, Long.class);
    }
}
