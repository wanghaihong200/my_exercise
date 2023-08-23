package com.example.springbootlearn.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: Swagger2 配置类   文档接口地址：http://localhost:8080/v2/api-docs
 * 文档页面地址：http://http://localhost:8080/swagger-ui.html
 * swagger增强版：http://localhost:8080/doc.html(ip+端口+doc.html)
 * @author: 王海虹
 * @create: 2022-10-10 19:15
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    @Value(value = "${swagger.enabled}")
    Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启 swagger2
                .enable(swaggerEnabled).select()
                // 扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.springbootlearn.controller"))
                // 指定处理路径，PathSelectors.any() 代表所有的 路径
                .paths(PathSelectors.any()).build()
                .pathMapping("/")
                .globalOperationParameters(this.getParameterList());
    }

    /**
     * 添加request header参数配置
     */
    private List<Parameter> getParameterList() {
        ParameterBuilder clientIdTicket = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        clientIdTicket.name("token").description("token令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build(); //设置false，表示clientId参数 非必填,可传可不传！
        pars.add(clientIdTicket.build());
        return pars;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("瑞吉平台接口文档")
                .description("demo")
                .contact(new Contact("Mr.海", "https://www.baidu.com", "wanghaihong200@163.com"))
                .version("1.0.0")
                .build();
    }
}
