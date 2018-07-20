package com.booksroo.classroom.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

/**
 * @author liujianjian
 * @date 2018/6/3 10:13
 */
public class BaseService {

    protected final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    protected TaskExecutor taskExecutor;

    @Autowired
    protected TaskExecutor ioTaskExecutor;

    protected boolean isValidId(Long id) {
        return id != null && id > 0;
    }
}
