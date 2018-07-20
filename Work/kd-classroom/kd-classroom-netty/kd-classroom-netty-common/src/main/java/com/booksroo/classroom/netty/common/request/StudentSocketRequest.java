package com.booksroo.classroom.netty.common.request;


/**
 * @author liujianjian
 * @date 2018/6/21 17:15
 */
public class StudentSocketRequest extends SocketRequest {

    public StudentSocketRequest(String host, int port, long studentId, long teacherId, long teacherClassId, long subjectId) {
        super(host, port, teacherId, teacherClassId, subjectId);
        super.setStudentId(studentId);
        super.setUserType("student");
    }

    @Override
    public String getClientId() {
        return getStudentId() + "_" + getTeacherId() + "_" + getTeacherClassId() + "_" + super.getSubjectId() + "_" + getUserType();
    }

    @Override
    public String getUrl() {
        String namespace0 = this.getNamespace0();
        return super.getHost() + ":" + super.getPort() + namespace0 + "?clientId=" + getClientId() + "&namespace1=" + this.getNamespace1() + "&namespace0=" + namespace0;
    }

    @Override
    public String getNamespace0() {
        if (super.getNamespace0() == null || super.getNamespace0().equals("")) {
            return "/0_" + super.getTeacherId() + "_" + super.getTeacherClassId() + "_" + super.getSubjectId() + "_teacher";
        }
        return super.getNamespace0();
    }

    @Override
    public String getNamespace1() {
        return super.getNamespace1();
    }
}
