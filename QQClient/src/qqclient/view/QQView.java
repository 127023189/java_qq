package qqclient.view;


import qqclient.service.MessageClientService;
import qqclient.service.UserClientService;
import qqclient.utils.Utility;

import java.util.concurrent.TimeUnit;

public class QQView {
    private Boolean loop = true; // 控制是否显示菜单
    private String key = ""; // 接收键盘信息
    private UserClientService userClientService = new UserClientService(); // 用于用户注册与登录
    private MessageClientService messageClientService = new MessageClientService(); // 用户消息的发送

    public static void main(String[] args) {
        new QQView().mainMenu();
    }

    private void mainMenu() {
        while (loop) {

            System.out.println("===========欢迎登录网络通信系统===========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.println("请输入你的选择: ");

            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.println("请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密 码：");
                    String pwd = Utility.readString(50);

                    //编写一个验证方法
                    if (userClientService.checkUser(userId,pwd)) {
                        System.out.println("===========欢迎 (用户 " + userId + " 登录成功) ===========");
                        //进入到二级菜单
                        while (loop) {
                            try {
                                Thread.currentThread().sleep(10);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("\n=========网络通信系统二级菜单(用户 " + userId + " )=======");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择: ");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("请输入群发的消息");
                                    String s = Utility.readString(50);
                                    messageClientService.sendMessageToAll(s,userId);
                                    // 发送消息
                                    break;
                                case "3":
                                    System.out.println("请输入想聊天的用户（在线）:");
                                    String getterId = Utility.readString(50);
                                    System.out.println("请输入想要说的话：");
                                    String content = Utility.readString(50);
                                    // 编写一个方法，用于发送消息
                                    messageClientService.sendMessageToOne(content,userId,getterId);

                                    //发送信息
//                                    System.out.println("发送的信息为：" + content);
                                    break;
                                case "4":
                                    System.out.println("请输入文件要发送的对象：");
                                    getterId = Utility.readString(50);
                                    System.out.println("请输入要发送的文件路径");
                                    String src = Utility.readString(50);
                                    System.out.println("请输入要发送的终点路径");
                                    String dest = Utility.readString(50);
                                    // 发送文件
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                            }
                        }
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("==========登录失败=========");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }

        }
    }
}
