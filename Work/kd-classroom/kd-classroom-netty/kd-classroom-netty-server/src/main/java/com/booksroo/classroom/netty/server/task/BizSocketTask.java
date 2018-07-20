package com.booksroo.classroom.netty.server.task;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author liujianjian
 * @date 2018/6/25 20:32
 */
@Component("bizSocketTask")
public class BizSocketTask implements InitializingBean {

    private ThreadPoolExecutor taskExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        taskExecutor = new ThreadPoolExecutor(10, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(500000));
    }

    public void execute(Runnable task) {
        taskExecutor.execute(task);
    }
}
