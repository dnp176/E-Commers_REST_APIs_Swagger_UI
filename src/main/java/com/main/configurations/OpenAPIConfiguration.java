package com.main.configurations;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
@OpenAPIDefinition
@Configuration
public class OpenAPIConfiguration  {
	
	@Value("${server.url}")
    private String serverUrl;
	
	@Bean
    public OpenAPI customOpenAPI() {
        // SecurityScheme for JWT Bearer Token
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("E-Commers REST APIs Projects")
                        .version("1.0")
                        .description("This project is represent for real time e-commers projects.")
                        .contact(new Contact()
                                .name("Demo UserName")
                                .email("demouser@xyz.com")))
                .addSecurityItem(securityRequirement)
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme))
        .addServersItem(new Server().url(serverUrl));  // Update this line with your HTTPS URL
    }
}
