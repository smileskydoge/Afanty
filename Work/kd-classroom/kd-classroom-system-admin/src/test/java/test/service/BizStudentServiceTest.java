package test.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.dao.StudentMapper;
import com.booksroo.classroom.common.domain.Student;
import com.booksroo.classroom.common.query.StudentQuery;
import com.booksroo.classroom.service.BizStudentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

import java.util.Date;

/**
 * @author liujianjian
 * @date 2018/6/3 11:11
 */
public class BizStudentServiceTest extends BaseJunitTest {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private BizStudentService bizStudentService;

    @Test
    public void testPageList() throws Exception {
        StudentQuery query = new StudentQuery();
        query.setOrderByStr("id desc");
        System.out.println(JSON.toJSONString(bizStudentService.getPageList(query)));
    }

    @Test
    public void testAdd() throws Exception{
        Student s = new Student();

        s.setClassId(3L);
        s.setSchoolId(1L);
        s.setStudentNo("107");
        s.setStudentName("提莫");
        s.setPassword("e10adc3949ba59abbe56e057f20f883e");
        s.setAge(Byte.parseByte("18"));
        s.setParentPhone("123");
        s.setBeginTime(new Date());

        System.out.println(bizStudentService.addStudentService(s));

    }

    @Test
    public void testUpdate() throws Exception{
        Student student = new Student();
        student.setId(4L);
        student.setAge((byte)14);
        student.setDelFlag(false);
        System.out.println(bizStudentService.updateStudentService(student));
    }

    @Test
    public void testDel() throws Exception {
        System.out.println(bizStudentService.deleteStudentService(4L));
    }

    public void testGet(){
//        System.out.println(JSON.toJSONString(bizStudentService.get));
    }
}
