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

            // 服务端返回message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message mess = (Message) ois.readObject();

            if (mess.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                //创建一个线程类进行持续通信 -》 ClientConnectServerThread
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start(); // 启动线程


                flag = true;
            } else {
                socket.close();  // 失败后，关闭socket
                flag = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        return flag;
    }
}
