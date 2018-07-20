package com.booksroo.classroom.netty.common.request;


/**
 * @author liujianjian
 * @date 2018/6/21 17:15
 */
public class TeacherSocketRequest extends SocketRequest {

    public TeacherSocketRequest(String host, int port, long teacherId, long teacherClassId, long subjectId) {
        super(host, port, teacherId, teacherClassId, subjectId);
        super.setUserType("teacher");
    }

    @Override
    public String getClientId() {
        return "0_" + super.getTeacherId() + "_" + super.getTeacherClassId() + "_" + super.getSubjectId() + "_" + super.getUserType();
    }

    @Override
    public String getUrl() {
        String namespace0 = this.getNamespace0();
        return super.getHost() + ":" + super.getPort() + namespace0 + "?clientId=" + getClientId() + "&namespace1=" + this.getNamespace1() + "&namespace0=" + namespace0;
    }

    @Override
    public String getNamespace0() {
        return super.getNamespace0() == null ? "" : super.getNamespace0();
    }

    @Override
    public String getNamespace1() {
        if (super.getNamespace1() == null || super.getNamespace1().equals("")) {
            return "/" + this.getClientId();
        }
        return super.getNamespace1();
    }
}
