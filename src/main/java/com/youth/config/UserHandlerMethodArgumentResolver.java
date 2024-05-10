package com.youth.config;

import com.youth.annotaion.LoginUser;
import com.youth.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义参数解析器
 * 自动解析token中的user信息
 */
@Configuration
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        System.out.println("supportsParameter");
        return parameter.getParameterType().isAssignableFrom(com.youth.entity.User.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader(jwtUtil.getHeader());
        if (StringUtils.isEmpty(token)) {
            throw new Exception(jwtUtil.getHeader() + "为空");
        }
        return jwtUtil.getUserInfo(token);
    }
}
