package org.art.tetragallery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class WebConfig {

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(@NonNull CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins(
                                    "http://localhost:5173",// Vite dev
                                    "http://localhost:8080",
                                    "http://localhost"
                                    // ,"https://our-frontend.com" // later
                            )
                            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                            .allowedHeaders("*");
                }
            };
        }
}
