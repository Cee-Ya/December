package com.yarns.december.service;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 对象存储工厂类
 * @author Yarns
 */
public class StorageFactory {
    private static Map<String,StorageService> storageServiceMap = Maps.newHashMapWithExpectedSize(3);

    /**
     * 注册到工厂
     * @param type
     * @param storageService
     */
    public static void register(String type,StorageService storageService){
        storageServiceMap.put(type,storageService);
    }

    /**
     * 获取对象存储服务实例
     * @param type
     * @return
     */
    public static StorageService getStorageService(String type){
        return storageServiceMap.get(type);
    }
}
