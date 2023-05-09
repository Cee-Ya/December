package com.yarns.december.support.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述: 过滤和转义html标签和属性中的敏感字符
 * @author color
 */
public class JsoupUtil {

    /**
     * 标签白名单
     * relaxed() 允许的标签:
     * a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4,
     * h5, h6, i, img, li, ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul。
     * 结果不包含标签rel=nofollow ，如果需要可以手动添加。
     */
    static Whitelist WHITELIST = Whitelist.relaxed();

    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    static Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    /**
     * 设置自定义的标签和属性
     */
    static {
        /**
         * addTags() 设置白名单标签
         * addAttributes()  设置标签需要保留的属性 ,[:all]表示所有
         * preserveRelativeLinks()  是否保留元素的URL属性中的相对链接，或将它们转换为绝对链接,默认为false. 为false时将会把baseUri和元素的URL属性拼接起来
         */
        WHITELIST.addAttributes(":all", "style");
        WHITELIST.preserveRelativeLinks(true);
    }

    public static String clean(String s) {
        /**
         * baseUri ,非空
         * 如果baseUri为空字符串或者不符合Http://xx类似的协议开头,属性中的URL链接将会被删除,如<a href='xxx'/>会变成<a/>
         * 如果WHITELIST.preserveRelativeLinks(false), 会将baseUri和属性中的URL链接进行拼接
         */
        return Jsoup.clean(s, "http://base.uri", WHITELIST, OUTPUT_SETTINGS);
    }

    /**
     * 处理Json类型的Html标签,进行xss过滤
     *
     * @param s
     * @return
     */
    public static String cleanJson(String s) {
        //先处理双引号的问题
        s = jsonStringConvert(s);
        return clean(s);
    }

    /**
     * 将json字符串本身的双引号以外的双引号变成单引号
     *
     * @param s
     * @return
     */
    public static String jsonStringConvert(String s) {
        char[] temp = s.toCharArray();
        int n = temp.length;
        for (int i = 0; i < n; i++) {
            if (temp[i] == ':' && temp[i + 1] == '"') {
                for (int j = i + 2; j < n; j++) {
                    if (temp[j] == '"') {
                        //如果该字符为双引号,下个字符不是逗号或大括号,替换
                        if (temp[j + 1] != ',' && temp[j + 1] != '}') {
                            //将json字符串本身的双引号以外的双引号变成单引号
                            temp[j] = '\'';
                        } else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
                            break;
                        }
                    }
                }
            }
        }
        return new String(temp);
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

    public static String cleanXssAndSql(String value){
        return (null == value) ? null : clean(stripSqlInjection(value));
    }

    public static void main(String[] args) {
        Set<Long> ids = new HashSet<>();
        for (long i = 0; i < 20; i++) {
            ids.add(i);
        }
        String o = ids.toString();
        System.out.println(o);
        System.out.println("##############################");
        System.out.println(clean(o));;
    }
}