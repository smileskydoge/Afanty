package com.booksroo.classroom.system.admin.controller;

import com.booksroo.classroom.common.domain.StatusDomain;
import com.booksroo.classroom.common.domain.Student;
import com.booksroo.classroom.common.query.StudentQuery;
import com.booksroo.classroom.service.BizStudentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liujianjian
 * @date 2018/6/2 23:19
 */
@RestController
@RequestMapping("/admin/student")
public class StudentController extends BaseController {

    @Autowired
    private BizStudentService bizStudentService;

    @RequestMapping("/list")
    public Object queryStudentList(StudentQuery query) throws Exception {
        return tableResp(bizStudentService.getPageList(query));
    }


//    @InitBinder("/add") //注意，这里的date需要与请求中的参数名相同
//    protected void initBinder(WebDataBinder binder){
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
//    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addStudentInfo(Student student) throws Exception {
        if (bizStudentService.addStudentService(student))
            return success();
        return fail("","添加失败");
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteStudentInfo(Long id) throws Exception {
        if(bizStudentService.deleteStudentService(id))return success();
        return fail("1","删除失败");
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateStudentInfo(Student student) throws Exception {
        if(bizStudentService.updateStudentService(student))return success();
        return fail("1","修改失败");
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public Object selectStudentList(StudentQuery studentQuery) throws Exception {
        return tableResp(bizStudentService.getPageList(studentQuery));
    }

    @RequestMapping(value = "/queryStudentById")
    public Object queryStudentById(Long studentId) throws Exception {
        return success(bizStudentService.getVoById(studentId));
    }

    @RequestMapping(value = "/changeStatus")
    public Object changeStudentStatus(StatusDomain statusDomain) throws Exception{
        if (bizStudentService.updateStudetnStatus(statusDomain))return success();
        return fail("-1","修改失败");
    }
}
