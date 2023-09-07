package com.yarns.december.support.utils;


import com.yarns.december.entity.base.RouteTree;
import com.yarns.december.entity.base.Tree;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于构建前端路由
 * @author 62537
 */
@SuppressWarnings("Duplicates")
public class TreeUtil {

    private final static String TOP_NODE_ID = "0";

    /**
     * 用于构建菜单或组织树
     *
     * @param nodes nodes
     * @return <T> List<? extends Tree>
     */
    public static <T> List<? extends Tree<?>> build(List<? extends Tree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<>();
        nodes.forEach(node -> {
            String pid = node.getParentId();
            if (pid == null || TOP_NODE_ID.equals(pid)) {
                topNodes.add(node);
                return;
            }
            for (Tree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null) {
                        n.initChildren();
                    }
                    n.getChildren().add(node);
                    node.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
            }
            if (topNodes.isEmpty()) {
                topNodes.add(node);
            }
        });
        return topNodes;
    }

    /**
     * 用于构建菜单或组织树
     *
     * @param nodes nodes
     * @return <T> List<? extends Tree>
     */
    public static <T> List<? extends Tree<?>> build(List<? extends Tree<T>> nodes,String s) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<>();
        nodes.forEach(node -> {
            String pid = node.getParentId();
            if (pid == null || "1".equals(pid)) {
                topNodes.add(node);
                return;
            }
            for (Tree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null) {
                        n.initChildren();
                    }
                    n.getChildren().add(node);
                    node.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
            }
//            if (topNodes.isEmpty()) {
//                topNodes.add(node);
//            }
        });
        return topNodes;
    }
    /**
     * 构造前端路由
     *
     * @param routes routes
     * @param <T>    T
     * @return ArrayList<VueRouter < T>>
     */
    public static <T> List<RouteTree> buildVueRouter(List<RouteTree> routes) {
        if (routes == null) {
            return null;
        }
        List<RouteTree> topRoutes = new ArrayList<>();
        RouteTree router = new RouteTree();
        routes.forEach(route -> {
            String parentId = route.getParentId();
            if (parentId == null || TOP_NODE_ID.equals(parentId)) {
                topRoutes.add(route);
                return;
            }
            for (RouteTree parent : routes) {
                String id = parent.getId();
                if (id != null && id.equals(parentId)) {
                    if (parent.getChildren() == null) {
                        parent.initChildren();
                    }
                    parent.getChildren().add(route);
                    parent.setHasChildren(true);
                    route.setHasParent(true);
                    parent.setHasParent(true);
                    return;
                }
            }
        });
//        RouteTree router404 = new VueRouter<>();
//        router404.setName("404");
//        router404.setComponent("error-page/404");
//        router404.setPath("*");
//        topRoutes.add(router404);
        return topRoutes;
    }

    /**
     * 处理id
     * @param nodes
     * @param <T>
     * @return
     */
    public static <T> List<? extends Tree<?>> removeIds(List<? extends Tree<T>> nodes) {
        nodes.forEach(r ->{
            r.setId(null);
            r.setParentId(null);
            r.setHasChildren(null);
            r.setHasParent(null);
            if(r.getChildren() != null && !r.getChildren().isEmpty()){
                removeIds(r.getChildren());
            }
        });
        return nodes;
    }
}
