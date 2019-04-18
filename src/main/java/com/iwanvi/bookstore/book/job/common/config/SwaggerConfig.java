package com.iwanvi.bookstore.book.job.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置类
 *
 * @author zzw
 * @since 2018年12月17日20:39:15
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iwanvi.bookstore.searchcenter.job"))
//        .apis(RequestHandlerSelectors.basePackage("com.iwanvi.bookstore.searchcenter.job"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
        return docket;
    }


    /**
     * app信息初始化
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("中文书城")
                .description("中文书城-搜索中心-JOB")
                .termsOfServiceUrl("")
                .contact(new Contact("", "", ""))
                .license("")
                .licenseUrl("")
                .version("0.0.1")
                .build();
    }
}