package com.booksroo.classroom.common.third.service;

import com.booksroo.classroom.common.pojo.PropertyValue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author liujianjian
 * @date 2018/6/22 10:13
 */
@Component("jedisService")
public class JedisService implements InitializingBean {

    private JedisPool pool;

    @Autowired(required = false)
    private PropertyValue propertyValue;

    public String get(String key) {
        if (noUse()) return null;

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } finally {
            closeJedis(jedis);
        }
    }

    public void set(String key, String value, long timeout) {
        if (noUse()) return;

        Jedis jedis = null;
        try {
            jedis = getJedis();
            boolean keyExist = jedis.exists(key);
            // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
            if (keyExist) {
                jedis.del(key);
            }
            jedis.set(key, value, "NX", "EX", timeout);
        } finally {
            closeJedis(jedis);
        }
    }

    public Jedis getJedis() {
        if (noUse()) return null;

        if (pool == null) return null;
        return pool.getResource();
    }

    public void init() {
        if (noUse()) return;

        JedisPoolConfig config = new JedisPoolConfig();
//最大空闲连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
        config.setMaxIdle(200);
//最大连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
        config.setMaxTotal(10000);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        String host = propertyValue.getRedisUrl();
        String password = propertyValue.getRedisPwd();

        pool = new JedisPool(config, host, propertyValue.getRedisPort(), 3000, password);
        System.out.println("redis 服务初始化完成...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public void destroyJedisPool() {
        if (pool != null) pool.destroy();
    }

    private void closeJedis(Jedis jedis) {
        if (jedis == null) return;

        jedis.close();
    }

    private boolean isDev() {
        return propertyValue != null && "dev".equals(propertyValue.getEnv());
    }

    private boolean propertyNull() {
        return propertyValue == null;
    }

    private boolean noUse() {
        return isDev() || propertyNull();
    }
}
