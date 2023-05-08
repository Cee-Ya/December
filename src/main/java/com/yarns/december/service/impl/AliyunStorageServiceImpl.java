package com.yarns.december.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.yarns.december.service.StorageFactory;
import com.yarns.december.service.StorageService;
import com.yarns.december.service.SysParamsService;
import com.yarns.december.support.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 阿里云存储实现类
 * @author Yarns
 */
@Service
@RequiredArgsConstructor
public class AliyunStorageServiceImpl implements StorageService, InitializingBean {
    private final SysParamsService sysParamsService;

    private OSS oss;
    private String accessId;
    private String dir;
    private String host;
    private String callbackUrl;


    @Override
    public void afterPropertiesSet() throws Exception {
         StorageFactory.register(this.getClass().getName(),this);

         // 从数据库中读取相关配置
         this.accessId = sysParamsService.getSysParamsValueByKey(Constant.Storage.ACCESS_ID);
         this.dir = sysParamsService.getSysParamsValueByKey(Constant.Storage.DIR);
         this.callbackUrl = sysParamsService.getSysParamsValueByKey(Constant.Storage.CALLBACK_URL);
         this.host = "http://" + sysParamsService.getSysParamsValueByKey(Constant.Storage.BUCKET) + "." + sysParamsService.getSysParamsValueByKey(Constant.Storage.ENDPOINT);
         oss = new OSSClientBuilder().build(sysParamsService.getSysParamsValueByKey(Constant.Storage.ENDPOINT),
                 this.accessId, sysParamsService.getSysParamsValueByKey(Constant.Storage.ACCESS_KEY));
    }
    @Override
    public Map<String, String> getSignature() throws Exception {
        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = oss.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = oss.calculatePostSignature(postPolicy);
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        respMap.put("accessid",accessId);
        respMap.put("policy", encodedPolicy);
        respMap.put("signature", postSignature);
        respMap.put("dir", dir);
        respMap.put("host", host);
        respMap.put("expire", String.valueOf(expireEndTime / 1000));
        if(StringUtils.isNotBlank(callbackUrl)){
            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", callbackUrl);
            jasonCallback.put("callbackBody",
                    "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            respMap.put("callback", base64CallbackBody);
        }
        return respMap;
    }
}
