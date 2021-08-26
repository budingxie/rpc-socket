package com.py.domain;

import java.io.Serializable;

public class HeartData implements Serializable {

    private static final long serialVersionUID = -6282210251760287967L;

    private String ip;

    private int port;

    private String data;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
