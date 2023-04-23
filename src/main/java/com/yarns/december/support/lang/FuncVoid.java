package com.yarns.december.support.lang;

/**
 * 有返回的函数
 * @param <T>
 */
public interface FuncVoid<T> {

    /**
     * 执行方法
     *
     * @return T
     */
    T invoke();

}