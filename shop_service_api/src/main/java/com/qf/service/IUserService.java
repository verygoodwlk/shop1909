package com.qf.service;

import com.qf.entity.User;

public interface IUserService {

    int register(User user);

    User queryByUserName(String username);

    int updatePassword(String username, String password);
}
