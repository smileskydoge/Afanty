package com.booksroo.classroom.netty.client;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.netty.client.handler.StudentSocketHandler;
import com.booksroo.classroom.netty.common.bean.SocketData;
import com.booksroo.classroom.netty.common.request.SocketRequest;

import static com.booksroo.classroom.netty.common.constant.EventKeyConstant.EVENT_DATA_STUDENT;

/**
 * @author liujianjian
 * @date 2018/6/20 16:24
 */
public class StudentSocketClient extends NettySocketIOClient {

    public StudentSocketClient(SocketRequest request, StudentSocketHandler socketHandler) throws Exception {
        super(request, socketHandler);
    }

    @Override
    public void sendData(SocketData data) {
        sendData(JSON.toJSONString(data));
    }

    @Override
    protected void sendData(String data) {
        socket.emit(EVENT_DATA_STUDENT, data);
    }

    @Override
    protected void onEvent() {
        socket.on(EVENT_DATA_STUDENT, new EventListener());
    }

    public static StudentSocketClient newInstance(SocketRequest request, StudentSocketHandler socketHandler) throws Exception {
        return new StudentSocketClient(request, socketHandler);
    }

}
