package com.youth.config;

import com.youth.interceptor.AdminInterceptor;
import com.youth.interceptor.JwtInterceptor;
import com.youth.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class InterceptConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Autowired
    private UserInterceptor userInterceptor;

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Autowired
    private UserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(jwtInterceptor)
                //拦截所有路径
                .addPathPatterns("/**")
                //放行登录相关接口
                .excludePathPatterns("/login/**")
                //放行 Knife4j所有的资源路径
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/v3/api-docs/**")
                .excludePathPatterns("/swagger-resources/**");
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/youth/**", "login/**");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**", "login/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userHandlerMethodArgumentResolver);
    }
}