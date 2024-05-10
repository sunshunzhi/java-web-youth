package com.youth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youth.entity.User;
import com.youth.request.User.UserQueryDto;
import com.youth.response.CommonPage;
import com.youth.response.ResponseResult;

import java.util.List;


public interface UserService extends IService<User> {
    ResponseResult<CommonPage<User>> getPageUser(UserQueryDto userQueryDto);

    ResponseResult<String> addUser(User user);

    ResponseResult<String> updateUser(User user);

    ResponseResult<String> deleteUser(String id);

    ResponseResult<User> getUserById(String id);

    ResponseResult<List<User>> listUser();
}
