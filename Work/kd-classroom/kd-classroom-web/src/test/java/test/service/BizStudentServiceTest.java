package test.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.domain.Student;
import com.booksroo.classroom.common.domain.Subject;
import com.booksroo.classroom.common.query.PackageResourceNoteQuery;
import com.booksroo.classroom.common.query.StudentCollectExercisesQuery;
import com.booksroo.classroom.common.query.StudentCollectQuery;
import com.booksroo.classroom.common.query.StudentQuery;
import com.booksroo.classroom.common.request.HistoryPackageListGetRequest;
//import com.booksroo.classroom.service.BizFileNoteService;
import com.booksroo.classroom.common.request.SubmitExerciseAnswerRequest;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.vo.ExerciseVo;
import com.booksroo.classroom.service.BizStudentCollectService;
import com.booksroo.classroom.service.BizStudentService;
import com.booksroo.classroom.service.BizSubjectService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liujianjian
 * @date 2018/6/4 21:47
 */
public class BizStudentServiceTest extends BaseJunitTest {

    @Autowired
    private BizStudentService bizStudentService;
    @Autowired
    private BizSubjectService bizSubjectService;
    //    @Autowired
//    private BizFileNoteService bizFileNoteService;
    @Autowired
    private BizStudentCollectService bizStudentCollectService;

    @Test
    public void testCollectQues() {
        bizStudentCollectService.collectExercise(10000203, 100020, 2, "fda", 1);
    }

    @Test
    public void testCancelCollectQues() {
        bizStudentCollectService.cancelCollectExercise(10000203, 100020);
    }

    @Test
    public void testCollectedQues() {
        StudentCollectExercisesQuery qry = new StudentCollectExercisesQuery();
        qry.setStudentId(10000203L);
        qry.setPage(1);
        qry.setLimit(10);
//        Object obj = bizStudentService.getCollectExerciseList(qry);
//        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testHistoryQues() {
//        Object obj = bizStudentService.getQuesListByPackage(10000203, 10000246);
//        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testSubmit() throws Exception {
        String submitInfo = "[{'unitTestId':14,'exerciseId':100005,'answer':['A'],'answerResult':'right'}]";
        bizStudentService.submitExerciseAnswer(10000203, BizUtil.jsonStrToList(submitInfo, SubmitExerciseAnswerRequest.class));
    }

    @Test
    public void testSelect() {
        StudentQuery query = new StudentQuery();
        query.limitOne();
        query.setParentPhone("15867133397");
        List<Student> list = bizStudentService.getList(query);
        System.out.print(list);
    }

    @Test
    public void test() throws Exception {
        StudentCollectQuery query = new StudentCollectQuery();
        query.setStudentId(10000000L);
//        query.setNoteId(10000000L);
        bizStudentCollectService.count(query);
    }

    @Test
    public void testCollect() throws Exception {
//        bizStudentService.collectResource(10000000, 10000001, 10000001, 1000);
//        bizStudentService.delCollectResource(10000000, 10000001, 100000);
    }

    @Test
    public void testList() throws Exception {
        Set<Long> rids = new HashSet<>();
        rids.add(10000001L);
        rids.add(10000000L);
//        Map<Long, Boolean> collectMap = bizStudentCollectService.getByStuIdAndRids(10000000L, rids);
//        System.out.println(collectMap);
    }

    @Test
    public void testGetCollectedList() throws Exception {
        StudentCollectQuery query = new StudentCollectQuery();
        query.setStudentId(10000000L);
        query.setSubjectId(100000L);
//        Object obj = bizStudentService.getCollectedList(query);
//        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testGetNotesByPackage() throws Exception {
        PackageResourceNoteQuery query = new PackageResourceNoteQuery();
        query.setPackageClassId(60L);

//        Object obj = bizFileNoteService.getNotesByPackage(query);

//        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testGetHistoryPackageList() throws Exception {
        HistoryPackageListGetRequest req = new HistoryPackageListGetRequest();
        req.setSubjectId(1000L);
        req.setClassId(7L);

        Object obj = bizStudentService.getHistoryPackageList(req);

        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testGetSubjectList() {
        Object obj = bizSubjectService.getSubjectListByClass(7);
        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testLogin() throws Exception {

//        System.out.println(bizStudentService.doLogin("15867133397", "e10adc3949ba59abbe56e057f20f883e"));
    }
}
