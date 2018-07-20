package com.booksroo.classroom.system.admin.controller;

import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.query.ClassQuery;
import com.booksroo.classroom.service.BizClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/class")
public class ClassController extends BaseController {

    @Autowired
    private BizClassService bizClassService;

    @RequestMapping("/list")
    public Object queryClassInfo(ClassQuery classQuery) throws Exception {
        return tableResp(bizClassService.queryClassPageList(classQuery));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addClassInfo(ClassDomain classDomain) throws Exception {
        if(bizClassService.addClassService(classDomain))return success();
        return fail("","添加失败");
    }


    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteClassInfo(Long id) throws Exception {
        if(bizClassService.deleteClassService(id)) return success();
        return fail("1","删除失败");
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateClassInfo(ClassDomain classDomain) throws Exception {
        if(bizClassService.updateClassService(classDomain))return success();
        return fail("1","修改失败");
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public Object selectClassList(ClassQuery classQuery) throws Exception {
        return tableResp(bizClassService.queryClassPageList(classQuery));
    }

    @RequestMapping("/getClassInfoBySchoolId")
    public Object queryClassBySchool(Long schoolId) {
        return success(bizClassService.getClassInfoBySchoolId(schoolId));
    }

    @RequestMapping("/queryClassById")
    public Object queryClassById(Long classId){
        return success(bizClassService.getClassInfoById(classId));
    }

}
