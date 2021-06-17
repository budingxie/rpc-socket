package com.py.client;


import com.google.gson.Gson;
import com.py.inf.UserService;
import com.py.model.User;
import com.py.protocol.InfProtocol;
import com.py.support.ProxySupport;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientBs {
    public static void main(String[] args) {
        ProxySupport rpcClientProxy = new ProxySupport();
        UserService userService = rpcClientProxy.clientProxy(UserService.class, UserService.class.getName(), "localhost", 9902);
        System.out.println(userService);
        User user = userService.getUser(123);
        System.out.println(user);


        // 1.创建Socket
        // 2.getOutputStream()；发送信息到服务端
        // 3.getInputStream()；获取服务端返回的信息
        // 4.断开连接

//        Gson gson = new Gson();
//
//        String serverName = args[0];
//        int port = Integer.parseInt(args[1]);
//        try {
//            System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
//            Socket client = new Socket(serverName, port);
//            System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
//            OutputStream outToServer = client.getOutputStream();
//            DataOutputStream out = new DataOutputStream(outToServer);
//
//            // 发送信息{"inf":"com.py.inf.UserService","method":"getUser", "types":["int"], "params":{"age":1}}
//            InfProtocol infProtocol = new InfProtocol();
//            infProtocol.setInf("com.py.inf.UserService");
//            infProtocol.setMethod("getUser");
//            infProtocol.setTypes(new String[]{"int"});
//            Map<String, Object> params = new HashMap<>();
//            params.put("age", 1);
//            infProtocol.setParams(params);
//
//            String msg = gson.toJson(infProtocol);
//            out.writeUTF(msg);
//            InputStream inFromServer = client.getInputStream();
//            DataInputStream in = new DataInputStream(inFromServer);
//            System.out.println("服务器响应： " + in.readUTF());
//            client.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
