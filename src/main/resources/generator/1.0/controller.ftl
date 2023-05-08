package ${basePackage}.${controllerPackage};

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import ${basePackage}.entity.base.ResponseBo;
import ${basePackage}.entity.base.QueryRequest;
import ${basePackage}.support.utils.CommonUtils;
import ${basePackage}.support.annotation.ControllerEndpoint;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${servicePackage}.${className}Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * ${tableComment} Controller
 *
 * @author ${author}
 * @date ${date}
 */
@Slf4j
@Validated
@RestController
@RequestMapping("${className?uncap_first}")
@RequiredArgsConstructor
public class ${className}Controller {
    private final ${className}Service ${className?uncap_first}Service;

    /**
     * ${tableComment}列表
     * @param request 分页部分
     * @param ${className?uncap_first} 查询对象
     * @return
     */
    @GetMapping("list")
    public ResponseBo ${className?uncap_first}List(QueryRequest request, ${className} ${className?uncap_first}) {
        Map<String, Object> dataTable = CommonUtils.getDataTable(this.${className?uncap_first}Service.find${className}s(request, ${className?uncap_first}));
        return ResponseBo.result(dataTable);
    }

    /**
     * 新增${tableComment}
     * @param ${className?uncap_first} ${tableComment}对象
     * @return
     */
    @PostMapping
    @ControllerEndpoint(operation = "新增${tableComment}")
    public ResponseBo add${className}(@Valid @RequestBody ${className} ${className?uncap_first}) {
        this.${className?uncap_first}Service.create${className}(${className?uncap_first});
        return ResponseBo.ok();
    }

    /**
     * 修改${tableComment}
     * @param ${className?uncap_first} ${tableComment}对象
     * @return
     */
    @PutMapping
    @ControllerEndpoint(operation = "修改${tableComment}")
    public ResponseBo update${className}(@Valid @RequestBody ${className} ${className?uncap_first}) {
        this.${className?uncap_first}Service.update${className}(${className?uncap_first});
        return ResponseBo.ok();
    }

    /**
     * 删除${tableComment}
     * @param ${className?uncap_first}Ids 需要删除的id
     * @return
     */
    @DeleteMapping
    @ControllerEndpoint(operation = "删除${tableComment}")
    public ResponseBo delete${className}s(@NotBlank(message = "{required}") String ${className?uncap_first}Ids) {
        String[] ids = ${className?uncap_first}Ids.split(StringPool.COMMA);
        this.${className?uncap_first}Service.delete${className}s(ids);
        return ResponseBo.ok();
    }
}
