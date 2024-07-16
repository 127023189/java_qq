package qqclient.service;

import qqcommon.Message;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {   // 需要持续通信，做成while
        while (true) {
            try {
                System.out.println("客户端线程，等待从服务器端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message mess = (Message) ois.readObject(); // 如果没有得到消息，会持续阻塞


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}

