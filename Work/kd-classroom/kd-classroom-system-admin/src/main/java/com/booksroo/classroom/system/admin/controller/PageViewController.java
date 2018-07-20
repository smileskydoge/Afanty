package com.booksroo.classroom.system.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liujianjian
 * @date 2018/6/4 9:22
 */
@Controller
@RequestMapping("/admin")
public class PageViewController {

    @RequestMapping("/student")
    public String toStudentPage() { return "Student"; }

    @RequestMapping("/teacher")
    public String toteacherPage() { return "Teacher"; }

    @RequestMapping("/subject")
    public String toSubjectPage() { return "Subject"; }

    @RequestMapping("/class")
    public String toClassPage() { return "Class"; }

    @RequestMapping("/school")
    public String toSchoolPage() { return "School"; }

    @RequestMapping("/selectStudent")
    public String toSelectStudentPage(){ return "SelectStudent";}

    @RequestMapping("/addStudent")
    public String toAddStudentPage(){ return "AddStudent";}

    @RequestMapping("/addSubject")
    public String toAddSubjectPage(){
        return "AddSubject";
    }

    @RequestMapping("/addTeacher")
    public String toAddTeacherPage(){
        return "AddTeacher";
    }

    @RequestMapping("/addClass")
    public String toAddClassPage(){
        return "AddClass";
    }

    @RequestMapping("/addSchool")
    public String toAddSchoolPage(){
        return "AddSchool";
    }

    @RequestMapping("/editStudent")
    public ModelAndView toEditStudentPage(Integer id){
        return new ModelAndView("EditStudent","id",id);
    }

    @RequestMapping("/editTeacher")
    public ModelAndView toEditTeacherPage(Integer id){
        return new ModelAndView("EditTeacher","id",id);
    }

    @RequestMapping("/editSchool")
    public ModelAndView toEditSchoolPage(Integer id){
        return new ModelAndView("EditSchool","id",id);
    }

    @RequestMapping("/editSubject")
    public ModelAndView toEditSubjectPage(Integer id){
        return new ModelAndView("EditSubject","id",id);
    }

    @RequestMapping("/editClass")
    public ModelAndView toEditClassPage(Integer id){
        return new ModelAndView("EditClass","id",id);
    }

}
