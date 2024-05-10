package com.youth.service;

import com.youth.common.Token;
import com.youth.entity.User;
import com.youth.response.ResponseResult;

public interface LoginService {

    ResponseResult<Token> userLogin(User user);

}
