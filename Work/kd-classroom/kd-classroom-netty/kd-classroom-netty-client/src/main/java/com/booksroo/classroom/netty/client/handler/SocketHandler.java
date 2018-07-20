package com.booksroo.classroom.netty.client.handler;

/**
 * @author liujianjian
 * @date 2018/6/21 9:21
 */
public interface SocketHandler {

    /**
     * 连接成功时处理
     */
    void handleConnect(String data);

    /**
     * 重连成功时处理
     *
     * @param data
     */
    void handleReConnect(String data);

    /**
     * 断开连接时处理
     *
     * @param data
     */
    void handleDisconnect(String data);

    /**
     * 接收到数据时处理
     *
     * @param data
     */
    void handleData(String data);

    /**
     * 连接超时处理
     *
     * @param data
     */
    void handleConnectTimeout(String data);

    /**
     * 连接异常处理
     * @param data
     */
    void handleConnectError(String data);
}
