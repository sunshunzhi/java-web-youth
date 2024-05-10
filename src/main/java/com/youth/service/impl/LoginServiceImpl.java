package com.youth.service.impl;

import com.youth.common.Token;
import com.youth.dao.LoginDao;
import com.youth.entity.User;
import com.youth.response.ResponseResult;
import com.youth.service.LoginService;
import com.youth.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    LoginDao loginDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseResult<Token> userLogin(User user) {
        // 根据用户名和密码查询用户
        User login = loginDao.login(user);
        if (login != null) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("user", login);
            // 生成token
            Token token = jwtUtil.createTokens(payload);
            jwtUtil.tokenAssociation(token);
            jwtUtil.getUserInfo(token.getAccessToken());
            return ResponseResult.success(token, "登录成功");
        }
        return ResponseResult.failed("用户名或密码错误");
    }
}
