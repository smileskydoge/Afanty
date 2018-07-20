package com.booksroo.classroom.system.admin.controller;

import com.booksroo.classroom.common.domain.Subject;
import com.booksroo.classroom.common.query.SubjectQuery;
import com.booksroo.classroom.service.BizSubjectService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/subject")
public class SubjectController extends BaseController {

    @Autowired
    private BizSubjectService bizSubjectService;

    @RequestMapping("/list")
    public Object querySubjectList(SubjectQuery subjectQuery) throws Exception{
        return tableResp(bizSubjectService.getSubjectInfo(subjectQuery));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSubject(Subject subject) throws Exception {
        if (bizSubjectService.addSubjectInfo(subject))
        return success();
        return fail("1","添加失败");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteSubject(Long id) throws Exception {
        if (bizSubjectService.deleteSubjectInfo(id))return success();
        return  fail("1","删除失败");
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSubject(Subject subject) throws Exception {
        if (bizSubjectService.updateSubjectInfo(subject))return success();
        return  fail("1","修改失败");
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public Object selectSubjectList(SubjectQuery subjectQuery) throws Exception {
        return tableResp(bizSubjectService.getSubjectInfo(subjectQuery));
    }

    @RequestMapping(value = "/allSubjectInfo")
    public Object getAllSubjectInfo(){
        return success(bizSubjectService.getAllSubjectList());
    }

    @RequestMapping(value = "/querySubjectById")
    public Object querySubjectById(Long subjectId) throws Exception{
        return success(bizSubjectService.getById(subjectId));
    }
}
