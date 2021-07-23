package com.py.common;

public class Constant {

    public final static String PROTOCOL = "rmi://";

    public final static String SYMBOL = ":";

    public final static String HOST = "127.0.0.1";

    public final static int PORT = 8977;

    // rmi://127.0.0.1:8977
    public final static String PREFIX_RMI_NAME = PROTOCOL + HOST + SYMBOL + PORT;

    // 注册的方法接口：rmi://127.0.0.1:8977/get
    public final static String GET_RMI_NAME = PREFIX_RMI_NAME + "/get";
}
