package test.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.domain.Student;
import com.booksroo.classroom.service.BizClassExcelService;
import com.booksroo.classroom.service.BizStudentExcelService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

import java.io.File;
import java.util.List;

public class BizFileServiceTest extends BaseJunitTest {

    @Autowired
    private BizStudentExcelService bizStudentExcelService;

    @Autowired
    private BizClassExcelService bizClassExcelService;

    @Test
    public void testBizStudentExcelService() throws Exception{
//        bizStudentExcelService.initStudentExcel("D:\\student.xlsx");
    }


    @Test
    public void testInsert() throws Exception{

        File file = new File("D:\\student.xlsx");
        bizStudentExcelService.insertStudentsInfo(file);
    }

    @Test
    public void testDown(){
    }
}
