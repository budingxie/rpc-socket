package com.py.server;

import com.py.RelaxHeartMonitorService;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

public class RelaxHeartMonitorServiceImpl extends UnicastRemoteObject
        implements RelaxHeartMonitorService {


    public RelaxHeartMonitorServiceImpl() throws RemoteException {
    }

    @Override
    public List<String> get(String idx) throws RemoteException {
        System.out.println(idx);
        return Arrays.asList("test", "demo", idx);
    }

    //用RMI注册此服务
    public static void main(String[] args) {
        try {
            RelaxHeartMonitorService service = new RelaxHeartMonitorServiceImpl();
            Naming.rebind("monitorService", service);
        } catch (Exception e) {
            System.err.println("服务注册异常！");
        }
    }
    // 其他逻辑
}