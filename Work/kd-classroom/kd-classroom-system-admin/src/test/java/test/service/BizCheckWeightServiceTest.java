package test.service;

import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.domain.Subject;
import com.booksroo.classroom.service.BizCheckWeightService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

public class BizCheckWeightServiceTest extends BaseJunitTest {

    @Autowired
    private BizCheckWeightService bizCheckWeightService;

    @Test
    public void testSchoolName(){
//        System.out.println(bizCheckWeightService.checkSchoolName("杭州市一小学"));
    }

    @Test
    public void testCheckClassNo(){
        ClassDomain classDomain = new ClassDomain();
        classDomain.setSchoolId(16L);
        classDomain.setGrade((byte)2);
        classDomain.setClassNo((byte)1);

        System.out.println(bizCheckWeightService.checkClassNo(classDomain));
    }

    @Test
    public void checkSubjectName(){
        Subject subject = new Subject();
        subject.setSubjectName("语文");
        System.out.println(bizCheckWeightService.checkSubjectName(subject));
    }

    @Test
    public void checkClassTeacher(){
        System.out.println(bizCheckWeightService.checkClassTeacher(10L));
        System.out.println(bizCheckWeightService.checkClassStudent(15L));
    }

    @Test
    public void checkStudentByUnique(){
//        System.out.println(bizCheckWeightService.checkStudentByUnique("1301111111")
//        );
    }

}
