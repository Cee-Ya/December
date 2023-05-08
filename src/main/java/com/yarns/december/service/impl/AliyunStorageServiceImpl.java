package com.yarns.december.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.google.common.collect.Maps;
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
import java.util.Map;

/**
 * 阿里云存储实现类
 * @apiNote 其实不需要用bean的方式实现，直接使用实例也行，至于sysParamsService怎么获取，
 *          直接使用SpringContextUtil.getBean(SysParamsService.class)也行
 * @author Yarns
 */
@Service("aliyunStorageService")
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
         StorageFactory.register(Constant.StorageType.ALI,this);

         // 从数据库中读取相关配置
         this.accessId = sysParamsService.getSysParamsValueByKey(Constant.Storage.ACCESS_ID);
         this.dir = sysParamsService.getSysParamsValueByKey(Constant.Storage.DIR);
         this.callbackUrl = sysParamsService.getSysParamsValueByKey(Constant.Storage.CALLBACK_URL);
         String host = sysParamsService.getSysParamsValueByKey(Constant.Storage.HOST);
         if(StringUtils.isBlank(host)){
             this.host = "http://" + sysParamsService.getSysParamsValueByKey(Constant.Storage.BUCKET) + "." + sysParamsService.getSysParamsValueByKey(Constant.Storage.ENDPOINT);
         }else{
             this.host = host;
         }
         oss = new OSSClientBuilder().build(sysParamsService.getSysParamsValueByKey(Constant.Storage.ENDPOINT),
                 this.accessId, sysParamsService.getSysParamsValueByKey(Constant.Storage.ACCESS_KEY));
    }
    @Override
    public Map<String, Object> getSignature() throws Exception {
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
        Map<String, Object> respMap = Maps.newLinkedHashMapWithExpectedSize(7);
        respMap.put("accessid",accessId);
        respMap.put("policy", encodedPolicy);
        respMap.put("signature", postSignature);
        respMap.put("dir", dir);
        respMap.put("host", host);
        respMap.put("expire", String.valueOf(expireEndTime / 1000));
        // todo 目前不支持回调
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
