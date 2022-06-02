package com.yarns.december.entity.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@Data
public class BaseResult<T> implements Serializable {
    /**
     * 是否操作成功
     */
    private boolean success = false;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 具体数据
     */
    private T data;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 时间戳
     */
    private String timestamp;
}
