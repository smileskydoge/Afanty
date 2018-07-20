package com.booksroo.classroom.sdk.socket.demo;


import com.booksroo.classroom.netty.client.NettySocketIOClient;
import com.booksroo.classroom.netty.client.TeacherSocketClient;
import com.booksroo.classroom.netty.client.handler.TeacherSocketHandler;
import com.booksroo.classroom.netty.common.bean.SocketData;
import com.booksroo.classroom.netty.common.request.TeacherSocketRequest;

/**
 * @author liujianjian
 * @date 2018/6/20 14:18
 */
public class MainT {

    public static void main(String[] args) throws Exception {
        //clientId: teacher_teacherId_teacherClassId

//        String host = "http://47.98.99.57";//线上
//      String host = "http://47.96.108.32";  //测试
                String host = "http://localhost";
        long teacherId = 10000000, teacherClassId = 10000000, subjectId = 100000;
        TeacherSocketRequest request = new TeacherSocketRequest(host, 9099, teacherId, teacherClassId, subjectId);
        NettySocketIOClient tc = TeacherSocketClient.newInstance(request, new TeacherSocketHandler());
        System.out.println(request.getUrl());

//        while (true) {
//            Thread.sleep(1000L * 30);
//            tc.sendData(new SocketData("teacher", "student", "hi students, i am yours teacher", "hi"));
//        }
    }
}
