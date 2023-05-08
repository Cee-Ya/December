package com.yarns.december.service.impl;

import com.google.common.collect.Maps;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import com.yarns.december.service.StorageFactory;
import com.yarns.december.service.StorageService;
import com.yarns.december.service.SysParamsService;
import com.yarns.december.support.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * 腾讯云存储实现类
 * @author Yarns
 */
@Service
@RequiredArgsConstructor
public class TencentStorageServiceImpl implements StorageService, InitializingBean {
    private final SysParamsService sysParamsService;


    @Override
    public void afterPropertiesSet() throws Exception {
         StorageFactory.register(this.getClass().getName(),this);
    }

    /**
     * 服务端上传（设置回调）获取签名
     * @apiNote 参考地址：<a href="https://cloud.tencent.com/document/product/1312/48195">...</a>
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getSignature() throws Exception {
        TreeMap<String, Object> config = new TreeMap<>();
        // 云 api 密钥 SecretId
        config.put("secretId", sysParamsService.getSysParamsValueByKey(Constant.Storage.ACCESS_ID));
        // 云 api 密钥 SecretKey
        config.put("secretKey", sysParamsService.getSysParamsValueByKey(Constant.Storage.ACCESS_KEY));

        // 设置域名,可通过此方式设置内网域名
        config.put("host", sysParamsService.getSysParamsValueByKey(Constant.Storage.HOST));
        // 临时密钥有效时长，单位是秒
        config.put("durationSeconds", 1800);
        // 换成你的 bucket
        config.put("bucket", sysParamsService.getSysParamsValueByKey(Constant.Storage.BUCKET));
        // 换成 bucket 所在地区
        config.put("region", sysParamsService.getSysParamsValueByKey(Constant.Storage.ENDPOINT));
        // 设置允许的路径前缀
        String dir = sysParamsService.getSysParamsValueByKey(Constant.Storage.DIR);
        // 可以通过 allowPrefixes 指定前缀数组, 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
        config.put("allowPrefixes", new String[] {dir+"/*"});

        // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
        String[] allowActions = new String[] {
                // 简单上传
                "name/cos:PutObject",
                "name/cos:PostObject",
                // 分片上传
                "name/cos:InitiateMultipartUpload",
                "name/cos:ListMultipartUploads",
                "name/cos:ListParts",
                "name/cos:UploadPart",
                "name/cos:CompleteMultipartUpload"
        };
        config.put("allowActions", allowActions);

        Response response = CosStsClient.getCredential(config);

        Map<String,Object> respMap = Maps.newLinkedHashMapWithExpectedSize(4);
        respMap.put("tmpSecretId",response.credentials.tmpSecretId);
        respMap.put("tmpSecretKey",response.credentials.tmpSecretKey);
        respMap.put("sessionToken",response.credentials.sessionToken);
        respMap.put("startTime",response.startTime);
        return respMap;
    }
}
