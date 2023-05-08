package com.yarns.december.controller;

import com.yarns.december.entity.base.CommonResult;
import com.yarns.december.service.impl.StorageProxyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 对象存储
 * @author LENOVO
 */
@Slf4j
@Validated
@RestController
@RequestMapping("storage")
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class StorageController {
    private final StorageProxyService storageService;

    /**
     * 服务端上传（设置回调）获取签名
     * @return Map<String,String>
     */
    @GetMapping("signature")
    public CommonResult<Map<String,Object>> getSignature() throws Exception {
        return CommonResult.ok().setResult(storageService.getSignature());
    }


}
