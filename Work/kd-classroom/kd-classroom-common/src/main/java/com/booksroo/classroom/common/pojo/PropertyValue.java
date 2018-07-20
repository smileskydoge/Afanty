package com.booksroo.classroom.common.pojo;

/**
 * 根据properties文件设置一些值
 *
 * @author liujianjian
 * @date 2018/6/21 14:49
 */
public class PropertyValue {

    private String env;//当前启动环境
    private String socketServerHost;
    private Integer socketServerPort;
    private String redisUrl;
    private String redisPwd;
    private Integer redisPort;

    public int getSocketServerPort() {
        return socketServerPort;
    }

    public String getSocketServerHost() {
        return socketServerHost;
    }

    public void setSocketServerHost(String socketServerHost) {
        this.socketServerHost = socketServerHost;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setSocketServerPort(Integer socketServerPort) {
        this.socketServerPort = socketServerPort;
    }

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public String getRedisPwd() {
        return redisPwd;
    }

    public void setRedisPwd(String redisPwd) {
        this.redisPwd = redisPwd;
    }

    public Integer getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(Integer redisPort) {
        this.redisPort = redisPort;
    }
}
