package qqclient.service;

import qqclient.service.ClientConnectServerThread;

import java.util.HashMap;

/**
 *
 * 负责管理客户端的线程的类
 */

public class ManageClientConnectServerThread {
    private static HashMap<String,ClientConnectServerThread> hm = new HashMap<>();


    //添加到集合里
    public static void addClientServerThread(String userId, ClientConnectServerThread serverConnectClientThread){
        hm.put(userId,serverConnectClientThread);
    }

    // 获取线程信息·  给vbjlpn
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return hm.get(userId);
    }
}
