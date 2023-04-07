package com.yarns.december.entity.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author color
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<T> {

    private String id;

    /**
     * 名称
     */
    private String label;

    /**
     * 子集
     */
    private List<Tree<T>> children;

    /**
     * 父级
     */
    private String parentId;

    /**
     * 是否有父级
     */
    private Boolean hasParent;

    /**
     * 是否有子级
     */
    private Boolean hasChildren;

    public void initChildren(){
        this.children = new ArrayList<>();
    }

}
