package com.py;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    public static void main(String[] args) {



        try {
            System.out.println(">>>服务启动,等待终端的连接\n");
            ServerSocket server = new ServerSocket(8888);
            int count = 0;
            while (true) {
                //开启监听
                Socket socket = server.accept();
                count++;
                System.out.println(">>>开启第" + count + "次长连接...");
                Server thread = new Server(socket);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //16进制数字字符集
    public static final String HEXSTRING = "0123456789ABCDEF";
    //心跳超时时间
    private static final int TIMEOUT = 60 * 1000;

    private Socket m_socket;
    //接收到数据的最新时间
    private long m_lastReceiveTime = System.currentTimeMillis();
    //该线程是否正在运行
    private boolean m_isRuning = false;

    public Server(Socket socket) {
        this.m_socket = socket;
    }

    @Override
    public void start() {
        if (m_isRuning) {
            System.out.println(">>>线程" + this.getId() + "启动失败,该线程正在执行");
            return;
        } else {
            m_isRuning = true;
            super.start();
        }
    }

    @Override
    public void run() {
        //字节输入流
        InputStream inputStream = null;
        //字节输出流
        OutputStream outputStream = null;
        try {
            inputStream = m_socket.getInputStream();
            outputStream = m_socket.getOutputStream();
            String info = "";
            //按byte读
            byte[] bytes = new byte[1];
            while (m_isRuning) {
                //检测心跳
                if (System.currentTimeMillis() - m_lastReceiveTime > TIMEOUT) {
                    m_isRuning = false;
                    //跳出,执行finally块
                    break;
                }
                //返回下次调用可以不受阻塞地从此流读取或跳过的估计字节数,如果等于0则表示已经读完
                if (inputStream.available() > 0) {
                    //重置接收到数据的最新时间
                    m_lastReceiveTime = System.currentTimeMillis();
                    inputStream.read(bytes);
                    String tempStr = ByteArrayToHexStr(bytes);
                    info += tempStr;
                    //已经读完
                    if (inputStream.available() == 0) {
                        System.out.println(">>>线程" + this.getId() + "收到:" + info);
                        String responseStr = "Hello";
                        //响应内容
                        String hexStr = StrToHexStr(responseStr);
                        hexStr = hexStr.replaceAll("0[x|X]|,", "");
                        byte[] byteArray = HexStrToByteArray(hexStr);
                        outputStream.write(byteArray);
                        outputStream.flush();
                        //重置,不然每次收到的数据都会累加起来
                        info = "";
                        System.out.println(">>>线程" + this.getId() + "回应:" + responseStr);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//关闭资源
            System.out.println(">>>线程" + this.getId() + "的连接已断开\n");
            try {
                if (outputStream != null)
                    outputStream.close();
                if (inputStream != null)
                    inputStream.close();
                if (m_socket != null)
                    m_socket.close();
                m_isRuning = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * byte[]转16进制Str
     *
     * @param byteArray
     */
    public static String ByteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = HEXSTRING.toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int i = 0; i < byteArray.length; i++) {
            int temp = byteArray[i] & 0xFF;
            hexChars[i * 2] = hexArray[temp >>> 4];
            hexChars[i * 2 + 1] = hexArray[temp & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Str转16进制Str
     *
     * @param str
     * @return
     */
    public static String StrToHexStr(String str) {
        //根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        //将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append("0x");
            stringBuilder.append(HEXSTRING.charAt((bytes[i] & 0xf0) >> 4));
            stringBuilder.append(HEXSTRING.charAt((bytes[i] & 0x0f) >> 0));
            //去掉末尾的逗号
            if (i != bytes.length - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制Str转byte[]
     *
     * @param hexStr 不带空格、不带0x、不带逗号的16进制Str,如:06EEF7F1
     * @return
     */
    public static byte[] HexStrToByteArray(String hexStr) {
        byte[] byteArray = new byte[hexStr.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = hexStr.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }
}
