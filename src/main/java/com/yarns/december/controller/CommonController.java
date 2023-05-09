package com.yarns.december.controller;

import com.google.common.collect.Lists;
import com.yarns.december.entity.base.CommonResult;
import com.yarns.december.service.impl.ValidateCodeService;
import com.yarns.december.support.exception.BaseException;
import com.yarns.december.support.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 公共接口
 * @author Yarns
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("common")
@SuppressWarnings({"unchecked", "DuplicatedCode"})
public class CommonController {
    @Value("${december.upload.path}")
    private String path;
    @Value("${december.upload.max-size}")
    private String maxSize;
    @Value("${december.upload.url}")
    private String url;

    private final ValidateCodeService validateCodeService;

    /**
     * 获取图形验证码
     * @param key 验证码key
     * @throws IOException
     * @throws BaseException
     */
    @GetMapping("captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException, BaseException {
        validateCodeService.create(request,response);
    }

    /**
     * 上传文件(单个)
     * @apiNote 上传文件(单个) 记住请求头要加上 Content-Type:multipart/form-data
     * @param file 文件对象
     * @return
     */
    @PostMapping("upload")
    public CommonResult<String> upload(MultipartFile file){
        // 校验文件
        if (file.isEmpty()) {
            return CommonResult.fail("上传文件不能为空");
        }
        //文件大小是否超过限制
        if (FileUtil.checkFileSize(file, maxSize)) {
            return CommonResult.fail("上传文件大小不能超过" + maxSize);
        }
        String fileName = FileUtil.getDateFileName(file.getOriginalFilename());
        // 判断文件夹是否存在
        String folderPath = FileUtil.getFolderByType(fileName,path);
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // 保存文件
        File dest = new File(folderPath + fileName);
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.fail("上传失败");
        }
        return CommonResult.ok().setResult(url+FileUtil.getFolderByType(fileName,"")+fileName);
    }

    /**
     * 上传文件(多个)
     * @apiNote 上传文件(多个) 记住请求头要加上 Content-Type:multipart/form-data
     * @param files 文件对象
     * @return
     */
    @PostMapping("upload/batch")
    public CommonResult<List<String>> uploadBatch(MultipartFile[] files){
        // 校验文件
        if (files.length == 0) {
            return CommonResult.fail("上传文件不能为空");
        }
        //文件大小是否超过限制
        for (MultipartFile file : files) {
            if (FileUtil.checkFileSize(file, maxSize)) {
                return CommonResult.fail("上传文件大小不能超过" + maxSize);
            }
        }
        List<String> resultPath = Lists.newArrayListWithCapacity(files.length);
        for (MultipartFile file : files) {
            String fileName = FileUtil.getDateFileName(file.getOriginalFilename());
            // 判断文件夹是否存在
            String folderPath = FileUtil.getFolderByType(fileName,path);
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // 保存文件
            File dest = new File(folderPath + fileName);
            try {
                file.transferTo(dest);
            } catch (Exception e) {
                e.printStackTrace();
                return CommonResult.fail("上传失败");
            }
            resultPath.add(url+FileUtil.getFolderByType(fileName,"")+fileName);
        }
        return CommonResult.ok().setResult(resultPath);
    }

}
