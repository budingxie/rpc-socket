package com.py.remote;

import com.google.gson.Gson;
import com.py.protocol.InfProtocol;

import java.io.*;
import java.net.Socket;

public class RpcTransport {

    private final String host;

    private final int port;

    private Gson gson = new Gson();

    public RpcTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object sendRequest(InfProtocol infProtocol) {
        try (Socket socket = new Socket(host, port);
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
            dataOutputStream.writeUTF(gson.toJson(infProtocol));
            //清空缓冲区
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
