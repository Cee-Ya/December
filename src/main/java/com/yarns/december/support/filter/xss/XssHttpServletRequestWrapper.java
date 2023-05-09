package com.yarns.december.support.filter.xss;


import com.yarns.december.support.utils.JsoupUtil;
import com.yarns.december.support.utils.XssUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Jsoup过滤http请求，防止Xss攻击
 * @author color
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private HttpServletRequest orgRequest = null;

	private boolean isIncludeRichText = false;

	XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
		super(request);
		orgRequest = request;
		this.isIncludeRichText = isIncludeRichText;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		//判断是否为json
		String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
		if(!StringUtils.startsWithIgnoreCase(header, MediaType.APPLICATION_JSON_VALUE)){
			return super.getInputStream();
		}

		//为空，直接返回
		String json = IOUtils.toString(super.getInputStream(), StandardCharsets.UTF_8);
		if (StringUtils.isBlank(json)) {
			return super.getInputStream();
		}

		//xss过滤
		json = JsoupUtil.cleanXssAndSql(json);
		final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return true;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}

			@Override
			public int read() {
				return bis.read();
			}
		};
	}

	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss过滤
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter(String name) {
		boolean flag = ("content".equals(name) || name.endsWith("WithHtml")) && !isIncludeRichText;
		if (flag) {
			return super.getParameter(name);
		}
		name = JsoupUtil.cleanXssAndSql(name);
		String value = super.getParameter(name);
		if (StringUtils.isNotBlank(value)) {
			value = JsoupUtil.clean(value);
		}
		return value;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] arr = super.getParameterValues(name);
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				arr[i] = JsoupUtil.clean(arr[i]);
			}
		}
		return arr;
	}

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做xss过滤
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
	 * getHeaderNames 也可能需要覆盖
	 */
	@Override
	public String getHeader(String name) {
		name = JsoupUtil.clean(name);
		String value = super.getHeader(name);
		if (StringUtils.isNotBlank(value)) {
			value = JsoupUtil.clean(value);
		}
		return value;
	}

	@Override
	public Map<String,String[]> getParameterMap() {
		Map<String,String[]> primary = super.getParameterMap();
		Map<String,String[]> result = new HashMap<>();
		for (Map.Entry<String,String[]> entry : primary.entrySet()) {
			result.put(entry.getKey(),filterEntryString(entry.getValue()));
		}
		return result;
	}

	@Override
	public Cookie[] getCookies() {
		Cookie[] cookies = super.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setValue(filterParamString(cookie.getValue()));
			}
		}
		return cookies;
	}

	private String[] filterEntryString(String[] value) {
		for (int i = 0; i < value.length; i++) {
			value[i] = filterParamString(value[i]);
		}
		return value;
	}

	private String filterParamString(String value) {
		if (null == value) {
			return null;
		}
		// 过滤XSS 和 SQL 注入
		return XssUtil.stripSqlXss(value);
	}

	/**
	 * 获取原始的request
	 */
	private HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	/**
	 * 获取原始的request的静态方法
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
		if (req instanceof XssHttpServletRequestWrapper) {
			return ((XssHttpServletRequestWrapper) req).getOrgRequest();
		}
		return req;
	}

}
