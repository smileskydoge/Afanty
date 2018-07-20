package test.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.dao.SubjectMapper;
import com.booksroo.classroom.common.domain.Subject;
import com.booksroo.classroom.common.query.SubjectQuery;
import com.booksroo.classroom.common.vo.PageList;
import com.booksroo.classroom.common.vo.SubjectVo;
import com.booksroo.classroom.service.BizSubjectService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

import java.util.List;

public class BizSubjectServiceTest extends BaseJunitTest {

    @Autowired
    private BizSubjectService bizSubjectService;

    @Autowired
    private SubjectMapper subjectMapper;
    @Test
    public void testQueryList() throws Exception{

        SubjectQuery sub = new SubjectQuery();

        PageList<SubjectVo> pl = bizSubjectService.getSubjectInfo(sub);

        System.out.println(JSON.toJSONString(pl));
    }

    @Test
    public void testQueryListById(){
        System.out.println(subjectMapper.selectByPrimaryKey(3L));
    }

    @Test
    public void testAdd() throws Exception {
        Subject subject = new Subject();
        subject.setSubjectName("政治");
        System.out.println(bizSubjectService.addSubjectInfo(subject));
    }

    @Test
    public void testDel() throws Exception {
        System.out.println(bizSubjectService.deleteSubjectInfo(11L));
    }

    @Test
    public void testUpdate() throws Exception {
        Subject subject = new Subject();
        subject.setId(7L);
        subject.setSubjectName("地理");
        subject.setDelFlag(false);

        System.out.println(bizSubjectService.updateSubjectInfo(subject));
    }

    @Test
    public void testSelect() throws Exception{
        SubjectQuery subjectQuery = new SubjectQuery();
        subjectQuery.setSubjectName("地理");
        System.out.println(bizSubjectService.getSubjectInfo(subjectQuery));
    }
}
