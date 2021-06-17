package com.py.impl;

import com.py.inf.UserService;
import com.py.model.User;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class UserServiceImpl implements UserService {

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = UserServiceImpl.class.getMethod("getUser", Integer.class);
        for (Parameter parameter : method.getParameters()) {
            System.out.println(parameter.getName());
        }
    }

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
