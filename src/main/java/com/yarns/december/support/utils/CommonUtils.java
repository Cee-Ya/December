package com.yarns.december.support.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.yarns.december.entity.base.Page;
import com.yarns.december.entity.base.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @Author Yarns
 * @Date 17:46
 * @Version 1.0
 **/
public class CommonUtils {


    /**
     * 通用返回json分页数据
     * */
    public static Map<String,Object> viewReturnPageData(Page page, List pageLst) {
        // 返回数据必须包含这个格式
        Map<String,Object> json = new HashMap<>(2);
        json.put("total", page.getTotalResult());
        json.put("rows", pageLst);
        return json;
    }

    /**
     * 获取PageData
     *
     * @return
     */
    public static PageData getPageData() {
        return new PageData(getHttpServletRequest());
    }

    public static Page getDataPage(){
        Page page = getPage();
        PageData pd = getPageData();
        page.setPd(pd);
        return page;
    }

    /**
     * 得到分页列表的信息
     */
    public static Page getPage() {
        HttpServletRequest request = getHttpServletRequest();
        //管理分页编码
        String currentResult = request.getParameter("offset");
        //业务分页编码
        String currentPage = request.getParameter("currentPage");
        //显示数量
        String showCount = request.getParameter("showCount");
        Page page = new Page();
        //默认10条
        if(StringUtils.isNotBlank(showCount)){
            page.setShowCount(Integer.parseInt(showCount));
        }
        //两种分页标签 第一种是offset 适用于管理功能的分页 currentPage 适用于业务功能的分页
        if(StringUtils.isEmpty(currentPage)){
            //管理功能分页
            if(StringUtils.isEmpty(currentResult)){
                // 分页开始位置
                page.setCurrentResult(0);
            }else {
                if(StringUtils.isEmpty(currentResult)){
                    // 分页开始位置
                    page.setCurrentResult(0);
                }else {
                    // 分页开始位置
                    page.setCurrentResult(Integer.parseInt(currentResult));
                }
            }
        }else{
            //业务功能分页
            //当前页数
            int startLimt = (Integer.parseInt(currentPage)-1) * page.getShowCount();
            // 分页开始位置
            page.setCurrentResult(startLimt);
        }
        return page;
    }


    /**
     * 封装前端分页表格所需数据
     *
     * @param pageInfo pageInfo
     * @return Map<String ,   Object>
     */
    public static Map<String, Object> getDataTable(IPage<?> pageInfo) {
        Map<String, Object> data = new HashMap<>();
        data.put("rows", pageInfo.getRecords());
        data.put("total", pageInfo.getTotal());
        return data;
    }

    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
        if (arr.length == 0) {
            return value;
        }
        StringBuilder result = new StringBuilder();
        IntStream.range(0, arr.length).forEach(i -> {
            if (i != arr.length - 1) {
                result.append(arr[i]).append(StringPool.UNDERSCORE);
            } else {
                result.append(arr[i]);
            }
        });
        return StringUtils.lowerCase(result.toString());
    }

    /**
     * 下划线转驼峰
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String underscoreToCamel(String value) {
        StringBuilder result = new StringBuilder();
        String[] arr = value.split(StringPool.UNDERSCORE);
        for (String s : arr) {
            result.append((String.valueOf(s.charAt(0))).toUpperCase()).append(s.substring(1));
        }
        return result.toString();
    }



    /**
     * 获取HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
    /**
     * 获取HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 获取请求IP
     *
     * @return String IP
     */
    public static String getHttpServletRequestIpAddress() {
        HttpServletRequest request = getHttpServletRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取请求IP
     *
     * @param request ServerHttpRequest
     * @return String IP
     */
    public static String getServerHttpRequestIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.contains(StringPool.COMMA)) {
                ip = ip.split(StringPool.COMMA)[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 判断是否包含中文
     *
     * @param value 内容
     * @return 结果
     */
    public static boolean containChinese(String value) {
        Pattern p = Pattern.compile(ValidatorUtil.CONTAIN_CHINESE_STRING);
        Matcher m = p.matcher(value);
        return m.find();
    }

}
