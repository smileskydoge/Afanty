package com.booksroo.classroom.netty.server.task;

import com.booksroo.classroom.common.pojo.PropertyValue;
import com.booksroo.classroom.netty.server.NettySocketIOServer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liujianjian
 * @date 2018/6/27 14:51
 */
@Component
public class ScheduledTask {
    protected final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private NettySocketIOServer server;
    @Autowired
    private PropertyValue propertyValue;

    //每天早上5点打开服务
    @Scheduled(cron = "0 0 5 * * ?")
    public void startServer() {
        try {
            if (server.getServer() == null) {
                server.createServer(propertyValue.getSocketServerHost(), propertyValue.getSocketServerPort());
            }
            server.getServer().start();
            log.info("socketServer start by task....");
        } catch (Exception e) {
            log.error("start socketServer error: " + e.getMessage(), e);
        }
    }

    //每天晚上23点关闭服务
    @Scheduled(cron = "0 0 23 * * ?")
    public void stopServer() {
        try {
            if (server.getServer() == null) return;

            server.getServer().stop();
            log.info("socketServer stop by task....");
        } catch (Exception e) {
            log.error("stop socketServer error: " + e.getMessage(), e);
        }
    }
}
