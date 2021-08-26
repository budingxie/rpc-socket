package com.py.domain;

import java.io.Serializable;
import java.util.Objects;

public class ServerEntry implements Serializable {

    private static final long serialVersionUID = 189302740666003309L;

    /**
     * 服务标识
     */
    private String serviceId;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 机器 ip 信息
     */
    private String ip;

    /**
     * 端口信息
     */
    private int port;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 权重
     */
    private int weight;

    /**
     * 心跳时间
     */
    private long heartTime;

    /**
     * 是否 超时
     */
    private boolean hasTimeOut;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public long getHeartTime() {
        return heartTime;
    }

    public void setHeartTime(long heartTime) {
        this.heartTime = heartTime;
    }

    public boolean getHasTimeOut() {
        return hasTimeOut;
    }

    public void setHasTimeOut(boolean hasTimeOut) {
        this.hasTimeOut = hasTimeOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerEntry that = (ServerEntry) o;
        if (port != that.port) {
            return false;
        }
        if (Objects.equals(serviceId, that.serviceId)) {
            return false;
        }
        if (Objects.equals(ip, that.ip)) {
            return false;
        }
        if (Objects.equals(protocol, that.protocol)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, ip, port, protocol);
    }
}
