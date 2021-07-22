package com.py;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RelaxHeartMonitorService extends Remote {

    List<String> get(String idx) throws RemoteException;
}
