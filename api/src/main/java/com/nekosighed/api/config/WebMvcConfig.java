package com.nekosighed.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//public class WebMvcConfig extends WebMvcConfigurerAdapter 老版本写法
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 资源控制
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 表示可以访问所有资源
        registry.addResourceHandler("/**")
                // swagger2 的静态资源保存路径
                .addResourceLocations("classpath:/META-INF/resources/")
                // 可以查看 用户资源 保存路径
                .addResourceLocations("file:/E:/wx_video/");
    }
}
