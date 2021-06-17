package com.py.remote;

import com.google.gson.Gson;
import com.py.protocol.InfProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class RemoteInvokeHandler implements InvocationHandler {

    // 后续改造，把通用的写成工具类
    private Gson gson = new Gson();

    private String className;

    private String host;

    private int port;

    public RemoteInvokeHandler(String className, String host, int port) {
        this.className = className;
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("toString".equals(method.getName())) {
            // 一些不需要代理的方法
            return method.invoke(new Object(), args);
        }

        InfProtocol infProtocol = new InfProtocol();
        infProtocol.setInf(className);
        infProtocol.setMethod(method.getName());

        Parameter[] parameters = method.getParameters();
        String[] types = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class<?> type = parameters[i].getType();
            switch (type.getName()) {
                case "java.lang.String":
                    types[i] = "string";
                    break;
                case "java.lang.Integer":
                    types[i] = "int";
            }
        }
        infProtocol.setTypes(types);

        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String argName = parameters[i].getName();
            params.put(argName, args[i]);
        }


        infProtocol.setParams(params);

        // socket调用
        RpcTransport rpcTransport = new RpcTransport(host, port);
        Object result = rpcTransport.sendRequest(infProtocol);
        Class<?> returnType = method.getReturnType();
        if ("java.lang.String".equals(result.getClass().getName())) {
            return gson.fromJson(result.toString(), returnType);
        }
        return gson.fromJson(gson.toJson(result), returnType);
    }
}
