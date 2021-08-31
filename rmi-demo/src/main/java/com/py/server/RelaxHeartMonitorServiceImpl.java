package com.py.server;

import com.py.RelaxHeartMonitorService;
import com.py.common.Constant;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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
            // 注册RMI端口
            LocateRegistry.createRegistry(Constant.PORT);
            // 创建一个服务
            RelaxHeartMonitorService service = new RelaxHeartMonitorServiceImpl();
            // 服务名绑定
            Naming.rebind(Constant.GET_RMI_NAME, service);

            System.out.println("启动服务：" + Constant.GET_RMI_NAME);
        } catch (Exception e) {
            System.err.println("服务注册异常！");
        }
    }
}