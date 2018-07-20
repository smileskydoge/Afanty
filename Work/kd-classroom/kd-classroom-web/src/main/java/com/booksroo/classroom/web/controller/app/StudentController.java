package com.booksroo.classroom.web.controller.app;

import com.booksroo.classroom.common.query.PackageResourceNoteQuery;
import com.booksroo.classroom.common.query.StudentCollectExercisesQuery;
import com.booksroo.classroom.common.query.StudentCollectQuery;
import com.booksroo.classroom.common.request.BaseRequest;
import com.booksroo.classroom.common.request.HistoryPackageListGetRequest;
import com.booksroo.classroom.common.request.SubmitExerciseAnswerRequest;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.ParamCheckUtil;
import com.booksroo.classroom.common.vo.*;
import com.booksroo.classroom.service.BizStudentCollectService;
import com.booksroo.classroom.service.BizStudentService;
import com.booksroo.classroom.service.BizSubjectService;
import com.booksroo.classroom.web.controller.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author liujianjian
 * @date 2018/6/4 17:08
 */
@RequestMapping("/app/student")
@RestController("appStudentController")
public class StudentController extends BaseController {

    @Autowired
    private BizStudentService bizStudentService;
    @Autowired
    private BizSubjectService bizSubjectService;
    @Autowired
    private BizStudentCollectService bizStudentCollectService;

    //修改密码
    @RequestMapping("/updatePWD")
    public Object updatePWD(HttpServletRequest request, String oldPassword, String newPassword) throws Exception {
        bizStudentService.updatePwd(getLoginStudent(request), oldPassword, newPassword);
        return success();
    }

    //学科列表
    @RequestMapping("/getSubjectList")
    public Object getSubjectList(HttpServletRequest request) throws Exception {
        StudentVo user = getLoginStudent(request);
        //给学科排序 语文 数学 英语 物理 化学 生物 政治 历史 地理 信息
        List<SubjectVo> voList = bizSubjectService.getSubjectList(user.getClassId());
        if (CollectionUtils.isNotEmpty(voList)) {
            voList.sort(new Comparator<SubjectVo>() {
                @Override
                public int compare(SubjectVo o1, SubjectVo o2) {
                    return o1.getOrderNo() - o2.getOrderNo();
                }
            });
        }
        return success(voList);
    }

    //正在上课中的学科
    @RequestMapping("/getOnClassSubjects")
    public Object getOnClassSubjects(String tcSubjectIds) throws Exception {
        //获取正在上课中的学科
        Set<Long> sids = bizSubjectService.getOnClassSubjects(BizUtil.strToArr(tcSubjectIds));
        if (CollectionUtils.isEmpty(sids)) return success();
        return success(PageList.newInstance(sids.size(), new ArrayList<>(sids), 1));
    }

    //历史资源包列表
    @RequestMapping("/getHistoryPackageList")
    public Object getHistoryPackageList(HttpServletRequest request, HistoryPackageListGetRequest req) throws Exception {
        StudentVo user = getLoginStudent(request);
        req.setClassId(user.getClassId());

        PageList<PackageVo> pageList = bizStudentService.getHistoryPackageList(req);
        BizUtil.orderPackageList(pageList.getRows());
        return success(pageList);
    }

    //历史资源列表
    @RequestMapping("/getHistoryResourceList")
    public Object getHistoryResourceList(HttpServletRequest request, PackageResourceNoteQuery query) throws Exception {
        query.setStudentId(getLoginStudent(request).getId());
        List<FileResourceVo> list = bizStudentService.getNotesList(query);
        if (CollectionUtils.isEmpty(list)) return success(PageList.newInstance());
        BizUtil.replaceOSSHost(list);
        return success(PageList.newInstance(list.size(), list, query.getPage()));
    }

    //历史习题列表
    @RequestMapping("/getHistoryExerciseList")
    public Object getHistoryExerciseList(HttpServletRequest request, long packageClassId) throws Exception {
        List<UnitTestVo> list = bizStudentService.getQuesListByPackage(getLoginStudent(request).getId(), packageClassId, null);
        if (CollectionUtils.isEmpty(list)) return success(PageList.newInstance());
        return success(PageList.newInstance(list.size(), list, 1));
    }

    //收藏资源
    @RequestMapping("/doCollect")
    public Object doCollect(HttpServletRequest request, long packageClassId, long resourceId, long subjectId) throws Exception {
        bizStudentService.collectResource(getLoginStudent(request).getId(), packageClassId, resourceId, subjectId);
        return success();
    }

    //收藏列表
    @RequestMapping("/getCollectResourceList")
    public Object getCollectResourceList(HttpServletRequest request, BaseRequest req, StudentCollectQuery query) throws Exception {
        StudentVo user = getLoginStudent(request);
        query.setStudentId(user.getId());
        query.setPage(req.getPageNo());
        query.setLimit(req.getPageSize());
        PageList<FileResourceVo> pageList = bizStudentService.getCollectedList(query);
        BizUtil.replaceOSSHost(pageList.getRows());
        return success(pageList);
    }

    //取消收藏
    @RequestMapping("/cancelCollect")
    public Object cancelCollect(HttpServletRequest request, long packageClassId, long resourceId, long subjectId) throws Exception {
        bizStudentService.delCollectResource(getLoginStudent(request).getId(), packageClassId, resourceId, subjectId);
        return success();
    }

    //收藏习题
    @RequestMapping("/collectExercises")
    public Object collectExercises(HttpServletRequest request, long exerciseId, long subjectId, String studentAnswer, Integer answerResult) throws Exception {
        StudentVo student = getLoginStudent(request);
        if (bizStudentCollectService.collectExercise(student.getId(), exerciseId, subjectId, studentAnswer, answerResult))
            return success();
        return fail("", "collect fail");
    }

    //取消收藏
    @RequestMapping("/cancelCollectExercises")
    public Object cancelCollectExercises(HttpServletRequest request, long exerciseId) throws Exception {
        StudentVo student = getLoginStudent(request);
        if (bizStudentCollectService.cancelCollectExercise(student.getId(), exerciseId)) return success();
        return fail("-1", "cancel fail");
    }

    //查看收藏的习题列表
    @RequestMapping("/getCollectExerciseList")
    public Object getCollectExerciseList(HttpServletRequest request, BaseRequest req, StudentCollectExercisesQuery qry) throws Exception {
        StudentVo user = getLoginStudent(request);
        qry.setStudentId(user.getId());
        qry.setPage(req.getPageNo());
        qry.setLimit(req.getPageSize());
        return success(bizStudentService.getCollectExerciseList(qry, null));
    }

    //课堂上提交习题答案
    @RequestMapping("/sendExerciseAnswer")
    public Object sendExerciseAnswer(HttpServletRequest request, String submitInfo) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(submitInfo);
        submitInfo = BizUtil.decodeUrlJsonArr(submitInfo);

        StudentVo student = getLoginStudent(request);
        bizStudentService.submitExerciseAnswer(student.getId(), BizUtil.jsonStrToList(submitInfo, SubmitExerciseAnswerRequest.class));
        return success();
    }
}
