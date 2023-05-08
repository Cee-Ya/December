package com.yarns.december.service;

import java.util.Map;

/**
 * @author Yarns
 */
public interface StorageService {

    /**
     * 服务端上传（设置回调）获取签名
     * @return Map<String,String>
     */
    Map<String,String> getSignature() throws Exception;
}
