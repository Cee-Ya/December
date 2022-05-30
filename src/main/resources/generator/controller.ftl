package ${basePackage}.${controllerPackage};

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.yarns.december.entity.base.ResponseBo;
import com.yarns.december.entity.base.QueryRequest;
import com.yarns.december.support.annotation.ControllerEndpoint;
import com.yarns.december.support.utils.CommonUtils;
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

    @Autowired
    private ${className}Service ${className?uncap_first}Service;

    @GetMapping("list")
    public ResponseBo ${className?uncap_first}List(QueryRequest request, ${className} ${className?uncap_first}) {
        Map<String, Object> dataTable = CommonUtils.getDataTable(this.${className?uncap_first}Service.find${className}s(request, ${className?uncap_first}));
        return ResponseBo.result(dataTable);
    }

    @PostMapping
    @ControllerEndpoint(operation = "新增${tableComment}", exceptionMessage = "新增${tableComment}失败")
    public ResponseBo add${className}(@Valid @RequestBody ${className} ${className?uncap_first}) {
        this.${className?uncap_first}Service.create${className}(${className?uncap_first});
        return ResponseBo.ok();
    }

    @PutMapping
    @ControllerEndpoint(operation = "修改${tableComment}", exceptionMessage = "修改${tableComment}失败")
    public ResponseBo update${className}(@Valid @RequestBody ${className} ${className?uncap_first}) {
        this.${className?uncap_first}Service.update${className}(${className?uncap_first});
        return ResponseBo.ok();
    }


    @DeleteMapping
    @ControllerEndpoint(operation = "删除${tableComment}", exceptionMessage = "删除${tableComment}失败")
    public ResponseBo delete${className}s(@NotBlank(message = "{required}") String ${className?uncap_first}Ids) {
        String[] ids = ${className?uncap_first}Ids.split(StringPool.COMMA);
        this.${className?uncap_first}Service.delete${className}s(ids);
        return ResponseBo.ok();
    }
}
