package com.yarns.december.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Maps;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.yarns.december.service.StorageFactory;
import com.yarns.december.service.StorageService;
import com.yarns.december.service.SysParamsService;
import com.yarns.december.support.constant.Constant;
import com.yarns.december.support.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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
    private UploadManager uploadManager;

    public void afterPropertiesSet() throws Exception {
         StorageFactory.register(Constant.StorageType.QINIU,this);
         uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
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

    @Override
    public String upload(InputStream is, String path) throws Exception {
        byte[] data = IOUtils.toByteArray(is);
        String token = (String) getSignature().get("token");
        String dir = sysParamsService.getSystemParamsValueByKey(Constant.Storage.DIR);
        if(StringUtils.isNotBlank(dir)){
            path = dir + StringPool.SLASH + path;
        }
        Response res = uploadManager.put(data, path, token);
        if (!res.isOK()) {
            throw new BaseException(String.format("上传失败,原因为：%s", res));
        }
        String host = sysParamsService.getSystemParamsValueByKey(Constant.Storage.HOST);
        if(StringUtils.isNotBlank(host)){
            return host + "/" + path;
        }
        throw new BaseException("结果返回失败，七牛云host为空");
    }
}
