<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.${mapperPackage}.${className}Mapper">

    <select id="find${className}DetailPage" parameterType="${className?uncap_first}" resultType="${className?uncap_first}">
        SELECT
        <#if columns??>
            <#list columns as column>
                ${column.name} as ${column.field?uncap_first}<#if column_has_next>,</#if>
            </#list>
        </#if>
        FROM ${tableName} WHERE 1=1  order by id desc
    </select>
</mapper>
