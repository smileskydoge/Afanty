package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.StudentMapper;
import com.booksroo.classroom.common.domain.*;
import com.booksroo.classroom.common.enums.PackageClassStatusEnum;
import com.booksroo.classroom.common.enums.PlatformEnum;
import com.booksroo.classroom.common.enums.StudentExercisesEnum;
import com.booksroo.classroom.common.enums.UserEnum;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.*;
import com.booksroo.classroom.common.request.HistoryPackageListGetRequest;
import com.booksroo.classroom.common.request.SubmitExerciseAnswerRequest;
import com.booksroo.classroom.common.util.BeanUtilsExt;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.ParamCheckUtil;
import com.booksroo.classroom.common.vo.*;
import com.booksroo.classroom.service.interf.CacheService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static com.booksroo.classroom.common.constant.BizConstant.UPDATE_TOKEN_WAY_DO_LOGIN;
import static com.booksroo.classroom.common.constant.BizConstant.UPDATE_TOKEN_WAY_UPDATE_PWD;
import static com.booksroo.classroom.common.constant.PromptConstant.*;

/**
 * @author liujianjian
 * @date 2018/6/3 10:13
 */
@Service("bizStudentService")
public class BizStudentService extends BaseService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private BizSchoolService bizSchoolService;
    @Autowired
    private BizClassService bizClassService;
    @Autowired
    private BizUserActionDataService bizUserActionDataService;
    @Autowired
    private BizPackageClassService bizPackageClassService;
    @Autowired
    private BizPackageInfoService bizPackageInfoService;
    @Autowired
    private BizTeacherClassService bizTeacherClassService;
    @Autowired
    private BizStudentCollectService bizStudentCollectService;
    @Autowired
    private BizPackageResourceNoteService bizPackageResourceNoteService;
    //    @Autowired
