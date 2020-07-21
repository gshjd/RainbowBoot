package com.example.rainboot.system.config.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LogConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logMdcInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public LogMdcInterceptor logMdcInterceptor() {
        return new LogMdcInterceptor();
    }
}
