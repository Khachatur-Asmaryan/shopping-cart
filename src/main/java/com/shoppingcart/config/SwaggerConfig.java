package com.shoppingcart.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url(contextPath))
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .components(
                        new Components()
                                .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                                        new SecurityScheme()
                                                .name(HttpHeaders.AUTHORIZATION)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
