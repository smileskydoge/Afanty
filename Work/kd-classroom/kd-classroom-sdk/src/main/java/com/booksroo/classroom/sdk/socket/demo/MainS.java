package com.booksroo.classroom.sdk.socket.demo;


import com.booksroo.classroom.netty.client.StudentSocketClient;
import com.booksroo.classroom.netty.client.handler.StudentSocketHandler;
import com.booksroo.classroom.netty.common.bean.SocketData;
import com.booksroo.classroom.netty.common.request.StudentSocketRequest;

/**
 * @author liujianjian
 * @date 2018/6/20 14:18
 */
public class MainS {

    public static void main(String[] args) throws Exception {
        //clientId: student_studentId_teacherClassId_teacherId

        String host = "http://localhost";
//        String host = "http://47.96.108.32";
        long teacherId = 10000000, teacherClassId = 10000000, subjectId = 100000;
        long studentId = 10000000;
        StudentSocketRequest request = new StudentSocketRequest(host, 9099, studentId, teacherId, teacherClassId, subjectId);
        System.out.println(request.getUrl());

        StudentSocketClient sc = StudentSocketClient.newInstance(request, new StudentSocketHandler());

//        while (true) {
//            Thread.sleep(1000L * 30);
//            sc.sendData(new SocketData("f", "t", "con", "ins"));
//        }
    }
}
