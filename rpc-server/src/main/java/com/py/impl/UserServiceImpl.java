package com.py.impl;

import com.py.inf.UserService;
import com.py.model.User;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(Integer age) {
        System.out.println("-----getUser");
        return new User("张三", age);
    }

    @Override
    public String toString() {
        System.out.println("-----toString");
        return "UserServiceImpl{}";
    }
}
