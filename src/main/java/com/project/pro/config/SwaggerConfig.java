package com.project.pro.config;

import com.project.pro.controller.PedidoController;
import com.project.pro.utils.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration

public class SwaggerConfig {

//
//    @Bean
//    public Docket portal() {
//        return new Docket(DocumentationType.OAS_30)
//                .groupName("Portal")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(PedidoController.class.getPackage().getName()))
//                .paths(PathSelectors.ant("/*"))
//                .build()
//                .securityContexts(ListUtils.toList(securityContext()))
//                .securitySchemes(ListUtils.toList(apiKey()))
//                .apiInfo(metaInfo());
//    }
//
//    @Bean
//    public Docket gestao() {
//        return new Docket(DocumentationType.OAS_30)
//                .groupName("Gestão")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(PedidoController.class.getPackage().getName()))
//                .paths(PathSelectors.ant("/*"))
//                .build()
//                .securityContexts(ListUtils.toList(securityContext()))
//                .securitySchemes(ListUtils.toList(apiKey()))
//                .apiInfo(metaInfo());
//    }
//
//    private ApiInfo metaInfo() {
//        return new ApiInfoBuilder()
//                .title("Clínica API")
//                .description("<b>Environment:</b> " +  "" + " </br></br> Created by SmartBr" )
//                .version("1.0")
//                .termsOfServiceUrl("http://smartbr.com")
//                .contact(new Contact("SmartBR", "https://smartbr.com", "contato@smartbr.com"))
//                .build();
//    }

//    private ApiKey apiKey() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return ListUtils.toList(new SecurityReference("JWT", authorizationScopes));
//    }


}
