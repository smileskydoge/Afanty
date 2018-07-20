package com.booksroo.classroom.system.admin.controller;

import com.booksroo.classroom.common.domain.*;
import com.booksroo.classroom.common.query.TeacherQuery;
import com.booksroo.classroom.service.BizCheckWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/check")
public class CheckWeightController extends BaseController {

    @Autowired
    private BizCheckWeightService bizCheckWeightService;

    @RequestMapping("/schoolName")
    public Object checkSchoolName(School school){
        if (bizCheckWeightService.checkSchoolName(school))return success();
        return fail("-1",school.getSchoolName()+"-该学校已存在");
    }

    @RequestMapping("/class")
    public Object checkClassNo(ClassDomain classDomain){
        if (bizCheckWeightService.checkClassNo(classDomain))return success();
        return fail("-1",classDomain.getGrade()+"年级"+classDomain.getClassNo()+"班在该学校已存在");
    }


    @RequestMapping("/subject")
    public Object checkSubjectName(Subject subject){
        if(bizCheckWeightService.checkSubjectName(subject))return success();
        return fail("-1",subject.getSubjectName()+"已存在");
    }

    @RequestMapping("/schoolChild")
    public Object checkSchoolChildNode(Long schoolId){
        if (bizCheckWeightService.checkSchoolChildNode(schoolId))return success();
        return fail("-1","请先解除该学校子节点信息");
    }


    @RequestMapping("/classAndStudent")
    public Object checkClassStudent(Long classId){
        if (bizCheckWeightService.checkClassStudent(classId))return success();
        return fail("-1","该班级有关联学生");
    }

    @RequestMapping("/classAndTeacher")
    public Object checkClassTeacher(Long classId){
        if (bizCheckWeightService.checkClassTeacher(classId)) return success();
        return fail("-1","该班级有关联教师");
    }

    @RequestMapping("/classChildNode")
    public Object checkClassChildNode(Long classId){
        if(bizCheckWeightService.checkClassChildNode(classId))return success();
        return fail("-1","该班级有关联子集");
    }

    @RequestMapping("/subjectAndTeacher")
    public Object checkSubjectTeacher(Long subjectId){
        if (bizCheckWeightService.checkSubjectTeacher(subjectId))return success();
        return fail("-1","该学科有关联教师");
    }

    @RequestMapping("/student")
    public Object checkStudentByUnique(Student student){
        if (bizCheckWeightService.checkStudentByUnique(student))return success();
        return fail("-1","该手机号码已存在");
    }

    @RequestMapping("/teacher")
    public Object checkTeacherByUnique(Teacher teacher){
        if (bizCheckWeightService.checkTeacherByUnique(teacher))return success();
        return fail("-1","该手机号码已存在");
    }

    @RequestMapping("/teacherClass")
    public Object checkTeacherClassByUnique(TeacherQuery teacherQuery){
        if (bizCheckWeightService.checkTeacherClassByUnique(teacherQuery))return success();
        return fail("-1","该学科教师在选择班级已存在");
    }
}
