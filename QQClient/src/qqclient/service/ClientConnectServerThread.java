package qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket(){
        return socket;
    }

    @Override
    public void run() {   // 需要持续通信，做成while
        while (true) {
            try {

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject(); // 如果没有得到消息，会持续阻塞
                // 判断这个message 类型,然后进行相应的处理
                System.out.println("客户端线程，等待从服务器端发送的消息");
                //如果时读取到的是 服务端返回的在线用户列表
                if(message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    // 取出在线列表信息
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n=======当前在线用户列表=======");
                    for (String onlineUser : onlineUsers) {
                        System.out.println("用户： " + onlineUser);
                    }
                }else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    System.out.println("\n" + message.getSender() + "对" + message.getGetter() + "说： " +
                            message.getContent());

                }else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    // 发送给所有人
                    System.out.println("\n" + message.getSender() + " 对大家说: " + message.getContent());
                }else{
                    System.out.println("其他类型暂时不处理");
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

}

