package qqserver.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

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

    public Socket getSocket(){
        return socket;
    }

    @Override
    public void run(){

        while(true){
            try {
                System.out.println("服务端和用户端" + userId + "保持通信，读取数据...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                TimeUnit.SECONDS.sleep(5);
                // 对相应的动作进行处理
                if(message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    // 客户端要请求在线列表
                    System.out.println(message.getSender() + " 要在线用户列表");
                    String onlineUser = ManageClientThreads.getOnlineUser();
                    // 返回message信息
                    // 返回客户端，标志返回列表标志
                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message2.setContent(onlineUser);
                    message2.setGetter(message.getSender()); // 接收者变为原来获取信息者

                    // 写入
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                }else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    // 处理普通信息

                    ServerConnectClientThread serverConnectClientThread = ManageClientThreads.getServerConnectClientThread(message.getGetter());
                    //获得输出流，发送至客户端B
                    ObjectOutputStream oos =
                            new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message);
                }else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    // 发送给所有人
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();

                    while(iterator.hasNext()){
                        // 为每一个用户发送
                        String onLineUserId = iterator.next().toString();

                        if(!onLineUserId.equals(message.getSender())) {
                            // 不发送给自己

                            ObjectOutputStream oos = new ObjectOutputStream(
                                    hm.get(onLineUserId).getSocket().getOutputStream()
                            );
                            oos.writeObject(message);
                        }
                    }

                }else{
                    System.out.println("其他类型的message,暂不处理");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
