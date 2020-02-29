package com.example.freemarker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/download/**").addResourceLocations("file:F:/idea/zip/");
        registry.addResourceHandler("/download/**").addResourceLocations("file:/home/work/zip/");
    }
}
