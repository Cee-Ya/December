package com.yarns.december.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.entity.base.ResponseBo;
import com.yarns.december.entity.generator.Column;
import com.yarns.december.entity.generator.GeneratorConfig;
import com.yarns.december.service.GeneratorConfigService;
import com.yarns.december.service.GeneratorService;
import com.yarns.december.support.constant.GeneratorConstant;
import com.yarns.december.support.exception.BaseException;
import com.yarns.december.support.helper.GeneratorHelper;
import com.yarns.december.support.utils.CommonUtils;
import com.yarns.december.support.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 *
 * 代码生成配置类
 * @author Yarns
 * @date 2019年10月6日20:45:34
 */
@Slf4j
@RestController
@RequestMapping("generator")
@RequiredArgsConstructor
public class GeneratorController implements InitializingBean {

    @Value("${spring.datasource.dynamic.datasource.base.url}")
    private String databaseName;
    private String databaseType;

    private static final String SUFFIX = "_code.zip";

    private final GeneratorService generatorService;
    private final GeneratorConfigService generatorConfigService;
    private final GeneratorHelper generatorHelper;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化数据库类型 jdbc:mysql://192.168.13.189:3306/december?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull
        databaseType = databaseName.split(StringPool.COLON)[1];
        // 初始化数据库名称
        databaseName = RegExUtils.replacePattern(databaseName, ".*/|\\?.*", "");
        log.warn("代码生成使用的数据库类型为【{}】- 数据库名称为【{}】",databaseType, databaseName);
    }

    /**
     * 获取表结构
     * @param tableName 表名称
     * @param request
     * @return
     */
    @GetMapping("tables")
    public ResponseBo tablesInfo(String tableName, QueryRequest request) {
        Map<String, Object> dataTable = CommonUtils.getDataTable(generatorService.getTables(tableName, request, GeneratorConstant.DATABASE_TYPE, databaseName));
        return ResponseBo.result(dataTable);
    }

    /**
     * 代码生成
     * @param name 需要生成的表 例如：t_sys_user
     * @param remark 表的注释 例如：用户
     * @param response
     * @throws Exception
     */
    @GetMapping("zip")
    public void generate(@NotBlank(message = "{required}") String name, String remark, HttpServletResponse response) throws Exception {
        GeneratorConfig generatorConfig = generatorConfigService.findGeneratorConfig();
        if (generatorConfig == null) {
            throw new BaseException("代码生成配置为空");
        }

        String className = name;
        if (GeneratorConfig.TRIM_YES.equals(generatorConfig.getIsTrim())) {
            className = RegExUtils.replaceFirst(name, generatorConfig.getTrimValue(), StringUtils.EMPTY);
        }
        generatorConfig.setTableName(name);
        generatorConfig.setClassName(CommonUtils.underscoreToCamel(className));
        generatorConfig.setTableComment(remark);
        // 生成代码到临时目录
        List<Column> columns = generatorService.getColumns(GeneratorConstant.DATABASE_TYPE, databaseName, name);
        generatorHelper.generateEntityFile(columns, generatorConfig);
        generatorHelper.generateMapperFile(columns, generatorConfig);
        generatorHelper.generateMapperXmlFile(columns, generatorConfig);
        generatorHelper.generateServiceFile(columns, generatorConfig);
        generatorHelper.generateServiceImplFile(columns, generatorConfig);
        generatorHelper.generateControllerFile(columns, generatorConfig);
        // 打包
        String zipFile = System.currentTimeMillis() + SUFFIX;
        FileUtil.compress(GeneratorConstant.TEMP_PATH + "src", zipFile);
        // 下载
        FileUtil.download(zipFile, name + SUFFIX, true, response);
        // 删除临时目录
        FileUtil.delete(GeneratorConstant.TEMP_PATH);
    }
}
