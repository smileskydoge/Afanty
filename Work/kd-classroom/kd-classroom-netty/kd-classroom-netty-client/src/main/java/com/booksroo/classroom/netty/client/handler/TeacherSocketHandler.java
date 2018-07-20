package com.booksroo.classroom.netty.client.handler;

/**
 * @author liujianjian
 * @date 2018/6/21 9:24
 */
public class TeacherSocketHandler implements SocketHandler {
    @Override
    public void handleConnect(String data) {
        System.out.println("teacher connected ");
    }

    @Override
    public void handleDisconnect(String data) {
        System.out.println("teacher disconnected, data: " + data);
    }

    @Override
    public void handleData(String data) {
        System.out.println("teacher received data: " + data);
    }

    @Override
    public void handleReConnect(String data) {
        System.out.println("teacher reconnect, data: " + data);
    }

    @Override
    public void handleConnectTimeout(String data) {
        System.out.println("teacher connect timeout, data: " + data);
    }

    @Override
    public void handleConnectError(String data) {
        System.out.println("teacher connect error, data: " + data);
    }
}
