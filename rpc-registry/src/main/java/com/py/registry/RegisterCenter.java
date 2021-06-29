package com.py.registry;

import com.py.support.HeartTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RegisterCenter {

    public static void main(String[] args) {
        // 注册中心，维持心跳，保证长链接
        // 1.由 服务端 / 客户端 发送心跳包
        // 2.注册中心进行 超时判断，然后发送心跳包检测
        // 3.服务端 / 客户端 也有超时检测
        // 4.每次收到心跳包，把时间更新
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8884);
        } catch (IOException e) {
            return;
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                HeartTask heartTask = new HeartTask(socket);

                // todo 线程池
                Thread thread = new Thread(heartTask);
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
