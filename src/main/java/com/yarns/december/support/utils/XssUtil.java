package com.yarns.december.support.utils;

import java.util.regex.Pattern;

/**
 * @Author tomsun28
 * @Description Web防火墙工具类
 * @Date 19:51 2018/4/15
 */
public class XssUtil {
    private static final Pattern BETWEEN_SCRIPT = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern LAST_SCRIPT = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern HEADER_SCRIPT = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern AVOID_EVAL = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern AVOID_EXPRESSION = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern AVOID_JAVASCRIPT = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    private static final Pattern AVOID_VBSCRIPT = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    private static final Pattern AVOID_ONLOAD = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE | Pattern.DOTALL);
    /**
     * @Description 过滤XSS脚本内容
     * @Param [value]
     * @Return java.lang.String
     */
    public static String stripXSS(String value) {
        String rlt = null;

        if (null != value) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            rlt = value.replaceAll("", "");

            // Avoid anything between script tags
            rlt = BETWEEN_SCRIPT.matcher(rlt).replaceAll("");

            // Avoid anything in a src='...' type of expression
			/*scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");*/

            // Remove any lonesome </script> tag
            rlt = LAST_SCRIPT.matcher(rlt).replaceAll("");

            // Remove any lonesome <script ...> tag
            rlt = HEADER_SCRIPT.matcher(rlt).replaceAll("");

            // Avoid eval(...) expressions
            rlt = AVOID_EVAL.matcher(rlt).replaceAll("");

            // Avoid expression(...) expressions
            rlt = AVOID_EXPRESSION.matcher(rlt).replaceAll("");

            // Avoid javascript:... expressions
            rlt = AVOID_JAVASCRIPT.matcher(rlt).replaceAll("");

            // Avoid vbscript:... expressions
            rlt = AVOID_VBSCRIPT.matcher(rlt).replaceAll("");

            // Avoid onload= expressions
            rlt = AVOID_ONLOAD.matcher(rlt).replaceAll("");
        }

        return rlt;
    }

    /**
     * * @Description 过滤SQL注入内容
     * @Param [value]
     * @Return java.lang.String
     */
    public static String stripSqlInjection(String value) {
        //value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "");
        return (null == value) ? null : value.replaceAll("('.+--)|(--)|(%7C)", "");
    }

    /**
     * @Description 过滤SQL 和 XSS注入内容
     * @Param [value]
     * @Return java.lang.String
     */
    public static String stripSqlXss(String value) {
        return stripXSS(stripSqlInjection(value));
    }

}
