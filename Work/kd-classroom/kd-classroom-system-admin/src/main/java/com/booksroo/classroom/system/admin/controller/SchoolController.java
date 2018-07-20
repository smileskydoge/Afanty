package com.booksroo.classroom.system.admin.controller;

import com.booksroo.classroom.common.domain.School;
import com.booksroo.classroom.common.query.SchoolQuery;
import com.booksroo.classroom.service.BizSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/school")
public class SchoolController extends BaseController {

    @Autowired
    private BizSchoolService bizSchoolService;

    @RequestMapping("/list")
    public Object querySchoolList(SchoolQuery schoolQuery) throws Exception{
        return tableResp(bizSchoolService.getSchoolInfo(schoolQuery));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSchoolInfo(School school) throws Exception {
        if(bizSchoolService.addSchoolService(school))return success();
        return fail("1","添加学校信息失败");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSchoolInfo(Long id) throws Exception {
        if(bizSchoolService.deleteSchoolService(id))return success();
        return fail("1","删除失败");
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSchoolInfo(School school) throws Exception {
        if(bizSchoolService.updateSchoolService(school))return success();
        return fail("1","修改失败");
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public Object selectSchoolList(SchoolQuery schoolQuery) throws Exception {
        return tableResp(bizSchoolService.getSchoolInfo(schoolQuery));
    }

    @RequestMapping("/getAllInfo")
    public Object queryAllSchoolList(){
        return success(bizSchoolService.getAllSchoolInfo());
    }


    @RequestMapping("/querySchoolById")
    public Object querySchoolById(Long schoolId){
        return success(bizSchoolService.getById(schoolId));
    }



}
