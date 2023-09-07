package com.yarns.december;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author  Yarns
 * @date 15:14
 * @version  1.0
 **/
@SpringBootApplication
@MapperScan("com.yarns.december.mapper")
public class DecemberApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DecemberApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
