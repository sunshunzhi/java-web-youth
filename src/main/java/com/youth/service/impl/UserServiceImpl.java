package com.youth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youth.dao.UserDao;
import com.youth.entity.User;
import com.youth.request.User.UserQueryDto;
import com.youth.response.CommonPage;
import com.youth.response.ResponseResult;
import com.youth.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public ResponseResult<List<User>> listUser() {
        List<User> users = baseMapper.selectList(null);
        return ResponseResult.success(users);
    }

    @Override
    public ResponseResult<String> addUser(User user) {
        user.setRole(1);
        int insert = baseMapper.insert(user);
        if (insert > 0) {
            return ResponseResult.success("添加成功");
        } else {
            return ResponseResult.failed("添加失败");
        }
    }

    @Override
    public ResponseResult<String> updateUser(User user) {
        int update = baseMapper.updateById(user);
        if (update > 0) {
            return ResponseResult.success("修改成功");
        } else {
            return ResponseResult.failed("修改失败");
        }
    }

    @Override
    public ResponseResult<String> deleteUser(String id) {
        int delete = baseMapper.deleteById(id);
        if (delete > 0) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.failed("删除失败");
        }
    }

    @Override
    public ResponseResult<User> getUserById(String id) {
        User user = baseMapper.selectById(id);
        if (user != null) {
            return ResponseResult.success(user);
        } else {
            return ResponseResult.failed("查询失败");
        }
    }

    @Override
    public ResponseResult<CommonPage<User>> getPageUser(UserQueryDto userQueryDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userQueryDto.getUsername());
        queryWrapper.eq("role", 1);
        IPage<User> page = userQueryDto.getPage();
        IPage<User> userIPage = baseMapper.selectPage(page, queryWrapper);
        return ResponseResult.success(CommonPage.restPage(userIPage));
    }
}
