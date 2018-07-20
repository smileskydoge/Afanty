package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.*;
import com.booksroo.classroom.common.query.TeacherQuery;

/**
 *  查重
 */
public interface CheckWeightMapper {

    //校名查重
    int checkSchoolName(School school);
    //班级查重
    int checkClassNo(ClassDomain classDomain);
    //科目查询
    int checkSubjectName(Subject subject);


    //学校子节点查询
    int checkSchoolChildNode(Long schoolId);
    //班级关联学生查询
    int checkClassStudent(Long classId);
    //班级关联教师查询
    int checkClassTeacher(Long classId);
    //学科关联教师查询
    int checkSubjectTeacher(Long subjectId);
    //学生表唯一账号查询
    int queryStudentInfoByUnique(Student student);
    //教师表唯一账号查询
    int queryTeacherInfoByUnique(Teacher teacher);

    int queryTeacherClassByUnique(TeacherQuery teacherQuery);
}
