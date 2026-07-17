package com.amit.careerpilotai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI careerPilotOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("CareerPilotAI API")
                                .version("1.0")
                                .description("AI Powered Career Guidance Backend built using Spring Boot and Gemini AI")
                                .contact(
                                        new Contact()
                                                .name("Amit Bandivaddar")
                                                .email("bandivaddaramit@gmail.com")
                                )
                );
    }
}