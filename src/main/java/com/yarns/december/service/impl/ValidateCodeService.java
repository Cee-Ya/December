package com.yarns.december.service.impl;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.yarns.december.support.constant.Constant;
import com.yarns.december.support.exception.BaseException;
import com.yarns.december.support.helper.RedisHelper;
import com.yarns.december.support.properties.CaptchaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yarns
 */
@Service
@RequiredArgsConstructor
public class ValidateCodeService {
    private final RedisHelper redisHelper;
    private final CaptchaProperties captchaProperties;

    /**
     * 生成验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, BaseException {
        String key = request.getParameter("key");
        if (StringUtils.isBlank(key)) {
            throw new BaseException("验证码key不能为空");
        }
        setHeader(response, captchaProperties.getType());

        Captcha captcha = createCaptcha(captchaProperties);
        redisHelper.set(Constant.Redis.CODE_PREFIX + key, StringUtils.lowerCase(captcha.text()), captchaProperties.getTime());
        captcha.out(response.getOutputStream());
    }


    /**
     * 校验验证码
     *
     * @param key   前端上送 key
     * @param value 前端上送待校验值
     */
    public void check(String key, String value) throws BaseException {
        Object codeInRedis = redisHelper.get(Constant.Redis.CODE_PREFIX + key);
        if (StringUtils.isBlank(value)) {
            throw new BaseException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new BaseException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new BaseException("验证码不正确");
        }
    }

    /**
     * 通过验证码配置文件FebsValidateCodeProperties生成相应的验证码，比如PNG格式的或者GIF格式的，验证码图片的长宽高，验证码字符的类型（纯数字，纯字母或者数字字母组合），验证码字符的长度等
     * @param code
     * @return
     */
    private Captcha createCaptcha(CaptchaProperties code) {
        Captcha captcha = null;
        if (StringUtils.equalsIgnoreCase(code.getType(), Constant.FileConstant.GIF)) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
            captcha.setCharType(code.getCharType());
        }else if (StringUtils.equalsIgnoreCase(code.getType(), Constant.FileConstant.ARITHMETIC)) {
            // 第三个参数是几位数运算，默认是两位
            captcha = new ArithmeticCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
            captcha.setCharType(code.getCharType());
        }
        return captcha;
    }

    /**
     * 用于设置响应头。在生成验证码图片后我们需要将其返回到客户端，所以需要根据不同的验证码格式设置不同的响应头
     * @param response
     * @param type
     */
    private void setHeader(HttpServletResponse response, String type) {
        if (StringUtils.equalsIgnoreCase(type, Constant.FileConstant.GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

}
