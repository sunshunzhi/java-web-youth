package com.youth.controller;

import com.youth.common.Token;
import com.youth.entity.User;
import com.youth.response.ResponseResult;
import com.youth.service.LoginService;
import com.youth.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "用户登录", description = "用户登录、刷新令牌、注销")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户登录")
    @Parameter(name = "user", description = "用户登录", required = true)
    @PostMapping("/login")
    public ResponseResult<Token> userLogin(@RequestBody User user) {
        return loginService.userLogin(user);
    }

    /**
     * 刷新令牌
     */
    @Operation(summary = "刷新令牌", description = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌", required = true)
    @PostMapping("/refreshToken/{refreshToken}")
    public ResponseResult<Token> refreshToken(@PathVariable("refreshToken") String refreshToken) {

        if (StringUtils.isEmpty(refreshToken)) {
            return ResponseResult.failed("refreshToken不可为空");
        }

        // 判断token是否超时
        if (jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseResult.failed("refreshToken已超时");
        }
        // 判断refreshToken是否在黑名单
        if (jwtUtil.checkBlacklist(refreshToken)) {
            return ResponseResult.failed("refreshToken已失效");
        }
        // 刷新令牌 放入黑名单
        jwtUtil.addBlacklist(refreshToken, jwtUtil.getExpirationDate(refreshToken));
        // 访问令牌 放入黑名单
        String accessToken = jwtUtil.getAccessTokenByRefresh(refreshToken);
        if (!StringUtils.isEmpty(accessToken)) {
            jwtUtil.addBlacklist(accessToken, jwtUtil.getExpirationDate(accessToken));
        }

        // 生成新的 访问令牌 和 刷新令牌
        User user = jwtUtil.getUserInfo(refreshToken);

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);

        // 生成Token
        Token token = jwtUtil.createTokens(map);
        return ResponseResult.success(token, "刷新成功");
    }

    /**
     * 登出/注销
     */
    @Operation(summary = "注销", description = "注销")
    @Parameter(name = "token", description = "访问令牌", required = true)
    @PostMapping("/logOut/{token}")
    public ResponseResult<String> logOut(@PathVariable("token") String token) {
        // 放入黑名单
        jwtUtil.addBlacklist(token, jwtUtil.getExpirationDate(token));
        return ResponseResult.success("注销成功");
    }
}
