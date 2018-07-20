package test.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.dao.TeacherMapper;
import com.booksroo.classroom.common.domain.Teacher;
import com.booksroo.classroom.common.domain.TeacherClass;
import com.booksroo.classroom.common.query.TeacherQuery;
import com.booksroo.classroom.common.vo.TeacherVo;
import com.booksroo.classroom.service.BizTeacherClassService;
import com.booksroo.classroom.service.BizTeacherService;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

import java.util.HashSet;
import java.util.Set;

public class BizTeacherServiceTest extends BaseJunitTest {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private BizTeacherClassService bizTeacherClassService;

    @Autowired
    private BizTeacherService bizTeacherService;


    @Test
    public void testQueryTeacherList() throws Exception{
        TeacherQuery tq = new TeacherQuery();
        tq.setClassIdstr("10000000");

        System.out.println(JSON.toJSONString(bizTeacherService.getTeacherInfo(tq)));
    }

//    @Test
//    public void testAddTeacherInfo() throws Exception{
//
//        Teacher teacher = new Teacher();
//        teacher.setAge((byte)40);
//        teacher.setTeacherName("jack");
//        teacher.setDelFlag(false);
//        teacher.setSubjectId("3");
//        teacher.setSchoolId(1L);
//        teacher.setMobileNo("123455");
//        teacher.setPassword("e10adc3949ba59abbe56e057f20f883e");
//
//        System.out.println(bizTeacherService.addTeacherService(teacher));
//    }


//    @Test
//    public void testUpdate() throws Exception {
//        Teacher teacher = new Teacher();
//        teacher.setId(3L);
//        teacher.setHeadImg("/lkj");
//        teacher.setDelFlag(false);
//        teacher.setTeacherName("张三");
//        System.out.println(bizTeacherService.updateTeacherService(teacher));
//    }

    @Test
    public void testDel() throws Exception {
        System.out.println(bizTeacherService.deleteTeacherService(3L));
    }

    @Test
    public void testUpdateInfo(){
        TeacherClass teacherClass = new TeacherClass();
        teacherClass.setTeacherId(7L);
        teacherClass.setSubjectId("11");
        teacherClass.setClassId(5L);

        bizTeacherClassService.updateTeacherClassInfo(teacherClass);
    }

    @Test
    public void testQueryTeacherInfoById()throws Exception{
        System.out.println(JSON.toJSONString(bizTeacherService.queryTeacherInfoById(7L)));
    }


    @Test
    public void testQueryTeacherClassById()throws Exception{
        Set<Long> set = new HashSet<>();
        set.add(10000000L);
        System.out.println(JSON.toJSONString(bizTeacherClassService.queryTeacherIdsByClass(set)));
    }


}
