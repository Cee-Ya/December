package com.yarns.december.service.impl;

import com.google.common.collect.Maps;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.yarns.december.service.StorageFactory;
import com.yarns.december.service.StorageService;
import com.yarns.december.service.SysParamsService;
import com.yarns.december.support.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 七牛云存储实现类
 * <a href="https://developer.qiniu.com/kodo/sdk/1239/java#server-upload">...</a>
 * @apiNote 七牛云只需要配置accessId, secretKey, bucket即可
 * @author Yarns
 */
@SuppressWarnings("unchecked")
@Service("qiniuStorageService")
@RequiredArgsConstructor
public class QiniuStorageServiceImpl implements StorageService, InitializingBean {
    private final SysParamsService sysParamsService;

    public void afterPropertiesSet() throws Exception {
         StorageFactory.register(Constant.StorageType.QINIU,this);
    }
    @Override
    public Map<String, Object> getSignature() throws Exception {
        String accessId = sysParamsService.getSystemParamsValueByKey(Constant.Storage.ACCESS_ID);
        String secretKey = sysParamsService.getSystemParamsValueByKey(Constant.Storage.ACCESS_KEY);
        String bucket = sysParamsService.getSystemParamsValueByKey(Constant.Storage.BUCKET);
        Auth auth = Auth.create(accessId, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        Map<String,Object> resultMap = Maps.newHashMapWithExpectedSize(1);
        resultMap.put("token",upToken);
        return resultMap;
    }
}
