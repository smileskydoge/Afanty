package com.booksroo.classroom.netty.client;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.netty.client.handler.SocketHandler;
import com.booksroo.classroom.netty.common.bean.SocketData;
import com.booksroo.classroom.netty.common.request.SocketRequest;

import java.util.List;

import static com.booksroo.classroom.netty.common.constant.EventKeyConstant.EVENT_DATA_TEACHER;

/**
 * @author liujianjian
 * @date 2018/6/20 16:24
 */
public class TeacherSocketClient extends NettySocketIOClient {

    public TeacherSocketClient(SocketRequest request, SocketHandler socketHandler) throws Exception {
        super(request, socketHandler);
    }

    @Override
    public void sendData(SocketData data) {
        sendData(JSON.toJSONString(data));
    }

    @Override
    protected void sendData(String data) {
        socket.emit(EVENT_DATA_TEACHER, data);
    }

    @Override
    public void sendData(List<SocketData> data) {
        sendData(JSON.toJSONString(data));
    }

    @Override
    protected void onEvent() {
        socket.on(EVENT_DATA_TEACHER, new EventListener());
    }

    public static TeacherSocketClient newInstance(SocketRequest request, SocketHandler socketHandler) throws Exception {
        return new TeacherSocketClient(request, socketHandler);
    }
}
