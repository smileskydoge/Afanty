package com.booksroo.classroom.common.datasource;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author liujianjian
 * @date 2018/6/1 16:13
 */
public class CustomDruidDataSource extends DruidDataSource {
    @Override
    public void setUsername(String username) {
        try {
            username = ConfigTools.decrypt(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setUsername(username);
    }
}
