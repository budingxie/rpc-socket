package com.py.registry;

import com.py.domain.HeartData;
import com.py.domain.ServerEntry;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerRegister {
    /**
     * 1.保存需要注册的服务
     * 2.接收心跳包，更新心跳时间
     * 3.定时检查有没有服务，心跳超时
     * 4.解绑(删除)服务
     */
    private final Map<String, Set<ServerEntry>> registerMap;

    public ServerRegister() {
        this.registerMap = new ConcurrentHashMap<>();
    }

    public boolean register(ServerEntry serviceEntry){
        if (!registerMap.containsKey(serviceEntry.getServiceId())) {
            registerMap.put(serviceEntry.getServiceId(), new HashSet<>());
        }
        return registerMap.get(serviceEntry.getServiceId()).add(serviceEntry);
    }

    public void heartRecv(ServerEntry serviceEntry) {
        Set<ServerEntry> serverSet = registerMap.get(serviceEntry.getServiceId());
        for (ServerEntry entry : serverSet) {
            if (entry.equals(serviceEntry)) {
                entry.setHeartTime(System.currentTimeMillis());
                entry.setHasTimeOut(false);
            }
        }
    }

    public HeartData checkHeart(){
        // 定时检查 registerMap中服务的 心跳包是否超时
        for (Map.Entry<String, Set<ServerEntry>> entry : registerMap.entrySet()) {
            Set<ServerEntry> value = entry.getValue();
            for (ServerEntry item : value) {
                if (item.getHasTimeOut()) {
                    // 发送心跳检查
                    String ip = item.getIp();
                    int port = item.getPort();
                    HeartData heartData = new HeartData();
                    heartData.setIp(ip);
                    heartData.setPort(port);
                    heartData.setData("在线吗");
                    return heartData;
                }
            }
        }
        return null;
    }

    public boolean unRegister(ServerEntry serviceEntry){
        Set<ServerEntry> serverSet = registerMap.get(serviceEntry.getServiceId());
        return serverSet.remove(serviceEntry);
    }

}
