package com.booksroo.classroom.netty.server.listener;

import com.booksroo.classroom.common.pojo.PropertyValue;
import com.booksroo.classroom.netty.server.NettySocketIOServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author liujianjian
 * @date 2018/6/26 17:02
 */
public class ApplicationLifeListener implements ServletContextListener {

    private NettySocketIOServer server;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
            server = (NettySocketIOServer) ac.getBean("nettySocketIOServer");
            PropertyValue propertyValue = (PropertyValue) ac.getBean("propertyValue");
            ac.start();

            server.createServer(propertyValue.getSocketServerHost(), propertyValue.getSocketServerPort()).start();
            System.out.println("socketServer start ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (server != null && server.getServer() != null) server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
