package com.yarns.december.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Yarns
 */
public interface StorageService {

    /**
     * 服务端上传（设置回调）获取签名
     * @return Map<String,String>
     */
    Map<String,Object> getSignature() throws Exception;

    /**
     * 服务端上传
     * @param is 文件
     * @param path 路径+文件名
     * @return
     */
    String upload(InputStream is,String path) throws Exception;
}
