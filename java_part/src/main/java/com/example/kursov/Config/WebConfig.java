package com.example.kursov.Config;
//Этот класс конфигурации позволит запросам с http://localhost:8080 обращаться к конечной точке /submit с POST запросами.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/submit")
                        .allowedOrigins("http://localhost:8080")
                        .allowedMethods("POST")
                        .allowedHeaders("*");
            }
        };
    }
}