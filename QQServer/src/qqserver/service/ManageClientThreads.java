package qqserver.service;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThreads  {
    private static HashMap<String,ServerConnectClientThread> hm = new HashMap<>();

    // 返回HashMap
    public static HashMap<String,ServerConnectClientThread> getHm(){
        return hm;
    }

    // 添加线程对象到hm集合
    public static void addClientThread(String userId,ServerConnectClientThread serverConnectClientThread){
        hm.put(userId,serverConnectClientThread);
    }

    public static ServerConnectClientThread getServerConnectClientThread(String userId){
        return hm.get(userId);
    }

    //移除某对象
    public static void removeServerConnectClientThread(String userId){
        hm.remove(userId);
    }

    // 返回全部在线列表
    public static String getOnlineUser(){
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        while(iterator.hasNext()){
            onlineUserList += iterator.next().toString() + " ";
        }
        return onlineUserList;
    }
}
