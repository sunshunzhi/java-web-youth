package com.youth.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.youth.enums.ResultEnum;
import com.youth.response.ResponseResult;
import com.youth.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ResponseResult<Object> failed;
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //从http请求头获取token
        String token = request.getHeader(jwtUtil.getHeader());

        if (StringUtils.isEmpty(token)) {
            failed = ResponseResult.failed(ResultEnum.UNAUTHORIZED, jwtUtil.getHeader() + "不能为空！");
            String json = new ObjectMapper().writeValueAsString(failed);
            response.getWriter().println(json);
            return false;
        }

        if (!jwtUtil.isRightToken(token)) {
            failed = ResponseResult.failed(ResultEnum.UNAUTHORIZED, jwtUtil.getHeader() + "不合法！");
            String json = new ObjectMapper().writeValueAsString(failed);
            response.getWriter().println(json);
            return false;
        }

        // 判断token是否超时
        if (jwtUtil.isTokenExpired(token)) {
            failed = ResponseResult.failed(ResultEnum.UNAUTHORIZED, jwtUtil.getHeader() + "已失效！");
            String json = new ObjectMapper().writeValueAsString(failed);
            response.getWriter().println(json);
            return false;
        }

        // 判断 token 是否已在黑名单
        if (jwtUtil.checkBlacklist(token)) {
            failed = ResponseResult.failed(ResultEnum.UNAUTHORIZED, jwtUtil.getHeader() + "已被加入黑名单！");
            String json = new ObjectMapper().writeValueAsString(failed);
            response.getWriter().println(json);
            return false;
        }
        return true;
    }
}
