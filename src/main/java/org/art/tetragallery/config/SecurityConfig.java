package org.art.tetragallery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class SecurityConfig {

    @Value("${app.api-key}")
    private String apiKey;

    @Bean
    public FilterRegistrationBean<ApiKeyAuthFilter> apiKeyFilter() {
        FilterRegistrationBean<ApiKeyAuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ApiKeyAuthFilter(apiKey));

        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

}