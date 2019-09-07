package com.nekosighed.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 默认组
     * @return
     */
    @Bean
    public Docket defaultGroup(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nekosighed.api"))
                .build();
    }

    /**
     * 微信组
     * @return
     */
    @Bean
    public Docket wxOperationGroup(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("wxOperation")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nekosighed.api"))
                .build();
    }

    /**
     * 网页信息
     *
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                // 网页标题
                .title("短视频后端api接口文档")
                // 网页描述
                .description("以下是接口描述")
                // 指定联系人描述
                .contact(
                        new Contact(
                                "akarin",
                                "http://www.nekosighed.com",
                                "akarin@5913@163.com"
                        )
                )
                // 指定版本号
                .version("1.0")
                .build();
    }

}
