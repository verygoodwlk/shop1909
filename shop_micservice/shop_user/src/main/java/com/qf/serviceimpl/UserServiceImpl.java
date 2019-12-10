package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        Integer count = userMapper.selectCount(queryWrapper);

        if(count == 0){
            return userMapper.insert(user);
        }

        return -1;
    }

    @Override
    public User queryByUserName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public int updatePassword(String username, String password) {
        User user = this.queryByUserName(username);
        user.setPassword(password);
        return userMapper.updateById(user);
    }
}
