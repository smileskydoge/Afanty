package com.booksroo.classroom.netty.common.bean;

/**
 * @author liujianjian
 * @date 2018/6/20 14:32
 */
public class ClientInfo extends BaseBean {
    private String clientId;
    private String userType;
    private String clientIp;
    private Long teacherId;
    private Long studentId;
    private Long teacherClassId;
    private String namespace0;
    private String namespace1;
    private Long subjectId;

    public ClientInfo() {
    }

    public ClientInfo(String userType, Long teacherId, Long studentId, Long teacherClassId, Long subjectId) {
        this.userType = userType;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.teacherClassId = teacherClassId;
        this.subjectId = subjectId;
    }

    public static ClientInfo newInstance(String userType, Long teacherId, Long studentId, Long teacherClassId, Long subjectId) {
        return new ClientInfo(userType, teacherId, studentId, teacherClassId, subjectId);
    }

    public boolean isTeacherClient() {
        return "teacher".equalsIgnoreCase(userType);
    }

    public boolean isStudentClient() {
        return "student".equalsIgnoreCase(userType);
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTeacherClassId() {
        return teacherClassId;
    }

    public void setTeacherClassId(Long teacherClassId) {
        this.teacherClassId = teacherClassId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getNamespace0() {
        return namespace0;
    }

    public void setNamespace0(String namespace0) {
        this.namespace0 = namespace0;
    }

    public String getNamespace1() {
        if (namespace1 != null && !namespace1.trim().equals("")) return namespace1;

        return "/" + clientId;
    }

    public void setNamespace1(String namespace1) {
        this.namespace1 = namespace1;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}
