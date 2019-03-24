package com.feng.wenda.configuration;


import com.feng.wenda.interceptor.LoginRequiredInterceptor;
import com.feng.wenda.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;
@Autowired
private PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/**");
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/uploadImage");
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/editProfile");
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/question/*");
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/askQuestion");
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/addQuestion");
        super.addInterceptors(registry);
    }
}