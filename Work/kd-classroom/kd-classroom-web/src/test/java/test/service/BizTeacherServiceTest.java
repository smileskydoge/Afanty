package test.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.domain.PackageClass;
import com.booksroo.classroom.common.domain.PackageResourceNote;
import com.booksroo.classroom.common.domain.Teacher;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.BaseQuery;
import com.booksroo.classroom.common.query.PackageInfoQuery;
import com.booksroo.classroom.common.query.ResourceQuery;
import com.booksroo.classroom.common.request.AppBaseRequest;
import com.booksroo.classroom.common.request.UploadNotesRequest;
import com.booksroo.classroom.common.response.BaseResponse;
import com.booksroo.classroom.common.util.FileUtil;
import com.booksroo.classroom.service.*;
import com.booksroo.classroom.web.controller.app.TeacherController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liujianjian
 * @date 2018/6/3 17:48
 */
public class BizTeacherServiceTest extends BaseJunitTest {

    @Autowired
    private BizTeacherService bizTeacherService;
    @Autowired
    private BizPackageResourceService bizPackageResourceService;
    @Autowired
    private BizPackageResourceNoteService bizPackageResourceNoteService;
    @Autowired
    private BizExerciseService bizExerciseService;
    @Autowired
    private BizStatisticsService bizStatisticsService;
    @Autowired
    private BizStudentExerciseSubmitService bizStudentExerciseSubmitService;
    @Autowired
    private BizPackageExerciseService bizPackageExerciseService;

    @Test
    public void testTeacherShowFlag() {
        bizPackageResourceNoteService.updateTeacherShowFlag(10000235, 10000379, (short) 0);

        Object obj = bizPackageResourceNoteService.getConMapByPCIdAndRIdForTeacher(10000235, 10000379);
        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testGetQuesByTId() throws Exception {
        Object obj = bizTeacherService.getExerciseInfoByTeacherId(10000178L, 1, 10);
        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testStatistics() throws Exception {
//        Object o = bizStatisticsService.getVoListByUnitTestId(10000246L, 16, true);
//        System.out.println(JSON.toJSONString(o));

//        o = bizStudentExerciseSubmitService.countSubmitDetailByUTEId(10000235L, 15);
//        System.out.println(JSON.toJSONString(o));

        Object o = bizStatisticsService.getStuSubmitListByUTE(16, 100019);
        System.out.println(JSON.toJSONString(o));
    }

    @Test
    public void testUnitTest() throws Exception {
        Object o = bizTeacherService.createUnitTest("100005,100006", 10000235L);
        System.out.println(JSON.toJSONString(o));

        o = bizTeacherService.getUnitTestList(10000235L);
        System.out.println(JSON.toJSONString(o));
    }

    @Test
    public void testEnd() {
        long packageClassId = 10000002;
        int currentPPTNo = 3;
        long resourceId = 10000003;
        bizTeacherService.updatePackageStatus(packageClassId, (byte) 2);
        bizPackageResourceService.updateCurrentNo(packageClassId, resourceId, currentPPTNo);
    }

    @Test
    public void testAddNotes() {
        PackageResourceNote prn = new PackageResourceNote();
        prn.setCreateTime(new Date());
        prn.setNoteContent("http://www");
        prn.setNoteName("test.png");
        prn.setChildResourceId(111111L);
        prn.setPackageClassId(111111L);
        prn.setResourceId(111111L);

        bizPackageResourceNoteService.saveOrUpdate(prn);
    }

    @Test
    public void testSelect() {
        Teacher teacher = bizTeacherService.getByMobileNo("15867133397");
        System.out.println(teacher);
    }

    @Test
    public void testUpdatePackageStatus() throws Exception {
        bizTeacherService.updatePackageStatus(10000000L, (byte) 2);
    }

    @Test
    public void testUploadNotesFile() throws Exception {
        List<UploadNotesRequest> list = new ArrayList<>();

        UploadNotesRequest req = new UploadNotesRequest();
        req.setChildResourceId(143L);
        req.setFileName("file-name.png");
        req.setFileUrl("http://hjhj");
        req.setPackageClassId(60L);
        req.setResourceId(62L);
        req.setCreateTime(System.currentTimeMillis());
        list.add(req);

        bizTeacherService.uploadNotesFile(1L, list);
    }

    @Test
    public void testGetPackageList() throws Exception {
        PackageInfoQuery query = new PackageInfoQuery();
        query.setClassId(1L);
        Object obj = bizTeacherService.getPackageListByClass(query);
        System.out.println(JSON.toJSONString(obj));
    }

    @Test
    public void testGetResourceList() throws Exception {

    }

    @Test
    public void testSendToClass() throws Exception {
//        bizTeacherService.sendToClass(1, "1,2", "5,6", "数学第一章");
    }

    @Test
    public void testUploadPPT() throws Exception {
//        bizTeacherService.uploadFile(new FileInputStream(new File("C:\\Users\\LJ\\Desktop\\1.pptx")), "1.pptx", 1, null);
    }

    @Test
    public void testLogin() throws Exception {
//        System.out.println(new Teacher());

//        System.out.println(bizTeacherService.doLogin("15867133397", "e10adc3949ba59abbe56e057f20f883e"));
    }

    @Test
    public void testGetClassList() throws Exception {
//        System.out.println(bizTeacherService.getClassList(1L, false));
//        System.out.println(JSON.toJSONString(bizTeacherService.getClassList(1L, false)));
        System.out.println(JSON.toJSONString(BaseResponse.success(bizTeacherService.getClassList(1L, false))));
    }

//    @Test
//    public void  testQueryExerciseInfoByid() throws Exception {
//        System.out.println(JSON.toJSONString(bizTeacherService.getExerciseInfoByTeacherId(10000006L)));
//    }

    @Test
    public void testDelExerciseOnAll() {
        System.out.println(bizExerciseService.delExerciseOnAll(100004L));
    }

    @Test
    public void testSendExerciseToClass() throws Exception {
        bizTeacherService.sendExerciseToClass(10000006, "10000006,10000139,10000140", "100001,100002,100003", "测试包");
    }

//    @Test
//    public void  testQueryExercise() throws Exception {
//        System.out.println(JSON.toJSONString(bizTeacherService.getExerciseInfoByTCPId(10000006L,10000057L,10000146L)));
//    }

    @Test
    public void testDelExerciseOnCP() throws BizException {
//        System.out.println(bizExerciseService.delExerciseOnCP(10000006L, 10000058L, 10000147L, 100003L));
    }

    @Test
    public void testQueryExerciseBy() throws Exception {
        System.out.println(JSON.toJSONString(bizTeacherService.getExerciseInfoByPackageId(10000006L, 10000145L)));
    }

    @Test
    public void testQueryExerciseByPCId() throws Exception {
        BaseQuery query = new BaseQuery();
        query.setId(10000045L);
        System.out.println(JSON.toJSONString(bizTeacherService.getExerciseInfoByPCId(query)));
    }

    @Test
    public void testCheckPERepe() {
        System.out.println(bizPackageExerciseService.checkExerciseRepe(100005L, 10000249L));
    }

}
