package com.simplecraft.pyreneansafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica para todas las rutas de la API
                .allowedOrigins("http://localhost:5173") // Permite tu React local de Vite
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todas las cabeceras (incluyendo Authorization para el JWT)
                .allowCredentials(true); // Permite el uso de credenciales/cookies si procede
    }
}