package com.github.carloscontrerasruiz.booking.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Booking API")
                        .description("The best Booking API for the very last hotel in Cancun")
                        .version("v1.0.0")
                );
    }
}
