package com.py.client;

import com.py.RelaxHeartMonitorService;

import java.rmi.Naming;
import java.util.List;

public class ConsumerClient  {

    // monitorService必须为我们服务端注册的服务名
    private static final String RMI_REGISTRY = "rmi://127.0.0.1/monitorService";

    public static void main(String[] args) throws Exception {
        RelaxHeartMonitorService service = (RelaxHeartMonitorService) Naming.lookup(RMI_REGISTRY);
        List<String> value = service.get("args[0]");
        System.out.println(value);
    }
}