package com.youth.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youth.entity.User;
import com.youth.enums.ResultEnum;
import com.youth.response.ResponseResult;
import com.youth.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        ResponseResult<Object> failed;
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        //从http请求头获取token
        String token = request.getHeader(jwtUtil.getHeader());

        User user = jwtUtil.getUserInfo(token);
        if (user.getRole() == 0) {
            return true;
        } else {
            failed = ResponseResult.failed(ResultEnum.FORBIDDEN);
            String json = new ObjectMapper().writeValueAsString(failed);
            response.getWriter().println(json);
            return false;
        }
    }

}
