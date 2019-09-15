package com.nekosighed.api.config;

import com.nekosighed.api.controller.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
//public class WebMvcConfig extends WebMvcConfigurerAdapter 老版本写法
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 资源控制
     *
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

    /**
     * 将拦截器注册为 bean
     *
     * @return
     */
    @Resource
    UserInterceptor userInterceptor;

    /**
     * 注册 拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 将需要拦截的路径填入  支持 ant写法
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/video/uploadVideo", "/video/likeVideo", "/video/unlikeVideo")
                .addPathPatterns("/operation/**").excludePathPatterns("/operation/publisherInfo");
        // 将所有注册信息进行注册
        // 老版本写法
        // super.addInterceptors(registry);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
