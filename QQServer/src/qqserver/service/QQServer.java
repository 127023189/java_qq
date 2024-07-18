package qqserver.service;


import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 这是服务器，在监听9999，等待客户端的连接，并保持通信
 */
public class QQServer {
    private ServerSocket ss = null;

    // 使用集合模拟数据库，由于HashMap不具备纤尘安全，这里使用线程安全集合
    private static ConcurrentHashMap<String,User> validUsers = new ConcurrentHashMap<>();

    //静态代码块，初始化一次
    static {
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("至尊宝", new User("至尊宝", "123456"));
        validUsers.put("紫霞仙子", new User("紫霞仙子", "123456"));
        validUsers.put("菩提老祖", new User("菩提老祖", "123456"));
    }

    // 验证用户是否有效
    private boolean checkUser(String userId,String passwd){
        User user = validUsers.get(userId);

        if(user == null) return false;

        if(!user.getPasswd().equals(passwd)) return false;

        return true;
    }

    public QQServer(){
        try {
            System.out.println("服务端在9999端口监听...");
            ss = new ServerSocket(9999);

            while(true){  // 持续监听，当和某个客户端建立连接后，会继续进行监听
                Socket socket = ss.accept();  // 获得客户端连接信息

                //得到输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User) ois.readObject();

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); //输出流
                //构建一个Mesage对象，准备回复客户端
                Message message = new Message();

                //验证用户是否有效
                if(checkUser(u.getUserId(),u.getPasswd())){
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED); // 回复登录成功
                    //回复message对象
                    oos.writeObject(message);

                    // 创建一个线程和客户端保持通信
                    ServerConnectClientThread serverConnectClientThread =
                            new ServerConnectClientThread(u.getUserId(), socket);
                    serverConnectClientThread.start(); // 启动线程

                    // 将线程加入集合中
                    ManageClientThreads.addClientThread(u.getUserId(),serverConnectClientThread);

                }else{ // 登录失败
                    System.out.println("登录失败！ 用户 id=" + u.getUserId() + " pwd=" + u.getPasswd());
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL); // 登录失败
                    oos.writeObject(message);
                    //关闭socket
                    socket.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        }
    }
}
