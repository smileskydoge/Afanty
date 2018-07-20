package test.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.dao.ClassMapper;
import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.query.ClassQuery;
import com.booksroo.classroom.service.BizClassService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

/**
 * @author liujianjian
 * @date 2018/6/2 18:14
 */
public class BizClassServiceTest extends BaseJunitTest {

    @Autowired
    private BizClassService bizClassService;

    @Autowired
    private ClassMapper classMapper;

    @Test
    public void testAdd() throws Exception{
        ClassDomain domain = new ClassDomain();
        domain.setSchoolId(1L);
        domain.setClassNo((byte) 4);
        domain.setGrade((byte) 7);
        domain.setStudentNum((short)50);
        bizClassService.addClass(domain);
    }

    @Test
    public void testQuery() throws Exception {

        ClassQuery classQuery = new ClassQuery();
        System.out.println(classMapper.count(classQuery));
        System.out.println(classMapper.queryClassList(classQuery));
//        System.out.println(classMapper.queryClassList(classQuery));
        System.out.println(JSON.toJSONString(bizClassService.queryClassPageList(classQuery)));
    }

    @Test
    public void testDelClass() throws Exception{
        System.out.println(bizClassService.deleteClassService(5L));
    }

    @Test
    public void testUpdate() throws Exception {
        ClassDomain classDomain = new ClassDomain();
        classDomain.setId(3L);
        classDomain.setStudentNum((short)88);
        System.out.println(bizClassService.updateClassService(classDomain));
    }

    @Test
    public void testQueryClassBySchool(){
        System.out.println(bizClassService.getClassInfoBySchoolId(1L));
    }


    @Test
    public void testQueryClassByTeacherId(){
//        ClassDomain c = classMapper.queryTeacherClassInfoByTeacherId(7L);
//        System.out.println(c);
    }

    @Test
    public void testQueryClassById(){
        System.out.println(bizClassService.getClassInfoById(7L));
    }

}
