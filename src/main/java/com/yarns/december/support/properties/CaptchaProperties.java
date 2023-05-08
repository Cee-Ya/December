package com.yarns.december.support.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置类
 * @author Yarns
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {
    /**
     * 验证码有效时间，单位秒
     */
    private Long time;
    /**
     * 验证码类型
     */
    private String type;
    /**
     * 图片宽度，px
     */
    private Integer width;
    /**
     * 图片高度，px
     */
    private Integer height;
    /**
     * 验证码位数
     */
    private Integer length;
    /**
     * 验证码值的类型
     */
    private Integer charType;
}
