package qqserver.service;

import qqcommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * 该类的一个对象和某个客户端保持通信
 */
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userId; // 连接到的用户Id

    public ServerConnectClientThread(String userId,Socket socket){
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run(){

        while(true){
            try {
                System.out.println("服务端和用户端" + userId + "保持通信，读取数据...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
