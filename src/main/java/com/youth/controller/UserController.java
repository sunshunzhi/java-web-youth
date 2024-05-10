package com.youth.controller;

import com.youth.entity.User;
import com.youth.request.User.UserQueryDto;
import com.youth.response.CommonPage;
import com.youth.response.ResponseResult;
import com.youth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/youth/user")
@Tag(name = "用户管理", description = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "查询全部用户", description = "查询全部用户")
    @GetMapping("/list")
    public ResponseResult<List<User>> listUser() {
        return userService.listUser();
    }


    @Operation(summary = "查询单个用户", description = "按id查询用户")
    @Parameter(name = "id", description = "用户id", required = true)
    @GetMapping("/single/{id}")
    public ResponseResult<User> getUser(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }


    @Operation(summary = "新增用户", description = "新增用户")
    @Parameter(name = "user", description = "用户信息", required = true)
    @PostMapping("/add")
    public ResponseResult<String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }


    @Operation(summary = "删除用户", description = "根据id删除用户")
    @Parameter(name = "id", description = "用户id", required = true)
    @GetMapping("/delete/{id}")
    public ResponseResult<String> deleteUser(@PathVariable("id") String id) {
        return userService.deleteUser(id);
    }


    @Operation(summary = "修改用户", description = "根据id修改用户")
    @Parameter(name = "user", description = "用户信息", required = true)
    @PostMapping("/update")
    public ResponseResult<String> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @Operation(summary = "分页查询用户列表", description = "分页查询用户列表")
    @Parameter(name = "userQueryDto", description = "用户查询条件", required = true)
    @PostMapping("/page")
    public ResponseResult<CommonPage<User>> pageUser(@RequestBody UserQueryDto userQueryDto) {
        return userService.getPageUser(userQueryDto);
    }
}
