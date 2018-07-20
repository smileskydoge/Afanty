package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.CheckWeightMapper;
import com.booksroo.classroom.common.domain.*;
import com.booksroo.classroom.common.query.TeacherQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bizCheckWeightService")
public class BizCheckWeightService extends BaseService {

    @Autowired
    private CheckWeightMapper checkWeightMapper;

    //校名查重
    public boolean checkSchoolName(School school){
        if(checkWeightMapper.checkSchoolName(school)>0)return false;
        return true;
    }
    //班级查重
    public boolean checkClassNo(ClassDomain classDomain){
        if(checkWeightMapper.checkClassNo(classDomain)>0)return false;
        return true;
    }
    //科目查询
    public boolean checkSubjectName(Subject subject){
        if(checkWeightMapper.checkSubjectName(subject)>0)return false;
        return true;
    }
    //学校子节点查询
    public boolean checkSchoolChildNode(Long schoolId){
        if(checkWeightMapper.checkSchoolChildNode(schoolId)>0)return false;
        return true;
    }
    //班级关联学生查询
    public boolean checkClassStudent(Long classId){
        if (checkWeightMapper.checkClassStudent(classId)>0)return false;
        return true;
    }
    //班级关联教师查询
    public boolean checkClassTeacher(Long classId){
        if (checkWeightMapper.checkClassTeacher(classId)>0)return false;
        return true;
    }
    //学科关联教师查询
    public boolean checkSubjectTeacher(Long subjectId){
        if (checkWeightMapper.checkSubjectTeacher(subjectId)>0)return false;
        return true;
    }

    public boolean checkClassChildNode(Long classId){
        if(checkClassTeacher(classId)&&checkClassStudent(classId))return true;
        return false;
    }

    //学生信息唯一性查询
    public boolean checkStudentByUnique(Student student){
        if(checkWeightMapper.queryStudentInfoByUnique(student)>0)return false;
        return true;
    }

    //教师信息唯一性查询
    public boolean checkTeacherByUnique(Teacher teacher){
        if(checkWeightMapper.queryTeacherInfoByUnique(teacher)>0)return false;
        return true;
    }

    //同一个班级同一个学科只能一个教师
    public boolean checkTeacherClassByUnique(TeacherQuery teacherQuery){
        if(checkWeightMapper.queryTeacherClassByUnique(teacherQuery)>0)return false;
        return true;
    }
}
