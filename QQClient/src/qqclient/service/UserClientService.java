package qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 该类完成用户登录验证和用户注册等功能
 */

public class UserClientService {

    //方便使用
    private User u = new User();
    private Socket socket;

    // 根据id与pwd到服务器验证是否合法
    public boolean checkUser(String userId, String pwd) {
        boolean flag = false;
        u.setUserId(userId);
        u.setPasswd(pwd);

        //连接服务器
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            //得到ObjectOutputZStream对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            // 服务端返回message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message mess = (Message) ois.readObject();

            if (mess.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                //创建一个线程类进行持续通信 -》 ClientConnectServerThread
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.setDaemon(true);  // 设置成守护进程
                ccst.start(); // 启动线程


                //为了方便管理，设计一个集合，管理每一个用户的线程
                ManageClientConnectServerThread.addClientServerThread(userId,ccst);
                flag = true;
            } else {
                socket.close();  // 失败后，关闭socket
                flag = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 向服务器端请求
    public void onlineFriendList(){
        // 发送一个message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId()); // 发送的对象

        //发送给服务端
        // 应该得到当前线程的Socket 对应的ObjectOutputStream

        try {
            // 通过管理线程的集合中，通过userId得到线程对象
            ClientConnectServerThread clientConnectServerThread =
                    ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId());
            // 得到socket
            Socket socket = clientConnectServerThread.getSocket();

            // 得到ObjectOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // 发送给服务端，要求服务端返回在线用户列表
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
