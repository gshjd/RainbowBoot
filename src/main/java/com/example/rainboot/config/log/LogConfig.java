package com.example.rainboot.config.log;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
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
