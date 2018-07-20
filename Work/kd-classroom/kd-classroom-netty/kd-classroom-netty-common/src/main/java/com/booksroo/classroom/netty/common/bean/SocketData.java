package com.booksroo.classroom.netty.common.bean;

import com.alibaba.fastjson.JSON;

/**
 * @author liujianjian
 * @date 2018/6/15 13:35
 */
public class SocketData extends BaseBean {
    private String from;
    private String to;
    private String content;//数据内容
    private String instruction;//指令集合

    public SocketData() {
    }

    public SocketData(String from, String to, String content, String instruction) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.instruction = instruction;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
