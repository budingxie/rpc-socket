package com.py.support;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.py.Server.StrToHexStr;

public class HeartTask implements Runnable {

    private Socket socket;

    private long lastReceiveTime;

    private boolean hasRunning;

    //心跳超时时间
    private static final int TIMEOUT = 60 * 1000;


    public HeartTask(Socket socket) {
        this.socket = socket;
        this.lastReceiveTime = System.currentTimeMillis();
        this.hasRunning = true;
    }

    @Override
    public void run() {
        byte[] bytes = new byte[1];
        String info = "";
        while (hasRunning) {
            try (InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {
                int len = inputStream.read();
                if (lastReceiveTime - System.currentTimeMillis() > TIMEOUT) {
                    // 超时
                    hasRunning = false;
                    break;
                }
                //返回下次调用可以不受阻塞地从此流读取或跳过的估计字节数,如果等于0则表示已经读完
                if (inputStream.available() > 0) {
                    //重置接收到数据的最新时间
                    lastReceiveTime = System.currentTimeMillis();
                    inputStream.read(bytes);
                    String tempStr = ByteArrayToHexStr(bytes);
                    info += tempStr;
                    //已经读完
                    if (inputStream.available() == 0) {
                        System.out.println(">>>线程" + Thread.currentThread().getId() + "收到:" + info);
                        String responseStr = "Hello";
                        //响应内容
                        String hexStr = StrToHexStr(responseStr);
                        hexStr = hexStr.replaceAll("0[x|X]|,", "");
                        byte[] byteArray = HexStrToByteArray(hexStr);
                        outputStream.write(byteArray);
                        outputStream.flush();
                        //重置,不然每次收到的数据都会累加起来
                        info = "";
                        System.out.println(">>>线程" + Thread.currentThread().getId() + "回应:" + responseStr);
                    }
                }

            } catch (Exception ex) {
                System.out.println();
            }
        }

    }

    private byte[] HexStrToByteArray(String hexStr) {
        return new byte[0];
    }

    private String ByteArrayToHexStr(byte[] bytes) {
        return null;
    }
}
