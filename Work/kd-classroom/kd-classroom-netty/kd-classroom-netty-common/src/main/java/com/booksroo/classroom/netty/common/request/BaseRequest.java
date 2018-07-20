package com.booksroo.classroom.netty.common.request;

import java.io.Serializable;

/**
 * @author liujianjian
 * @date 2018/6/21 17:34
 */
public class BaseRequest implements Serializable {

    private String host = "";

    private int port;

    private String namespace0 = "";
    private String namespace1 = "";

    public BaseRequest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getNamespace0() {
        return namespace0;
    }

    public void setNamespace0(String namespace0) {
        this.namespace0 = namespace0;
    }

    public String getNamespace1() {
        return namespace1;
    }

    public void setNamespace1(String namespace1) {
        this.namespace1 = namespace1;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
