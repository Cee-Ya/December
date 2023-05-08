package com.yarns.december.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.system.SysParams;
import com.yarns.december.mapper.SysParamsMapper;
import com.yarns.december.service.SysParamsService;
import com.yarns.december.support.constant.Constant;
import com.yarns.december.support.helper.RedisHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 系统参数表 Service实现
 *
 * @author yarns
 * @date 2023-05-08 20:28:36
 */
@Slf4j
@Service("sysParamsService")
@RequiredArgsConstructor
public class SysParamsServiceImpl extends ServiceImpl<SysParamsMapper, SysParams> implements SysParamsService, InitializingBean {
    private final static String REDIS_KEY = "SysParams::";
    private final RedisHelper redisHelper;
    @Override
    public void afterPropertiesSet() throws Exception {
        // 这里只清理key缓存 不清理id缓存
        List<String> delCacheKeys = this.baseMapper.findDelCacheKeys();
        delCacheKeys.forEach(key -> redisHelper.del(REDIS_KEY+"key::"+key));
    }

    @Override
    public IPage<SysParams> findSysParamss(QueryRequest request, SysParams sysParams) {
        Page<SysParams> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findSysParamsDetailPage(page,sysParams);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void createSysParams(SysParams sysParams) {
        this.save(sysParams);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateSysParams(SysParams sysParams) {
        this.updateById(sysParams);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteSysParamss(String[] sysParamsIds) {
        this.removeByIds(Arrays.asList(sysParamsIds));
    }

    @Override
    public SysParams getParkParamsValueByKey(String key) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<SysParams>().eq(SysParams::getName, key));
    }

    @Override
    public String getSystemParamsValueByKey(String key) {
        String cacheKey = REDIS_KEY+"key::"+key;
        if(redisHelper.hasKey(cacheKey)){
            return (String) redisHelper.get(cacheKey);
        }
        SysParams sysParams = this.getParkParamsValueByKey(key);
        //只返回系统参数
        if(sysParams != null && sysParams.getParamValue() != null && sysParams.getSystemParamFlag() == 1){
            redisHelper.set(cacheKey,sysParams.getParamValue(), Constant.Redis.EXPIRE_TIME);
            return sysParams.getParamValue();
        }
        return null;
    }
}
