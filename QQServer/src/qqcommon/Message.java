package qqcommon;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;  //发送者
    private String getter; // 接收者
    private String content; // 消息的内容
    private String sendTime; // 发送的时间
    private String mesType; // 消息的类型


    // 文件信息
    private byte[] fileBytes;
    private int fileLen = 0; // 文件的长度
    private String dest; // 文件终点
    private String src; // 源文件路径


    /**
     * 获取
     *
     * @return sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * 设置
     *
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * 获取
     *
     * @return getter
     */
    public String getGetter() {
        return getter;
    }

    /**
     * 设置
     *
     * @param getter
     */
    public void setGetter(String getter) {
        this.getter = getter;
    }

    /**
     * 获取
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取
     *
     * @return sendTime
     */
    public String getSendTime() {
        return sendTime;
    }

    /**
     * 设置
     *
     * @param sendTime
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取
     *
     * @return mesType
     */
    public String getMesType() {
        return mesType;
    }

    /**
     * 设置
     *
     * @param mesType
     */
    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    /**
     * 获取
     *
     * @return fileBytes
     */
    public byte[] getFileBytes() {
        return fileBytes;
    }

    /**
     * 设置
     *
     * @param fileBytes
     */
    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    /**
     * 获取
     *
     * @return fileLen
     */
    public int getFileLen() {
        return fileLen;
    }

    /**
     * 设置
     *
     * @param fileLen
     */
    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    /**
     * 获取
     *
     * @return dest
     */
    public String getDest() {
        return dest;
    }

    /**
     * 设置
     *
     * @param dest
     */
    public void setDest(String dest) {
        this.dest = dest;
    }

    /**
     * 获取
     *
     * @return src
     */
    public String getSrc() {
        return src;
    }

    /**
     * 设置
     *
     * @param src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    public String toString() {
        return "Message{serialVersionUID = " + serialVersionUID + ", sender = " + sender + ", getter = " + getter + ", content = " + content + ", sendTime = " + sendTime + ", mesType = " + mesType + ", fileBytes = " + fileBytes + ", fileLen = " + fileLen + ", dest = " + dest + ", src = " + src + "}";
    }
}
