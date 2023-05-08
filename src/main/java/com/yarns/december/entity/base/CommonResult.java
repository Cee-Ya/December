package com.yarns.december.entity.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 如果需要把返回值写入结果 就使用该结构体返回
 * @author Yarns
 * @date 2022/6/3
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult<T> extends BaseResult implements Serializable {
    private static final long serialVersionUID = -7268040542410707954L;
    public CommonResult() {

    }
    public static CommonResult ok() {
        return baseCreate(200,"操作成功",true);
    }

    public static CommonResult fail() {
        return baseCreate(500,"操作失败",false);
    }

    public static CommonResult fail(String msg) {
        return baseCreate(500,msg,false);
    }

    private static <T> CommonResult<T> baseCreate(Integer code, String msg, boolean success) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setSuccess(success);
        result.setMessage(msg);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    public CommonResult<T> setResult(T data) {
        this.setData(data);
        return this;
    }

    public T getData() {
        return (T) super.getData();
    }
}
