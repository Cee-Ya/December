package com.yarns.december.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.yarns.december.entity.base.CommonResult;
import com.yarns.december.service.StorageFactory;
import com.yarns.december.service.StorageService;
import com.yarns.december.service.SysParamsService;
import com.yarns.december.support.constant.Constant;
import com.yarns.december.support.exception.BaseException;
import com.yarns.december.support.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Yarns
 */
@Slf4j
@SuppressWarnings({"AlibabaServiceOrDaoClassShouldEndWithImpl"})
@Service
@RequiredArgsConstructor
public class StorageProxyService implements StorageService, InitializingBean {
    private final SysParamsService paramsService;
    private StorageService storageService;

    @Override
    public void afterPropertiesSet() throws Exception {
        String type = paramsService.getSystemParamsValueByKey(Constant.Storage.TYPE);
        storageService = StorageFactory.getStorageService(type);
        log.info("初始化【{}】存储服务成功",type);
    }

    @Override
    public Map<String, Object> getSignature() throws Exception {
        return storageService.getSignature();
    }

    @Override
    public String upload(InputStream is, String path) throws Exception {
        return storageService.upload(is, path);
    }

    public List<String> upload(MultipartFile[] files) throws Exception {
        List<String> paths = Lists.newArrayListWithCapacity(files.length);
        for (MultipartFile file : files) {
            String fileName = FileUtil.getDateFileName(file.getOriginalFilename());
            // 文件夹名称
            String folderName = FileUtil.getFolderByType(fileName, "");
            // 开始上传
            paths.add(upload(file.getInputStream(),folderName+fileName));
        }
        return paths;
    }
}
