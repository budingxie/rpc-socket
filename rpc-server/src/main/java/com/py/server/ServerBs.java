package com.py.server;

import com.py.impl.UserServiceImpl;
import com.py.protocol.InfProtocol;
import com.py.refl.ReflSuport;
import com.py.util.JsonUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerBs extends Thread {

    private final ServerSocket serverSocket;

    public ServerBs(int port) throws IOException {
        serverSocket = new ServerSocket(port);
//        serverSocket.setSoTimeout(10000);
    }

    private Class<?>[] implClass = {UserServiceImpl.class};

    public void run() {
        // 1.创建ServerSocket
        // 2.accept()等待连接
        // 3.getOutPutStream()获取请求流，获取请求信息
        // 4.getOutPutStream()获取返回流，返回信息
        // 5.关闭连接
        while (true) {
            try {
                System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("远程主机地址：" + server.getRemoteSocketAddress());

                // 接收请求信息
                DataInputStream in = new DataInputStream(server.getInputStream());
                String data = in.readUTF();

                // 处理接口请求，协议对象InfProtocol
                InfProtocol protocol = JsonUtil.jsonToObj(data, InfProtocol.class);

                // 根据协议对象，进行反射获取：对象，方法，参数类型，参数数据
                Object respData = new Object();
                Class<?> infClazz = ReflSuport.strToClazz(protocol.getInf());
                // 获取服务
                for (Class<?> implClazz : implClass) {
                    if (infClazz.isAssignableFrom(implClazz)) {
                        // 实例化
                        Object infInstance = ReflSuport.clazzToObj(implClazz);
                        // 获取需要执行的方法
                        Method method = ReflSuport.analyzeAndObtain(implClazz, protocol.getMethod(),
                                ReflSuport.cusTypeToJavaType(protocol.getTypes()));
                        // 对参数进行解析
                        Object[] paramsObj = ReflSuport.analyzeParams(method, protocol.getParams());
                        respData = method.invoke(infInstance, paramsObj);
                        break;
                    }
                }

                // 返回结果
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF(JsonUtil.objToJson(respData));

                // 关闭连接
                server.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        try {
            Thread t = new ServerBs(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
