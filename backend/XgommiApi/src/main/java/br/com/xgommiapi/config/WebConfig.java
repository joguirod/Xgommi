package br.com.xgommiapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Aplica a todas as rotas da API
                .allowedOrigins("http://127.0.0.1:5500") // Permite o Live Server ou outro domínio
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*")  // Permite todos os cabeçalhos
                .allowCredentials(true);  // Permite cookies/credenciais se necessário
    }
}
