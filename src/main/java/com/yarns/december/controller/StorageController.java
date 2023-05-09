package com.yarns.december.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yarns.december.entity.base.CommonResult;
import com.yarns.december.service.impl.StorageProxyService;
import com.yarns.december.support.annotation.ControllerEndpoint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 对象存储
 * @author LENOVO
 */
@Slf4j
@Validated
@RestController
@RequestMapping("storage")
@DependsOn({"aliyunStorageService","tencentStorageService","qiniuStorageService"})
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class StorageController {

    private final StorageProxyService storageService;

    /**
     * 前端上传（设置回调）获取签名
     * @return Map<String,String>
     */
    @GetMapping("signature")
    public CommonResult<Map<String,Object>> getSignature() throws Exception {
        return CommonResult.ok().setResult(storageService.getSignature());
    }

    /**
     * 服务端上传
     * @param files
     * @return
     * @throws Exception
     */
    @PostMapping("upload")
    @ControllerEndpoint(operation = "上传文件")
    public CommonResult<List<String>> upload(MultipartFile[] files) throws Exception {
        if(files == null || files.length == 0) {
            return CommonResult.fail("文件不能为空");
        }
        return CommonResult.ok().setResult(storageService.upload(files));
    }




}
