package com.py.remote;

import com.py.protocol.InfProtocol;
import com.py.refl.ReflSuport;
import com.py.support.ParamSupport;
import com.py.util.JsonUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class RemoteInvokeHandler implements InvocationHandler {

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

        // 过滤出Object里面的方法
        if ("toString".equals(method.getName())) {
            // 一些不需要代理的方法
            return method.invoke(new Object(), args);
        }

        // 协议对象
        InfProtocol infProtocol = new InfProtocol();
        infProtocol.setInf(className);
        infProtocol.setMethod(method.getName());

        // 把java中基础类型转化
        Parameter[] parameters = method.getParameters();
        String[] types = ReflSuport.typeTransform(parameters);
        infProtocol.setTypes(types);

        // 参数转换
        Map<String, Object> params = ParamSupport.paramTransform(parameters, args);
        infProtocol.setParams(params);

        // socket调用
        RpcTransport rpcTransport = new RpcTransport(host, port);

        // 结果类型转换，返回
        Object result = rpcTransport.sendRequest(infProtocol);
        Class<?> returnType = method.getReturnType();
        if ("java.lang.String".equals(result.getClass().getName())) {
            return JsonUtil.jsonToObj(result.toString(), returnType);
        }
        return JsonUtil.jsonToObj(JsonUtil.objToJson(result), returnType);
    }

}
