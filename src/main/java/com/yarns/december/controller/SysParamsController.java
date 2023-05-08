package com.yarns.december.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.yarns.december.entity.base.PageRes;
import com.yarns.december.entity.base.CommonResult;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.support.annotation.ControllerEndpoint;
import com.yarns.december.entity.system.SysParams;
import com.yarns.december.service.SysParamsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 系统参数表 Controller
 *
 * @author yarns
 * @date 2023-05-08 20:28:36
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sysParams")
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
public class SysParamsController {
    private final SysParamsService sysParamsService;

    /**
     * 系统参数表列表
     * @param request 分页部分
     * @param sysParams 查询对象
     * @return
     */
    @GetMapping("list")
    public CommonResult<PageRes<SysParams>> sysParamsList(QueryRequest request, SysParams sysParams) {
        return CommonResult.ok().setResult(PageRes.init(this.sysParamsService.findSysParamss(request, sysParams)));
    }

    /**
     * 新增系统参数表
     * @param sysParams 系统参数表对象
     * @return
     */
    @PostMapping
    @ControllerEndpoint(operation = "新增系统参数表")
    public CommonResult addSysParams(@Valid @RequestBody SysParams sysParams) {
        this.sysParamsService.createSysParams(sysParams);
        return CommonResult.ok();
    }

    /**
     * 修改系统参数表
     * @param sysParams 系统参数表对象
     * @return
     */
    @PutMapping
    @ControllerEndpoint(operation = "修改系统参数表")
    public CommonResult updateSysParams(@Valid @RequestBody SysParams sysParams) {
        this.sysParamsService.updateSysParams(sysParams);
        return CommonResult.ok();
    }

    /**
     * 删除系统参数表
     * @param sysParamsIds 需要删除的id
     * @return
     */
    @DeleteMapping
    @ControllerEndpoint(operation = "删除系统参数表")
    public CommonResult deleteSysParamss(@NotBlank(message = "{required}") String sysParamsIds) {
        String[] ids = sysParamsIds.split(StringPool.COMMA);
        this.sysParamsService.deleteSysParamss(ids);
        return CommonResult.ok();
    }
}
