package com.py.remote;

import com.py.protocol.InfProtocol;
import com.py.util.JsonUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RpcTransport {

    private final String host;

    private final int port;

    public RpcTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object sendRequest(InfProtocol infProtocol) {
        try (Socket socket = new Socket(host, port);
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
            dataOutputStream.writeUTF(JsonUtil.objToJson(infProtocol));
            //清空缓冲区
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
