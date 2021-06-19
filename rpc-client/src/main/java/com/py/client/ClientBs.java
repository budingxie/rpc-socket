package com.py.client;

import com.py.inf.UserService;
import com.py.model.User;
import com.py.support.ProxySupport;

public class ClientBs {
    public static void main(String[] args) {
        UserService userService = ProxySupport.clientProxy(UserService.class,
                UserService.class.getName(),
                "localhost", 9902);
        User user = userService.getUser(123);
        System.out.println(user);
    }
}
