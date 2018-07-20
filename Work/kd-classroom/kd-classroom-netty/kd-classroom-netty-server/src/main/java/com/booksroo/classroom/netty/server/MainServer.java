package com.booksroo.classroom.netty.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liujianjian
 * @date 2018/6/20 12:45
 */
public class MainServer {

    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
        NettySocketIOServer server = (NettySocketIOServer) ac.getBean("nettySocketIOServer");
        ac.start();
        server.createServer("localhost", 9099).start();

        Thread.sleep(1000L*10);
        server.stop();

        Thread.sleep(1000L*10);
        server.createServer("localhost", 9099).start();

    }
}
