package com.yarns.december.service;

import com.yarns.december.entity.system.SysParams;

import com.yarns.december.entity.base.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统参数表 Service接口
 *
 * @author yarns
 * @date 2023-05-08 20:28:36
 */
public interface SysParamsService extends IService<SysParams> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sysParams sysParams
     * @return IPage<SysParams>
     */
    IPage<SysParams> findSysParamss(QueryRequest request, SysParams sysParams);

    /**
     * 新增系统参数表
     *
     * @param sysParams sysParams
     */
    void createSysParams(SysParams sysParams);

    /**
     * 修改系统参数表
     *
     * @param sysParams sysParams
     */
    void updateSysParams(SysParams sysParams);

    /**
     * 删除系统参数表
     *
     * @param sysParamsIds 系统参数表 id数组
     */
    void deleteSysParamss(String[] sysParamsIds);

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    String getSysParamsValueByKey(String key);
}
