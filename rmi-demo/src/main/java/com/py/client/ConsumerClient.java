package com.py.client;

import com.py.RelaxHeartMonitorService;
import com.py.common.Constant;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ConsumerClient  {

    public static void main(String[] args) throws Exception {
        // 获取服务注册器
        Registry registry = LocateRegistry.getRegistry(Constant.HOST, Constant.PORT);

        // 获取所有注册的服务
        String[] serverList = registry.list();

        for (String serverStr : serverList) {
            System.out.println("已经注册的服务：" + serverStr);
        }
        // 寻找RMI_NAME对应的RMI实例
        RelaxHeartMonitorService monitorService = (RelaxHeartMonitorService) Naming.lookup(Constant.GET_RMI_NAME);
        List<String> result = monitorService.get("args[1]");
        System.out.println(result);
    }
}