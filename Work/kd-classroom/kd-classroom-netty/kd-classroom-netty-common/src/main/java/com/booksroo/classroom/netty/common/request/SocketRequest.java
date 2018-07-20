package com.booksroo.classroom.netty.common.request;


/**
 * @author liujianjian
 * @date 2018/6/21 17:15
 */
public abstract class SocketRequest extends BaseRequest {

    private long teacherId;

    private long studentId;

    private long classId;

    private long subjectId;

    private long teacherClassId;

    private String userType;

    public SocketRequest(String host, int port, long teacherId, long teacherClassId, long subjectId) {
        super(host, port);
        this.teacherId = teacherId;
        this.teacherClassId = teacherClassId;
        this.subjectId = subjectId;
    }

    public abstract String getClientId();

    public abstract String getUrl();

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getTeacherClassId() {
        return teacherClassId;
    }

    public void setTeacherClassId(long teacherClassId) {
        this.teacherClassId = teacherClassId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
