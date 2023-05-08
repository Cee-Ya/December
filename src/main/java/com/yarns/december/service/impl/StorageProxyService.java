package com.yarns.december.service.impl;

import com.yarns.december.service.StorageFactory;
import com.yarns.december.service.StorageService;
import com.yarns.december.service.SysParamsService;
import com.yarns.december.support.constant.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Yarns
 */
@Slf4j
@SuppressWarnings("AlibabaServiceOrDaoClassShouldEndWithImpl")
@Service
@RequiredArgsConstructor
public class StorageProxyService implements StorageService, InitializingBean {
    private final SysParamsService paramsService;

    private StorageService storageService;

    @Override
    public void afterPropertiesSet() throws Exception {
        String type = paramsService.getSysParamsValueByKey(Constant.Storage.TYPE);
        storageService = StorageFactory.getStorageService(type);
        log.info("初始化【{}】存储服务成功",type);
    }

    @Override
    public Map<String, String> getSignature() throws Exception {
        return storageService.getSignature();
    }
}
