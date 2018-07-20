package com.booksroo.classroom.netty.client.handler;

/**
 * @author liujianjian
 * @date 2018/6/21 9:24
 */
public class StudentSocketHandler implements SocketHandler {
    @Override
    public void handleConnect(String data) {
        System.out.println("student connected ");
    }

    @Override
    public void handleReConnect(String data) {
        System.out.println("student reconnect, data: " + data);
    }

    @Override
    public void handleDisconnect(String data) {
        System.out.println("student disconnected, data: " + data);
    }

    @Override
    public void handleData(String data) {
        System.out.println("student received data: " + data);
    }

    @Override
    public void handleConnectTimeout(String data) {
        System.out.println("student connect timeout, data: " + data);
    }

    @Override
    public void handleConnectError(String data) {
        System.out.println("student connect error, data: " + data);
    }
}
