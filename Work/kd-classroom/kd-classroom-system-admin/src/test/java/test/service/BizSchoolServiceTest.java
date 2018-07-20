package test.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.dao.SchoolMapper;
import com.booksroo.classroom.common.domain.School;
import com.booksroo.classroom.common.query.SchoolQuery;
import com.booksroo.classroom.service.BizSchoolService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

public class BizSchoolServiceTest extends BaseJunitTest {

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private BizSchoolService bizSchoolService;

    @Test
    public void testQuerySchoolList(){
        SchoolQuery schoolQuery = new SchoolQuery();

        System.out.println(schoolMapper.count(schoolQuery));

        System.out.println(schoolMapper.querySchoolList(schoolQuery));
    }

    @Test
    public void testAdd() throws Exception{
        School school = new School();
        school.setSchoolName("清华大学");
        school.setEducationLength((byte)1);
        school.setArea("北京");
        System.out.println(bizSchoolService.addSchoolService(school));
    }

    @Test
    public void testUpdate() throws Exception {
        School school = new School();
        school.setId(2L);
        school.setDelFlag(false);
        school.setParentId(2L);
        System.out.println(bizSchoolService.updateSchoolService(school));
    }

    @Test
    public void testDel() throws Exception{
        System.out.println(bizSchoolService.deleteSchoolService(2L));
    }


    @Test
    public void testGetAllInfo(){
        System.out.println(JSON.toJSONString(bizSchoolService.getAllSchoolInfo()));
    }
}
