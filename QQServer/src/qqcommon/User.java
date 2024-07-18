package qqcommon;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 2L;

    private String userId;  // 用户id/账号
    private String passwd; // 用户密码

    public User() {
    }

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    /**
     * 获取
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取
     *
     * @return passwd
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * 设置
     *
     * @param passwd
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }


}
