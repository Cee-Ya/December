package com.yarns.december.support.filter.xss;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Xss攻击拦截器
 * @author color
 */
public class XssFilter implements Filter {

	/**
	 * 是否过滤富文本内容
	 */
	private static boolean IS_INCLUDE_RICH_TEXT = false;

	private final List<String> excludes = new ArrayList<>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
		if (StringUtils.isNotBlank(isIncludeRichText)) {
			IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isIncludeRichText);
		}
		String temp = filterConfig.getInitParameter("excludes");
		if (temp != null) {
			String[] url = temp.split(",");
			Collections.addAll(excludes, url);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (handleExcludeURL(req, resp)) {
			chain.doFilter(request, response);
			return;
		}
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request,
				IS_INCLUDE_RICH_TEXT);
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {

	}

	private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
		if (excludes.isEmpty()) {
			return false;
		}
		String url = request.getServletPath();
		for (String pattern : excludes) {
			Pattern p = Pattern.compile(StringPool.HAT + pattern);
			Matcher m = p.matcher(url);
			if (m.find()) {
                return true;
            }
		}
		return false;
	}

}
