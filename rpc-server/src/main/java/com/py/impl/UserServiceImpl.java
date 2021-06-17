package com.py.impl;

import com.py.inf.UserService;
import com.py.model.User;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(Integer age) {
        return new User("张三", age);
    }
}
