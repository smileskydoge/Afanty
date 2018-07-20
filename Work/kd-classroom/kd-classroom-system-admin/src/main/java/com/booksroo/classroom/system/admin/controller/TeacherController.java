package com.booksroo.classroom.system.admin.controller;

import com.booksroo.classroom.common.domain.StatusDomain;
import com.booksroo.classroom.common.domain.Teacher;
import com.booksroo.classroom.common.query.TeacherQuery;
import com.booksroo.classroom.common.vo.TeacherAddVo;
import com.booksroo.classroom.service.BizTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/teacher/")
public class TeacherController extends BaseController {

    @Autowired
    private BizTeacherService bizTeacherService;

    @RequestMapping("/list")
    public Object queryTeacherInfo(TeacherQuery teacherQuery) throws Exception {
        return tableResp(bizTeacherService.getTeacherInfo(teacherQuery));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addTeacherInfo(TeacherAddVo teacherAddVo) throws Exception {
        if(bizTeacherService.addTeacherInfoService(teacherAddVo))return success();
        return fail("","添加失败");
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteTeacherInfo(Long id) throws Exception {
        if (bizTeacherService.deleteTeacherService(id))return success();
        return fail("1","删除失败");
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateTeacherInfo(TeacherAddVo teacherAddVo) throws Exception {
        if (bizTeacherService.updateTeacherInfoService(teacherAddVo))return success();
        return fail("1","修改失败");
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public Object selectTeacherList(TeacherQuery teacherQuery) throws Exception {
        return tableResp(bizTeacherService.getTeacherInfo(teacherQuery));
    }

    @RequestMapping(value = "/queryById")
    public Object queryTeacherInfoById(Long teacherId) throws Exception{
        return success(bizTeacherService.queryTeacherInfoById(teacherId));
    }

    @RequestMapping(value = "/changeStatus")
    public Object changeStudentStatus(StatusDomain statusDomain) throws Exception{
        if (bizTeacherService.updateTeacherStatus(statusDomain))return success();
        return fail("-1","修改失败");
    }

}