//    private BizFileNoteService bizFileNoteService;
    @Autowired
    private CacheService redisCacheService;
    @Autowired
    private BizFileResourceService bizFileResourceService;
    @Autowired
    private BizFileResourceChildService bizFileResourceChildService;
    @Autowired
    private BizStudentExerciseSubmitService bizStudentExerciseSubmitService;
    @Autowired
    private BizUnitTestExerciseService bizUnitTestExerciseService;
    @Autowired
    private BizUnitTestService bizUnitTestService;
    @Autowired
    private BizPackageExerciseService bizPackageExerciseService;
    @Autowired
    private BizExerciseService bizExerciseService;
    @Autowired
    private BizStudentCollectExerciseService bizStudentCollectExerciseService;

    public StudentVo doLogin(String parentMobileNo, String password, int platform, String deviceNo) throws Exception {

        ParamCheckUtil.checkNotEmptyStr(parentMobileNo, password);
        Student student = getOneByMobile(parentMobileNo);
        if (student == null)
            throw new BizException(MOBILE_NUMBER_NOT_EXIST, "手机号不存在");

        if (!student.getPassword().equals(password)) throw new BizException(PASSWORD_INCORRECT, "密码不正确");

        StudentVo vo = new StudentVo();
        BeanUtilsExt.propertyUtilsCopy(vo, student);
        School school = bizSchoolService.getById(student.getSchoolId());

        if (school != null) {
            vo.setSchoolName(school.getSchoolName());
        }
        ClassDomain classDomain = bizClassService.getById(student.getClassId());
        if (classDomain != null) {
            vo.setGrade(classDomain.getGrade());
            vo.setClassNo(classDomain.getClassNo());
        }
        vo.setUserType(UserEnum.STUDENT.getType());

        vo.setToken(redisCacheService.getStudentToken(vo.getId()));
        if (PlatformEnum.isMobile(platform)) {
            vo.setToken(BizUtil.genToken(vo));
            redisCacheService.setStudentToken(vo.getId(), vo.getToken(), UPDATE_TOKEN_WAY_DO_LOGIN);
        }
        if (StringUtils.isBlank(vo.getToken())) {
            vo.setToken(BizUtil.genToken(vo));
            redisCacheService.setStudentToken(vo.getId(), vo.getToken(), UPDATE_TOKEN_WAY_DO_LOGIN);
        }
        vo.setFirstLoginTime(bizUserActionDataService.getSFirstLoginTime(vo.getId(), platform));
        //更新登录时间,暂时就取第一个吧
        bizUserActionDataService.updateStudentLoginTime(vo.getId(), new Timestamp(System.currentTimeMillis()), platform);
        return vo;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void updatePwd(StudentVo user, String oldPwd, String newPwd) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(oldPwd, newPwd);
        oldPwd = oldPwd.trim();
        newPwd = newPwd.trim();
        if (StringUtils.equalsIgnoreCase(oldPwd, newPwd)) return;

        Student student = getById(user.getId());
        if (student == null) throw new BizException(USER_INFO_NOT_EXIST, "用户不存在");

        if (!StringUtils.equals(oldPwd, student.getPassword()))
            throw new BizException(PASSWORD_INCORRECT, "原密码不正确");

//        BizUtil.checkPwdLength(newPwd, 6, 12);

        Student record = new Student();
        record.setId(user.getId());
        record.setPassword(newPwd);
        updateByPK(record);

        redisCacheService.setStudentToken(user.getId(), BizUtil.genToken(user), UPDATE_TOKEN_WAY_UPDATE_PWD);

        log.info(BizUtil.getUserLogPre(user) + ", 修改密码操作");

        bizUserActionDataService.updateChangeSPwdTime(user.getId(), new Timestamp(System.currentTimeMillis()), PlatformEnum.MOBILE.getType());
    }

    //获取历史资源包列表
    public PageList<PackageVo> getHistoryPackageList(HistoryPackageListGetRequest request) {

        TeacherClass tc = request.getTeacherClassId() != null && request.getTeacherClassId() > 0 ? bizTeacherClassService.getByPK(request.getTeacherClassId()) : bizTeacherClassService.getTCByCidAndSid(request.getClassId(), request.getSubjectIdStr());
        if (tc == null) return PageList.newInstance();

        PackageInfoQuery query = new PackageInfoQuery();
        query.setClassId(tc.getId());
        query.setStatus(PackageClassStatusEnum.STARTED.getStatus());
        query.setPage(request.getPageNo());
        query.setLimit(request.getPageSize());

        int total = bizPackageClassService.count(query);
        if (total <= 0) return PageList.newInstance();

        List<PackageClass> pcList = bizPackageClassService.getList(query);
        List<PackageVo> voList = new ArrayList<>();
        Set<Long> pids = new HashSet<>();
        for (PackageClass pc : pcList) {
            PackageVo vo = new PackageVo();
            vo.setId(pc.getPackageId());
            vo.setPackageClassId(pc.getId());
            vo.setCreateTime(pc.getUpdateTime());//设置成已上过的包的最近修改时间，学生端展示
            voList.add(vo);
            pids.add(pc.getPackageId());
        }

        Map<Long, PackageInfo> map = bizPackageInfoService.getMapByIds(pids);
        for (PackageVo vo : voList) {
            PackageInfo pi = map.get(vo.getId());
            if (pi != null)
                vo.setPackageName(pi.getPackageName());
            vo.setId(vo.getPackageClassId());
        }
        return PageList.newInstance(total, voList, query.getPage());
    }

    //获取收藏列表
    public PageList<FileResourceVo> getCollectedList(StudentCollectQuery query) {
        int total = bizStudentCollectService.count(query);
        if (total <= 0) return PageList.newInstance();

        List<StudentCollectNotes> list = bizStudentCollectService.getList(query);

        List<FileResourceVo> voList = new ArrayList<>();

        Map<Long, Set<Long>> pcrIdMap = new HashMap<>();//同一学科下，一个包对应多个资源
        Set<Long> resourceIds = new HashSet<>();
        for (StudentCollectNotes o : list) {

            long packageClassId = o.getPackageClassId();
            long resourceId = o.getResourceId();
            resourceIds.add(resourceId);

            Set<Long> rids = pcrIdMap.get(packageClassId);
            if (rids == null) rids = new HashSet<>();
            rids.add(resourceId);
            pcrIdMap.put(packageClassId, rids);

            FileResourceVo vo = new FileResourceVo();
            vo.setResourceId(o.getResourceId());
            vo.setCollected(true);
            vo.setPackageClassId(o.getPackageClassId());
            voList.add(vo);
        }

        //查资源
        Map<Long, FileResources> resourceMap = bizFileResourceService.getMapByIds(resourceIds);
        if (MapUtils.isEmpty(resourceMap)) return PageList.newInstance();

        Map<String, String> noteMap = new HashMap<>();
        for (Map.Entry<Long, Set<Long>> entry : pcrIdMap.entrySet()) {
            long packageClassId = entry.getKey();
            Set<Long> rids = entry.getValue();

            if (CollectionUtils.isEmpty(rids)) continue;

            Map<String, String> noteMapTemp = bizPackageResourceNoteService.getNoteContentMapByPCIdAndRIds(packageClassId, rids);
            if (MapUtils.isEmpty(noteMapTemp)) continue;
            noteMap.putAll(noteMapTemp);
        }

        //设置资源内容
        for (FileResourceVo vo : voList) {
            FileResources fr = resourceMap.get(vo.getResourceId());
            if (fr == null) continue;

            BizUtil.bindResourceVo(vo, fr);
            vo.setChildResourceList(bizFileResourceChildService.listByResourceId(vo.getResourceId()));
        }
        BizUtil.replaceResourceToNoteContent(voList, noteMap);

        return PageList.newInstance(total, voList, query.getPage());
    }

    //获取笔记列表
    public List<FileResourceVo> getNotesList(PackageResourceNoteQuery query) {
        query.noLimit();

        int total = bizPackageResourceNoteService.count(query);
        if (total <= 0) return null;

        //查笔记内容
        List<PackageResourceNote> prnList = bizPackageResourceNoteService.getList(query);
        Set<Long> rids = new HashSet<>();
        for (PackageResourceNote pr : prnList) {
            rids.add(pr.getResourceId());
        }
        Map<String, String> prnConMap = bizPackageResourceNoteService.bindRIdCRIdContentMapByList(prnList);

        //查资源主体信息
        List<FileResources> frList = bizFileResourceService.getListByIds(rids);
        if (CollectionUtils.isEmpty(frList)) return null;

        List<FileResourceVo> voList = new ArrayList<>(rids.size());
        for (FileResources fr : frList) {
            FileResourceVo vo = new FileResourceVo();
            BizUtil.bindResourceVo(vo, fr);
            vo.setChildResourceList(bizFileResourceChildService.listByResourceId(vo.getResourceId()));
            vo.setPackageClassId(query.getPackageClassId());
            voList.add(vo);
        }

        BizUtil.replaceResourceToNoteContent(voList, prnConMap);

        Map<Long, Boolean> collectMap = bizStudentCollectService.getByStuIdAndRids(query.getStudentId(), query.getPackageClassId(), rids);
        if (MapUtils.isNotEmpty(collectMap)) {
            for (FileResourceVo vo : voList) {
                vo.setCollected(collectMap.containsKey(vo.getResourceId()));
            }
        }
        return voList;
    }

    //获取资源包下的随堂测试列表
    public List<UnitTestVo> getUnitTestList(long packageClassId, Boolean del) {
        List<UnitTestVo> list = bizUnitTestService.getVoListByPCId(packageClassId);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(o -> {
                List<ExerciseVo> quesList = bizUnitTestExerciseService.getExerciseVoListByUTId(o.getUnitTestId(), del);
                o.setExerciseList(quesList);
            });
        }
        return list;
    }

    //根据包id查询包下的测试和习题，带学生答案和是否收藏相关信息
    public List<UnitTestVo> getQuesListByPackage(long studentId, long packageClassId, Boolean del) {
        List<UnitTestVo> list = getUnitTestList(packageClassId, del);
        if (CollectionUtils.isEmpty(list)) return null;
        list.forEach(o -> {
            if (CollectionUtils.isEmpty(o.getExerciseList())) return;
            Set<Long> quesIds = new HashSet<>(o.getExerciseList().size());
            o.getExerciseList().forEach(q -> {
                quesIds.add(q.getExerciseId());
                q.setCollected(bizStudentCollectService.isCollectedQues(studentId, q.getExerciseId()));
            });
            Map<String, StudentExerciseSubmit> map = bizStudentExerciseSubmitService.getMapByStuByStuUnitQuesIds(studentId, o.getUnitTestId(), quesIds);
            if (MapUtils.isEmpty(map)) return;

            o.getExerciseList().forEach(q -> {
                StudentExerciseSubmit submit = map.get(studentId + "_" + o.getUnitTestId() + "_" + q.getExerciseId());
                if (submit == null) return;
                q.setStudentAnswer(submit.getStudentAnswer());
                if (submit.getAnswerResult() != null)
                    q.setAnswerResult(submit.getAnswerResult().intValue());
            });
        });
        return list;
    }

    //收藏资源
    @Transactional(rollbackFor = Throwable.class)
    public void collectResource(long studentId, long packageClassId, long resourceId, long subjectId) {

//        List<PackageResourceNote> prnList = bizPackageResourceNoteService.getByPCIdAndRId(packageClassId, resourceId);
//        if (CollectionUtils.isEmpty(prnList)) return;

        StudentCollectNotes record = new StudentCollectNotes();
        record.setStudentId(studentId);
        record.setPackageClassId(packageClassId);
        record.setResourceId(resourceId);
        record.setSubjectId(subjectId);

        bizStudentCollectService.add(record);

    }

    //取消收藏
    @Transactional(rollbackFor = Throwable.class)
    public void delCollectResource(long studentId, long packageClassId, long resourceId, long subjectId) {
        bizStudentCollectService.delCollected(studentId, packageClassId, resourceId, subjectId);
    }

    //学生提交习题答案
    @Transactional(rollbackFor = Throwable.class)
    public void submitExerciseAnswer(long studentId, List<SubmitExerciseAnswerRequest> list) {
        if (CollectionUtils.isEmpty(list)) return;

        List<StudentExerciseSubmit> records = new ArrayList<>(list.size());

        list.forEach(req -> {
            req.setStudentId(studentId);
            StudentExerciseSubmit db = new StudentExerciseSubmit();
            db.setStudentId(req.getStudentId());

            UnitTestExercises ute = bizUnitTestExerciseService.getByUTIdAndEId(req.getUnitTestId(), req.getExerciseId());
            if (ute == null) return;

            db.setUnitTestExerciseId(ute.getId());
            db.setStudentAnswer(req.getAnswer());
            Integer result = StudentExercisesEnum.getResult(req.getAnswerResult());
            if (result != null)
                db.setAnswerResult(result.shortValue());
            db.setCostTime((int) ((System.currentTimeMillis() - ute.getCreateTime().getTime()) / 1000L));
            records.add(db);

        });
        bizStudentExerciseSubmitService.insertBatchNoExist(records);
    }

    public Student getById(long id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    public StudentVo getVoById(long id) throws Exception {
        Student s = getById(id);
        if (s == null) return null;

        StudentVo vo = new StudentVo();
        BeanUtilsExt.propertyUtilsCopy(vo, s);

        School school = bizSchoolService.getById(s.getSchoolId());
        if (school != null) {
            vo.setSchoolName(school.getSchoolName());
        }

        ClassDomain classDomain = bizClassService.getById(s.getClassId());
        if (classDomain != null) {
            vo.setGrade(classDomain.getGrade());
            vo.setClassNo(classDomain.getClassNo());
        }

        return vo;
    }

    public PageList<StudentVo> getPageList(StudentQuery query) throws Exception {
        if (query != null) {
            query = initQuery(query);
        }

        long count = count(query);
        if (count <= 0) return PageList.newInstance();

        List<Student> list = getList(query);
        Set<Long> schoolIds = new HashSet<>();
        Set<Long> classIds = new HashSet<>();
        List<StudentVo> voList = new ArrayList<>();
        for (Student s : list) {
            schoolIds.add(s.getSchoolId());
            classIds.add(s.getClassId());
            StudentVo vo = new StudentVo();
            BeanUtilsExt.propertyUtilsCopy(vo, s);
            voList.add(vo);
        }

        Map<Long, School> schoolMap = bizSchoolService.getMapByIds(schoolIds);
        Map<Long, ClassDomain> classMap = bizClassService.getMapByIds(classIds);
        for (StudentVo vo : voList) {
            School school = schoolMap.get(vo.getSchoolId());
            vo.setSchoolName(school != null ? school.getSchoolName() : "");

            ClassDomain classDomain = classMap.get(vo.getClassId());
            if (classDomain != null) {
                vo.setGrade(classDomain.getGrade());
                vo.setClassNo(classDomain.getClassNo());
            }
        }
        return new PageList<>(count, voList);
    }

    private StudentQuery initQuery(StudentQuery query) {
        Set<Long> cls = BizUtil.strToLongs(query.getClassIdstr());
        query.setClassIds(cls);
        return query;
    }

    public List<Student> getList(StudentQuery query) {
        return studentMapper.select(query);
    }

    public List<Student> getListForWeb(StudentQuery query) {
        return studentMapper.selectForWeb(query);
    }

    public Student getOneByMobile(String mobile) throws Exception {
        StudentQuery query = new StudentQuery();
        query.setParentPhone(mobile);
//        query.limitOne();
        List<Student> list = getListForWeb(query);
        if (CollectionUtils.isEmpty(list)) return null;

        if (list.size() > 1) throw new BizException("该手机号存在多个学生账号");
        return list.get(0);
    }

    public long count(StudentQuery query) {
        return studentMapper.count(query);
    }

    public void updateByPK(Student record) {
        studentMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateStudetnStatus(StatusDomain statusDomain) throws Exception {
        if (studentMapper.updateStatus(statusDomain) <= 0) return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean addStudentService(Student student) throws Exception {
        if (studentMapper.insertSelective(student) <= 0) return false;
        bizClassService.addStudentNum(student.getClassId());
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteStudentService(Long id) throws Exception {
        Student student = studentMapper.selectByPrimaryKey(id);
        if (studentMapper.deleteByPrimaryKey(id) <= 0) return false;
        bizClassService.SubstractStudentNum(student.getClassId());
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateStudentService(Student student) throws Exception {
        Student studentOri = studentMapper.selectByPrimaryKey(student.getId());
        if (studentMapper.updateByPrimaryKeySelective(student) <= 0) return false;
        if (studentOri.getClassId() != student.getClassId()) {
            bizClassService.addStudentNum(student.getClassId());
            bizClassService.SubstractStudentNum(studentOri.getClassId());
        }
        return true;
    }

    public List<String> getStuNameByClass(long classId) {
        return studentMapper.getStuNameByClass(classId);
    }

    public Map<Long, Student> getMapByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;
        StudentQuery qry = new StudentQuery();
        qry.setIds(ids);
        List<Student> list = getListForWeb(qry);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, Student> map = new HashMap<>();
        list.forEach(o -> {
            map.put(o.getId(), o);
        });
        return map;
    }

    //获取收藏的习题列表
    public PageList<ExerciseVo> getCollectExerciseList(StudentCollectExercisesQuery qry, Boolean del) {
        int count = bizStudentCollectExerciseService.count(qry);
        if (count <= 0) return PageList.newInstance();

        List<StudentCollectExercise> list = bizStudentCollectExerciseService.selectList(qry);
        Set<Long> quesIds = new HashSet<>();

        List<ExerciseVo> voList = new ArrayList<>(list.size());
        list.forEach(o -> {
            quesIds.add(o.getExerciseId());
            ExerciseVo vo = new ExerciseVo();
            vo.setExerciseId(o.getExerciseId());
            vo.setStudentAnswer(o.getStudentAnswer());
            vo.setAnswerResult(o.getAnswerResult().intValue());
            voList.add(vo);
        });
        Map<Long, ExerciseVo> map = bizExerciseService.getVoMapByIds(quesIds, del);
        voList.forEach(q -> {
            q.setCollected(true);
            ExerciseVo vo = map.get(q.getExerciseId());
            if (vo == null) return;
            try {
                String ans = q.getStudentAnswer();
                Integer result = q.getAnswerResult();
                BeanUtilsExt.copyProperties(q, vo);
                q.setStudentAnswer(ans);
                q.setAnswerResult(result);
                q.setCollected(true);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
        return PageList.newInstance(count, voList, qry.getPage());
    }
}
