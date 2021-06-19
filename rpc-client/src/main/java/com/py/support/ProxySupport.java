package com.py.support;

import com.py.remote.RemoteInvokeHandler;

import java.lang.reflect.Proxy;

public class ProxySupport {

    public static <T> T clientProxy(final Class<T> interfaceCls,
                                    final String className,
                                    final String host,
                                    final int port) {
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls},
                new RemoteInvokeHandler(className, host, port));
    }
}
