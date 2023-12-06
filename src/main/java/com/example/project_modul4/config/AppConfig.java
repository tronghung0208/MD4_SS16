package com.example.project_modul4.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@ComponentScan("com.example.project_modul4")
@PropertySource("classpath:config.properties")
public class AppConfig implements WebMvcConfigurer, ApplicationContextAware {
    @Value("${path}")
    private String pathUpload;

    private ApplicationContext applicationContext;

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        return thymeleafViewResolver;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(52426800);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**",
                        "/img/**",
                        "js/**",
                        "/fonts/**",
                        "/media/**",
                        "/webfonts/**",
                        "/css/**",
                        "/fonts/**",
                        "images/**",
                        "js/**",
                        "/tinymce/**",
                        "/uploads/**").
                addResourceLocations("classpath:assets/css/",
                        "classpath:assets/img/",
                        "classpath:assets/js/",
                        "classpath:assets/fonts/",
                        "classpath:assets/media/",
                        "classpath:assets/webfonts/",
                        "classpath:accets/css/",
                        "classpath:accets/fonts/",
                        "classpath:accets/images/",
                        "classpath:accets/js/",
                        "classpath:accets/tinymce/",
                        "file:"+pathUpload);
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // tác dụng với controller /admin/**
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/admin/**");
    }
}
