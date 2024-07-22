package com.chandankrr.expenseservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI expenseServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Expense Service API")
                        .description("This API handles the expense services for Budget Buddy, a mobile application designed for tracking expenses. It includes endpoints for  create, update, get expense, and more.")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")));
    }
}
