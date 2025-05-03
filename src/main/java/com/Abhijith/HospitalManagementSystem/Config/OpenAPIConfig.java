package com.Abhijith.HospitalManagementSystem.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
@Bean
public OpenAPI CustomOpenAPI() {
	return new OpenAPI()
			.info(new Info()
					.title("Hospital Management System")
					.description("Hospital Management System API With Spring Boot JWT Authentication")
					.version("v1.0"))
			.addSecurityItem(new SecurityRequirement().
					addList("bearer-key"))
			.components(new Components()
					.addSecuritySchemes("bearerAuth",
							new SecurityScheme()
									.type(SecurityScheme.Type.HTTP)
									.scheme("bearer")
									.bearerFormat("JWT")
					)
			);
	}

}