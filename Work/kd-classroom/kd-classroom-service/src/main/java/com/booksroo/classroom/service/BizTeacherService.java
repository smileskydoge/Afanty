package com.booksroo.classroom.service;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.constant.BizConstant;
import com.booksroo.classroom.common.constant.PromptConstant;
import com.booksroo.classroom.common.dao.TeacherMapper;
import com.booksroo.classroom.common.domain.*;
import com.booksroo.classroom.common.enums.FileEnum;
import com.booksroo.classroom.common.enums.PlatformEnum;
import com.booksroo.classroom.common.enums.UserEnum;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.*;
import com.booksroo.classroom.common.request.UploadNotesRequest;
import com.booksroo.classroom.common.util.*;
import com.booksroo.classroom.common.vo.*;
import com.booksroo.classroom.service.interf.CacheService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Util;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;

import static com.booksroo.classroom.common.constant.BizConstant.UPDATE_TOKEN_WAY_DO_LOGIN;
import static com.booksroo.classroom.common.constant.BizConstant.UPDATE_TOKEN_WAY_UPDATE_PWD;
import static com.booksroo.classroom.common.constant.CommonConstant.SYSTEM_ERROR;
import static com.booksroo.classroom.common.constant.PromptConstant.*;

/**
 * @author liujianjian
 * @date 2018/6/3 17:09
 */
@Service("bizTeacherService")
public class BizTeacherService extends BaseService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CacheService redisCacheService;
    @Autowired
    private BizSchoolService bizSchoolService;
    @Autowired
    private BizUserActionDataService bizUserActionDataService;
    @Autowired
    private BizClassService bizClassService;
    @Autowired
    private BizTeacherClassService bizTeacherClassService;
    @Autowired
    private BizFileStoreService bizFileStoreService;
    @Autowired
    private BizFileResourceService bizFileResourceService;
    @Autowired
    private BizPackageResourceService bizPackageResourceService;
    @Autowired
    private BizFileResourceChildService bizFileResourceChildService;
    @Autowired
    private BizPackageInfoService bizPackageInfoService;
    @Autowired
    private BizResourceClassService bizResourceClassService;
    @Autowired
    private BizTeacherResourceService bizTeacherResourceService;
    @Autowired
    private BizSubjectService bizSubjectService;
    @Autowired
    private BizPackageClassService bizPackageClassService;

    @Autowired
    private BizExerciseService bizExerciseService;
    @Autowired
    private BizPackageExerciseService bizPackageExerciseService;
    @Autowired
    private BizStatisticsService bizStatisticsService;
    @Autowired
    private BizUnitTestService bizUnitTestService;
    @Autowired
    private BizUnitTestExerciseService bizUnitTestExerciseService;
    @Autowired
    private BizExerciseClassService bizExerciseClassService;

    //    @Autowired
//    private BizFileNoteService bizFileNoteService;
    @Autowired
    private BizPackageResourceNoteService bizPackageResourceNoteService;

    public TeacherVo doLogin(String mobileNo, String password, int platform, String deviceNo) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(mobileNo, password);

        mobileNo = mobileNo.trim();
        password = password.trim();
        Teacher teacher = getByMobileNo(mobileNo);
        if (teacher == null) throw new BizException(MOBILE_NUMBER_NOT_EXIST, "手机号不存在");

        if (!StringUtils.equals(password, teacher.getPassword())) {
            throw new BizException(PASSWORD_INCORRECT, "密码不正确");
        }
        TeacherVo vo = new TeacherVo();
        vo.setUserType(UserEnum.TEACHER.getType());
        BeanUtilsExt.propertyUtilsCopy(vo, teacher);

        School school = bizSchoolService.getById(teacher.getSchoolId());
        if (school != null) {
            vo.setSchoolName(school.getSchoolName());
        }
        redisCacheService.setSessionTeacherVo(vo);
        vo.setFirstLoginTime(bizUserActionDataService.getTFirstLoginTime(vo.getId(), platform));
        //更新下登录时间
        bizUserActionDataService.updateTeacherLoginTime(vo.getId(), new Timestamp(System.currentTimeMillis()), platform);

        vo.setToken(redisCacheService.getTeacherToken(vo.getId()));
        if (PlatformEnum.isMobile(platform)) {
            vo.setToken(BizUtil.genToken(vo));
            redisCacheService.setTeacherToken(vo.getId(), vo.getToken(), UPDATE_TOKEN_WAY_DO_LOGIN);
        }
        if (StringUtils.isBlank(vo.getToken())) {
            vo.setToken(BizUtil.genToken(vo));
            redisCacheService.setTeacherToken(vo.getId(), vo.getToken(), UPDATE_TOKEN_WAY_DO_LOGIN);
        }
        return vo;
    }

    //修改密码
    @Transactional(rollbackFor = Throwable.class)
    public void updatePwd(TeacherVo user, String oldPwd, String newPwd) throws Exception {

        ParamCheckUtil.checkNotEmptyStr(oldPwd, newPwd);
        oldPwd = oldPwd.trim();
        newPwd = newPwd.trim();
        if (StringUtils.equalsIgnoreCase(oldPwd, newPwd)) return;

        Teacher teacher = getById(user.getId());
        if (teacher == null) throw new BizException(USER_INFO_NOT_EXIST, "用户不存在");

        if (!StringUtils.equals(oldPwd, teacher.getPassword()))
            throw new BizException(PASSWORD_INCORRECT, "原密码不正确");

//        BizUtil.checkPwdLength(newPwd, 6, 12);

        teacher = new Teacher();
        teacher.setId(user.getId());
        teacher.setPassword(newPwd);
        updateByPK(teacher);
        log.info(BizUtil.getUserLogPre(user) + ", 修改密码操作");

        redisCacheService.setTeacherToken(user.getId(), BizUtil.genToken(user), UPDATE_TOKEN_WAY_UPDATE_PWD);

        bizUserActionDataService.updateChangeTPwdTime(user.getId(), new Timestamp(System.currentTimeMillis()), PlatformEnum.PC.getType());
    }

    //上传资源
    @Transactional(rollbackFor = Throwable.class)
    public List<String> uploadFile(InputStream is, String fileName, long teacherId, List<String> urls) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(fileName);
        fileName = fileName.trim();

        //插入主资源
        long resourceId = addFileResource(fileName, teacherId);

        if (CollectionUtils.isEmpty(urls)) {
            urls = bizFileStoreService.handleStore(is, fileName, teacherId + "_teacher_" + resourceId);
        }
        if (CollectionUtils.isEmpty(urls)) throw new BizException(FILE_UPLOAD_FAIL, "上传ppt失败, 请重试");

        persistenceAfterUpload(urls, teacherId, resourceId);
        return urls;
    }

    //添加父资源文件
    private long addFileResource(String fileName, long teacherId) throws Exception {
        FileResources fr = new FileResources();
        fr.setResourceName(fileName);
        fr.setTeacherId(teacherId);
        fr.setResourceType(FileEnum.getType(FileUtil.getFileSuffix(fileName)).byteValue());
        bizFileResourceService.add(fr);
        Long resourceId = fr.getId();
        if (resourceId == null || resourceId <= 0)
            throw new BizException(PromptConstant.VALUE_INVALID, "resourceId为null");
        return resourceId;
    }

    //添加子资源文件
    private void persistenceAfterUpload(List<String> urls, long teacherId, long resourceId) {
        //插入子资源
        List<FileResourceChild> frcs = new ArrayList<>(urls.size());
        for (int i = 0; i < urls.size(); i++) {
            FileResourceChild frc = new FileResourceChild(resourceId, urls.get(i), i + 1);
            frcs.add(frc);
        }
        bizFileResourceChildService.addBatch(frcs);
        //老师跟资源关系
        addTeacherResource(teacherId, resourceId);
    }

    //上传多个文件到资源包下
    @Transactional(rollbackFor = Throwable.class)
    public void addNewResourceToPackageBatch(long teacherId, Map<Long, Map<String, String>> map) throws Exception {
        if (MapUtils.isEmpty(map)) return;
        for (Map.Entry<Long, Map<String, String>> entry : map.entrySet()) {
            long pcId = entry.getKey();
            addSingleResourcePackage(teacherId, pcId, entry.getValue());
        }
    }

    //上传单个文件到资源包下
    @Transactional(rollbackFor = Throwable.class)
    public void addSingleResourcePackage(long teacherId, long packageClassId, Map<String, String> files) throws Exception {
        if (MapUtils.isEmpty(files)) return;

        for (Map.Entry<String, String> entry : files.entrySet()) {
            String fileName = entry.getKey();
            String fileUrl = entry.getValue();
            long resourceId = addFileResource(fileName, teacherId);

            FileResourceChild child = new FileResourceChild();
            child.setResourceId(resourceId);
            child.setContent(fileUrl);
            child.setOrderNo(1);
            bizFileResourceChildService.add(child);
            if (child.getId() == null || child.getId() <= 0)
                throw new BizException(PromptConstant.VALUE_INVALID, "childResourceId为null");

            bizPackageResourceService.add(packageClassId, resourceId);
            PackageResourceNote prn = new PackageResourceNote();
            prn.setPackageClassId(packageClassId);
            prn.setResourceId(resourceId);
            prn.setChildResourceId(child.getId());
            bizPackageResourceNoteService.addNotExist(prn);

            //老师跟资源关系
            addTeacherResource(teacherId, resourceId);
        }
    }

    //添加教师资源关系
    private void addTeacherResource(long teacherId, long resourceId) {
        TeacherResource record = new TeacherResource();
        record.setTeacherId(teacherId);
        record.setResourceId(resourceId);
        bizTeacherResourceService.addNotExist(record);
    }

    //结束上课时的业务
    public void endClass(TeacherVo user, long packageClassId, Long resourceId, Integer currentPPTNo) {
        updatePackageStatus(packageClassId, (byte) 1);
        bizPackageResourceService.updateCurrentNo(packageClassId, resourceId, currentPPTNo);
        //把资源包下面的资源拷贝一份到package_resource_note表，用于学生端查询

//        ioTaskExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//        try {
        bizPackageResourceNoteService.copyResourcesByPackage(packageClassId);
//        } catch (Exception e) {
//            log.error(BizUtil.getUserLogPre(user) + ", packageClassId: " + packageClassId + ", copyResourcesByPackage 出错: " + e.getMessage(), e);
//        }
//            }
//        });
    }

    //资源名是否已存在
    public boolean isResourceNameExist(long teacherId, String name) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(name);

        List<FileResources> list = bizFileResourceService.getListByTIdAndName(teacherId, name);
        if (CollectionUtils.isEmpty(list)) return false;
        for (FileResources o : list) {
            if (bizTeacherResourceService.isExist(teacherId, o.getId())) return true;
        }
        return false;
    }

    //上传笔记
    @Transactional(rollbackFor = Throwable.class)
    public void uploadNotesFile(long teacherId, List<UploadNotesRequest> requestList) throws Exception {
        if (CollectionUtils.isEmpty(requestList)) return;

        for (int i = 0; i < requestList.size(); i++) {
            UploadNotesRequest request = requestList.get(i);
            ParamCheckUtil.checkNotNull(request.getChildResourceId(), request.getResourceId(), request.getPackageClassId(), request.getCreateTime());
            ParamCheckUtil.checkNotEmptyStr(request.getFileName(), request.getFileUrl());
        }

        ioTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < requestList.size(); i++) {
                    UploadNotesRequest request = null;
                    try {
                        request = requestList.get(i);
                        String url = request.getFileUrl();
                        PackageResourceNote prn = new PackageResourceNote();
                        prn.setCreateTime(new Date(request.getCreateTime()));
                        prn.setNoteContent(url);
                        prn.setNoteName(request.getFileName());
                        prn.setChildResourceId(request.getChildResourceId());
                        prn.setPackageClassId(request.getPackageClassId());
                        prn.setResourceId(request.getResourceId());
                        bizPackageResourceNoteService.saveOrUpdate(prn);
                    } catch (Exception e) {
                        log.error("持久化笔记信息出错, request: " + (request != null ? JSON.toJSONString(request) : "null") + ", " + e.getMessage(), e);
                    }
                }
            }
        });
    }

    //发送资源到班级
    @Transactional(rollbackFor = Throwable.class)
    public void sendToClass(long teacherId, String teacherClassIds, String resourceIds, String packageName, String packageId) throws Exception {

        ParamCheckUtil.checkNotEmptyStr(teacherClassIds, resourceIds, packageName);
        teacherClassIds = teacherClassIds.trim();
        resourceIds = resourceIds.trim();
        packageName = packageName.trim();

        //插入资源、包、班级之间关系
        Set<Long> tcIds = BizUtil.strToLongs(teacherClassIds);
        boolean oneClass = tcIds.size() == 1;
        for (long tcId : tcIds) {

            PackageInfo pi = bizPackageInfoService.getOneByTeacherAndName(tcId, packageName);
            Long pid = pi == null ? bizPackageInfoService.add(teacherId, tcId, packageName) : pi.getId();

            if (pid == null) throw new BizException(VALUE_INVALID, "packageId为空，数据不正确");

            //插入班级跟包关系
            Map<Long, Boolean> pcMap = bizPackageClassService.add(tcId, pid, true);
            Set<Long> pcIds = pcMap.keySet();
            if (CollectionUtils.isEmpty(pcIds)) throw new BizException(VALUE_INVALID, "数据错误: packageClassId为null");

            long pcId = 0;
            boolean isPackageExist = false;
            for (Map.Entry<Long, Boolean> entry : pcMap.entrySet()) {
                pcId = entry.getKey();
                isPackageExist = entry.getValue();
                break;
            }

            Set<Long> rids = BizUtil.strToLongs(resourceIds);
            boolean oneResource = rids.size() == 1;
            Map<Long, Integer> typeMap = bizFileResourceService.getResourceTypeByIds(rids);
            boolean pptExist = false;
            if (isPackageExist) {
                pptExist = bizPackageResourceService.isPPTExist(pcId);
            }

            //统计ppt类型数量，超过1个不执行
            int i = 0;
            if (MapUtils.isNotEmpty(typeMap)) {
                for (long rid : rids) {
                    Integer type = typeMap.get(rid);
                    if (type != null && FileEnum.isPPT(type)) ++i;
                }
            }
            if (i > 1) {
                throw new BizException(PromptConstant.FILE_TYPE_ALREADY_EXIST, "一个资源包下只能包含一个ppt类型的文件");
            }
            for (long rid : rids) {
                if (MapUtils.isNotEmpty(typeMap)) {
                    Integer type = typeMap.get(rid);
                    if (type != null && FileEnum.isPPT(type) && pptExist) {
                        //一个资源包限制一个ppt类型
                        if (oneResource)
                            throw new BizException(PromptConstant.FILE_TYPE_ALREADY_EXIST, oneClass ? "该资源包下已有ppt类型文件，请重新选择" : "存在班级该资源包下已有ppt类型文件，请重新选择");
                        continue;
                    }
                }
                //插入包跟资源关系
                if (bizPackageResourceService.isResourceExist(pcId, rid))
                    throw new BizException(FILE_ALREADY_EXIST_R, "有文件在该资源包下已存在，请重新选择");
                bizPackageResourceService.add(pcId, rid);

                //新添加了资源，将笔记表是否对教师显示字段重置为否
                final long packageClassId = pcId;
                ioTaskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        bizPackageResourceNoteService.updateTeacherShowFlag(packageClassId, rid, (short) 0);
                    }
                });
            }

            final long packageClassId = pcId;
            //插入资源跟班级关系
            ioTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    for (long rid : rids) {
                        ResourceClass rc = null;
                        try {
                            rc = new ResourceClass();
                            rc.setTeacherClassId(tcId);
                            rc.setResourceId(rid);
                            bizResourceClassService.saveOrUpdate(rc);
                        } catch (Exception e) {
                            log.error("插入班级跟资源关系出错, param:" + (rc != null ? JSON.toJSONString(rc) : "null"));
                        }
                    }
                }
            });

            ioTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    bizPackageResourceNoteService.handleTeacherAddResourceToPackage(packageClassId, rids);
                }
            });
        }
    }

    //添加已存在的资源到资源包下
    @Transactional(rollbackFor = Throwable.class)
    public void addResourceByPackage(long teacherClassId, long packageClassId, Set<String> resourceIds) throws Exception {
        if (CollectionUtils.isEmpty(resourceIds)) return;

        int ppt = 0;
        Map<Long, Integer> map = new HashMap<>();
        for (String str : resourceIds) {
            if (StringUtils.isBlank(str)) continue;

            String[] arr = str.split("_");
            long rid = Long.parseLong(arr[0]);
            int type = Integer.parseInt(arr[1]);
            map.put(rid, type);

            if (FileEnum.isPPT(type)) ++ppt;
        }

        if (ppt > 1) throw new BizException(PromptConstant.FILE_TYPE_ALREADY_EXIST, "一个资源包下只能添加一个ppt类型文件");

        Set<Integer> types = bizPackageResourceService.getPackageResourceType(packageClassId);

        if (MapUtils.isEmpty(map)) return;

        for (Map.Entry<Long, Integer> entry : map.entrySet()) {

            long rid = entry.getKey();
            int type = entry.getValue();

            if (FileEnum.isPPT(type)) ++ppt;

            if (FileEnum.isPPT(type) && CollectionUtils.isNotEmpty(types) && types.contains(type))
                throw new BizException(PromptConstant.FILE_TYPE_ALREADY_EXIST, "该资源包下已有ppt类型文件，请重新选择");

            bizPackageResourceService.addNotExist(packageClassId, rid);

            ResourceClass record = new ResourceClass();
            record.setResourceId(rid);
            record.setTeacherClassId(teacherClassId);
            bizResourceClassService.saveOrUpdate(record);
        }

        ioTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bizPackageResourceNoteService.handleTeacherAddResourceToPackage(packageClassId, map.keySet());
            }
        });
    }

    //查询资源列表
    public PageList<FileResourceVo> getResourceList(ResourceQuery query) {
        List<FileResources> list = null;
        long total = 0;
        Map<Long, Set<Long>> hasSendMap = null;
        Map<Long, PackageResource> prMap = null;
        Set<Long> resourceIds = null;

        boolean queryByPackage = query.getPackageId() != null && query.getPackageId() > 0;
        boolean historyPackage = false;
        if (query.getClassId() != null && query.getClassId() > 0) {
            //按班级查询
            total = bizResourceClassService.count(query);
            if (total <= 0) return PageList.newInstance();
            resourceIds = bizResourceClassService.getResourceIds(query);
        } else if (queryByPackage) {
            //按包查询
            historyPackage = bizPackageClassService.isPackageHistory(query.getPackageId());

            PackageResourceQuery pq = new PackageResourceQuery();
            pq.setPage(query.getPage());
            pq.setLimit(query.getLimit());
            pq.setPackageClassId(query.getPackageId());
            pq.setResourceType(query.getResourceType());
            total = bizPackageResourceService.count(pq);
            if (total <= 0) return PageList.newInstance();
            List<PackageResource> prList = bizPackageResourceService.getList(pq);
            resourceIds = new HashSet<>();
            prMap = new HashMap<>();
            for (PackageResource pr : prList) {
                resourceIds.add(pr.getResourceId());
                prMap.put(pr.getResourceId(), pr);
            }
        } else {
            //按用户查询
            TeacherResourceQuery trQuery = new TeacherResourceQuery();
            trQuery.setTeacherId(query.getTeacherId());
            trQuery.setResourceType(query.getResourceType());
            trQuery.setPage(query.getPage());
            trQuery.setLimit(query.getLimit());
            total = bizTeacherResourceService.count(trQuery);
            if (total <= 0) return PageList.newInstance();
            resourceIds = bizTeacherResourceService.getResourceIds(trQuery);
        }

        list = queryByPackage ? bizFileResourceService.getListByIds(resourceIds, "resource_type asc, id desc") : bizFileResourceService.getListByIds(resourceIds);

        if (CollectionUtils.isEmpty(list)) return PageList.newInstance();

        if (query.isNeedReturnPackageClassId()) {
            Set<Long> rids = new HashSet<>(list.size());
            for (FileResources fr : list) {
                rids.add(fr.getId());
            }
            hasSendMap = bizPackageResourceService.getResourcePackageMapByRids(rids);
        }

        List<FileResourceVo> voList = new ArrayList<>();
        for (FileResources fr : list) {
            FileResourceVo vo = new FileResourceVo(fr.getId(), fr.getResourceName(), fr.getResourceType().intValue());
            vo.setCreateTime(fr.getCreateTime());
            vo.setPackageId(hasSendMap != null ? BizUtil.longsToStr(hasSendMap.get(fr.getId())) : "");
            if (query.isNeedReturnChildList()) {
                //查询子资源
                vo.setChildResourceList(bizFileResourceChildService.listByResourceId(vo.getResourceId()));
            }
            if (queryByPackage) {
                vo.setPackageClassId(query.getPackageId());
            }
            voList.add(vo);
            if (MapUtils.isEmpty(prMap)) continue;
            PackageResource pr = prMap.get(vo.getResourceId());
            if (pr != null) {
                vo.setPackageResourceId(pr.getId());
                vo.setCurrentNo(pr.getCurrentNo());
            }
        }

        //查询笔记内容，替换子资源内容
        if (historyPackage && query.isNeedReturnChildList()) {
            bizPackageResourceNoteService.replaceToNoteContent(query.getPackageId(), resourceIds, voList);
        }
        return PageList.newInstance(total, voList, query.getPage());
    }

    //获取文件内容
    public List<FileResourceChild> getResourceDetail(Long packageClassId, Long teacherClassId, long resourceId) {
        List<FileResourceChild> list = bizFileResourceChildService.listByResourceId(resourceId);
        if (CollectionUtils.isEmpty(list)) return null;

        if (packageClassId != null && packageClassId > 0) {
            BizUtil.replaceResourceToNoteContent(packageClassId, list, bizPackageResourceNoteService.getConMapByPCIdAndRIdForTeacher(packageClassId, resourceId));
        }
        return list;
    }

    //获取班级下的包列表
    public PageList<PackageVo> getPackageListByClass(PackageInfoQuery query) throws Exception {
        int total = bizPackageClassService.count(query);
        if (total <= 0) return PageList.newInstance();

        List<PackageClass> pcList = bizPackageClassService.getList(query);
        if (CollectionUtils.isEmpty(pcList)) return PageList.newInstance();

        Map<Long, PackageClass> map = new HashMap<>();
        for (PackageClass o : pcList) {
            map.put(o.getPackageId(), o);
        }

        List<PackageInfo> list = bizPackageInfoService.getListByIds(map.keySet());
        if (CollectionUtils.isEmpty(list)) return PageList.newInstance();

        List<PackageVo> voList = new ArrayList<>();
        for (PackageInfo o : list) {
            PackageVo vo = new PackageVo();
            BeanUtilsExt.propertyUtilsCopy(vo, o);
            PackageClass pc = map.get(o.getId());
            if (pc != null) {
                vo.setPackageClassId(pc.getId());
                vo.setCreateTime(pc.getCreateTime());
                vo.setStatus(pc.getStatus());
            }
            voList.add(vo);
        }
        return PageList.newInstance(total, voList, query.getPage());
    }

    //删除资源
    @Transactional(rollbackFor = Throwable.class)
    public void delResource(TeacherVo user, Long teacherClassId, long resourceId, String packageClassIds) {
        log.info(BizUtil.getUserLogPre(user) + ", 删除资源, classId: " + teacherClassId + ", resourceId: " + resourceId + ", packageClassIds:" + packageClassIds);
        if (StringUtils.isNotBlank(packageClassIds)) {
            //删除包里的该文件
            packageClassIds = packageClassIds.trim();
            Set<Long> pcIds = BizUtil.strToLongs(packageClassIds);
            if (CollectionUtils.isNotEmpty(pcIds)) {
                for (long pcId : pcIds) {
                    bizPackageResourceService.delByPCidAndRid(pcId, resourceId);
                }
            }
            if (teacherClassId != null && teacherClassId > 0) {
                bizResourceClassService.delByTCIdAndRid(teacherClassId, resourceId);
            }
            return;
        }

        if (teacherClassId != null && teacherClassId > 0) {
            //删除班级下该文件, 如果存在包, 也删除文件和包的关系
            Set<Long> pcIds = bizPackageClassService.getPackageClassIdsByCid(teacherClassId);
            if (CollectionUtils.isNotEmpty(pcIds)) {
                for (long pcId : pcIds) {
                    bizPackageResourceService.delByPCidAndRid(pcId, resourceId);
                }
            }
            bizResourceClassService.delByTCIdAndRid(teacherClassId, resourceId);
            return;
        }

        //删文件跟老师关系, 文件跟包关系，文件跟班级关系
        bizTeacherResourceService.deleteByUK(user.getId(), resourceId);
        bizPackageResourceService.delByResourceId(resourceId);
        bizResourceClassService.delByResourceId(resourceId);
    }

    //删除班级下的包
    @Transactional(rollbackFor = Throwable.class)
    public void delClassPackage(TeacherVo user, long teacherClassId, long packageId) {
        PackageClass pc = bizPackageClassService.getByCidAndPid(teacherClassId, packageId);
        if (pc == null) return;

        //删包跟班级关系
        bizPackageClassService.del(teacherClassId, packageId);
        //删该包下的资源关系
        long packageClassId = pc.getId();
        bizPackageResourceService.delByPackageClassId(packageClassId);
        //删除包下的资源跟班级关系
        bizResourceClassService.delByPackageClassId(packageClassId);
        log.info(BizUtil.getUserLogPre(user) + ", 删除资源, classId: " + teacherClassId + ", packageId:" + packageId + ", packageClassId:" + packageClassId);
    }

    //修改包名
    @Transactional(rollbackFor = Throwable.class)
    public Long updateClassPackageName(long teacherId, long teacherClassId, long oldPackageId, String packageName) throws Exception {

        ParamCheckUtil.checkNotEmptyStr(packageName);
        packageName = packageName.trim();
        PackageInfo pi = bizPackageInfoService.getOneByTeacherAndName(teacherClassId, packageName);
        if (pi != null)
            throw new BizException(FILE_NAME_ALREADY_EXIST, "该班级下已存在包名相同的资源包，请重新命名");

        pi = new PackageInfo();
        pi.setTeacherId(teacherId);
        pi.setTeacherClassId(teacherClassId);
        pi.setPackageName(packageName);
        bizPackageInfoService.add(pi);
        bizPackageInfoService.delByPK(oldPackageId);
        if (pi.getId() == null) throw new BizException(SYSTEM_ERROR, "数据错误，请重试(packageId为null)");
        bizPackageClassService.updatePackageIdByTCIdAndPid(teacherClassId, oldPackageId, pi.getId());
        return pi.getId();
    }

    //修改包的状态
    @Transactional(rollbackFor = Throwable.class)
    public int updatePackageStatus(long packageClassId, byte status) {
        PackageClass record = new PackageClass();
        record.setId(packageClassId);
        record.setStatus(status);
        return bizPackageClassService.updateByPK(record);
    }

    //获取班级列表
    public List<TeacherClassVo> getClassList(long teacherId, boolean cacheFirst) throws Exception {
        List<TeacherClassVo> voList = null;
        if (cacheFirst) {
            voList = redisCacheService.getTeacherClassList(teacherId);
        }
        if (CollectionUtils.isNotEmpty(voList)) return voList;

        ClassDomainQuery query = new ClassDomainQuery();
        query.setTeacherId(teacherId);
        query.setPage(1);
        query.setLimit(100);
        List<TeacherClass> tcList = bizTeacherClassService.getList(query);
        if (CollectionUtils.isEmpty(tcList)) return new ArrayList<>();

        voList = new ArrayList<>(tcList.size());
        Set<Long> classIds = new HashSet<>();
        for (TeacherClass o : tcList) {
            TeacherClassVo vo = new TeacherClassVo();
            BeanUtilsExt.propertyUtilsCopy(vo, o);
            voList.add(vo);
            classIds.add(o.getClassId());
        }
        Map<Long, ClassDomain> map = bizClassService.getMapByIds(classIds);

        StringBuilder sb = new StringBuilder();
        if (MapUtils.isNotEmpty(map)) {
            for (TeacherClassVo vo : voList) {
                ClassDomain cd = map.get(vo.getClassId());
                if (cd == null) continue;

                vo.setGrade(cd.getGrade());
                vo.setClassNo(cd.getClassNo());
                vo.setClassStudentNum(cd.getStudentNum());
                vo.setTermStartTime(cd.getTermStartTime());
                vo.setTermEndTime(cd.getTermEndTime());
                vo.setClassId(vo.getId());

                sb.append(vo.getSubjectId()).append(",");
            }
        }

        Set<Long> subjectIds = BizUtil.strToLongs(sb.toString());
        Map<Long, Subject> subjectMap = bizSubjectService.getMapByIds(subjectIds);
        if (MapUtils.isNotEmpty(subjectMap)) {
            for (TeacherClassVo vo : voList) {
                if (StringUtils.isBlank(vo.getSubjectId())) continue;

                Set<Long> sids = BizUtil.strToLongs(vo.getSubjectId());
                if (CollectionUtils.isEmpty(sids)) continue;

                List<SubjectVo> subList = new ArrayList<>(sids.size());
                for (long sid : sids) {
                    Subject sub = subjectMap.get(sid);
                    if (sub == null) continue;
                    subList.add(new SubjectVo(sid, sub.getSubjectName()));
                }
                vo.setSubjectList(subList);
            }
        }

        if (CollectionUtils.isNotEmpty(voList)) {
            redisCacheService.setTeacherClassList(teacherId, voList);
        }

        return voList;
    }

    public Teacher getByMobileNo(String mobileNo) {
        return teacherMapper.selectByUnique(mobileNo);
    }

    public Teacher getById(long id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    public TeacherVo getVoById(long id) throws Exception {
        Teacher teacher = getById(id);
        if (teacher == null) return null;

        TeacherVo vo = new TeacherVo();
        BeanUtilsExt.propertyUtilsCopy(vo, teacher);
        School school = bizSchoolService.getById(teacher.getSchoolId());
        if (school != null) {
            vo.setSchoolName(school.getSchoolName());
        }
        return vo;
    }

    public PageList<TeacherVo> getTeacherInfo(TeacherQuery teacherQuery) throws Exception {

        if (teacherQuery == null) throw new BizException("教师查询错误");
        if (teacherQuery.getClassIdstr() != null && teacherQuery.getClassIdstr() != "") {
            teacherQuery = initTeacherQuery(teacherQuery);
        }

        long count = count(teacherQuery);
        if (count <= 0) return PageList.newInstance();

        List<Teacher> list = teacherMapper.queryTeacherList(teacherQuery);
        List<TeacherVo> voList = new ArrayList<>();
        Map<Long, String> subjectMap = bizSubjectService.getAllSubjectInfo();
        Set<Long> schoolIds = new HashSet<>();

        for (Teacher s : list) {
            schoolIds.add(s.getSchoolId());

            TeacherVo teacherVo = new TeacherVo();
            BeanUtilsExt.copyProperties(teacherVo, s);
            //根据科目Id查询Name
            changeSubIdToName(teacherVo, s, subjectMap);
            voList.add(teacherVo);
        }

        Map<Long, School> schoolMap = bizSchoolService.getMapByIds(schoolIds);

        for (TeacherVo tv : voList) {
            School school = schoolMap.get(tv.getSchoolId());
            tv.setSchoolName(school != null ? school.getSchoolName() : "");
            tv.setClassDomains(bizClassService.getClassInfoByTeacherId(tv.getId()));
        }
        return new PageList<>(count, voList);
    }

    private TeacherQuery initTeacherQuery(TeacherQuery teacherQuery) {
        Set<Long> classIds = BizUtil.strToLongs(teacherQuery.getClassIdstr());

        Set<Long> teacherIds = bizTeacherClassService.queryTeacherIdsByClass(classIds);
        teacherQuery.setTeacherIds(teacherIds);
        return teacherQuery;
    }

    private void changeSubIdToName(TeacherVo teacherVo, Teacher teacher, Map<Long, String> subjectMap) {

        if (subjectMap.size() <= 0 || teacher.getSubjectId() == null || "".equals(teacher.getSubjectId())) {
            teacherVo.setSubjectName("-");
            return;
        }
        if (!teacher.getSubjectId().contains(",")) {
            teacherVo.setSubjectName(subjectMap.get(Long.parseLong(teacher.getSubjectId())));
        } else {
            String[] ids = teacher.getSubjectId().split(",");
            StringBuilder names = new StringBuilder();
            for (int i = 0; i < ids.length; i++) {
                if (i == ids.length - 1) {
                    names.append(subjectMap.get(Long.parseLong("id")));
                } else {
                    names.append(subjectMap.get(Long.parseLong("id")) + "|");
                }
            }
        }
    }

    public long count(TeacherQuery teacherQuery) {
        return teacherMapper.count(teacherQuery);
    }

    public void updateByPK(Teacher teacher) {
        teacherMapper.updateByPrimaryKeySelective(teacher);
    }

    public TeacherVo queryTeacherInfoById(Long teacherId) throws Exception {
        Map<Long, String> subjectMap = bizSubjectService.getAllSubjectInfo();

        Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);

        School school = bizSchoolService.getById(teacher.getSchoolId());

        List<ClassDomain> classDomains = bizClassService.getClassInfoByTeacherId(teacherId);
        if (classDomains.size() <= 0) throw new BizException(SYSTEM_ERROR, "查询老师所在班级异常");

        TeacherVo teacherVo = new TeacherVo();
        BeanUtilsExt.copyProperties(teacherVo, teacher);
        teacherVo.setSubjectName(subjectMap.get(Long.parseLong(teacher.getSubjectId())));
        teacherVo.setSchoolName(school.getSchoolName());
        teacherVo.setClassDomains(classDomains);
        return teacherVo;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateTeacherStatus(StatusDomain statusDomain) throws Exception {
        if (teacherMapper.updateStatus(statusDomain) <= 0) return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean addTeacherInfoService(TeacherAddVo teacherAddVo) throws Exception {
        if (addTeacherService(teacherAddVo)) {
            Teacher teacher = getByMobileNo(teacherAddVo.getMobileNo());
            teacherAddVo.setId(teacher.getId());
            List<TeacherClass> teacherClasses = setUpTeacherClassInfo(teacherAddVo);
            if (teacherClasses.size() <= 0) throw new BizException("新增教师未填写班级信息");
            if (bizTeacherClassService.addTeacherClassInfo(teacherClasses)) return true;
        }
        return false;
    }


    @Transactional(rollbackFor = Throwable.class)
    public Boolean addTeacherService(TeacherAddVo teacherAddVo) throws Exception {
        Teacher teacher = new Teacher();
        BeanUtilsExt.copyProperties(teacher, teacherAddVo);
        if (teacherMapper.insertSelective(teacher) <= 0) return false;
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteTeacherService(Long id) throws Exception {
        if (teacherMapper.deleteByPrimaryKey(id) <= 0) return false;
        bizTeacherClassService.deleteOriTeacherClassInfo(id);
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateTeacherInfoService(TeacherAddVo teacherAddVo) throws Exception {


        Set<Long> OriClassIds = bizTeacherClassService.queryClassIdsByTeacherId(teacherAddVo.getId());
        Set<Long> UpdateClassIds = BizUtil.strToLongs(teacherAddVo.getClassIds());
        Map<String, Set<Long>> finalData = compareSet(OriClassIds, UpdateClassIds);

        if (updateTeacherService(teacherAddVo)) {
            if (OriClassIds.size() == UpdateClassIds.size()) {
                if (finalData.get("diff").size() == 0) {
                    return true;
                } else if (finalData.get("equal").size() == 0) {  //更新信息修改班级全修改
                    //删除老师之前绑定关系
                    bizTeacherClassService.deleteOriTeacherClassInfo(teacherAddVo.getId());
                    List<TeacherClass> teacherClasses = setUpTeacherClassInfo(teacherAddVo);
                    if (teacherClasses.size() <= 0) throw new BizException("error", "修改教师未填写班级信息");
                    if (bizTeacherClassService.addTeacherClassInfo(teacherClasses)) return true;
                } else {
                    bizTeacherClassService.deleteOriByRetain(teacherAddVo.getId(), finalData.get("equal"));
                    List<TeacherClass> teacherClasses = NewAddClassIds(finalData.get("diff"), teacherAddVo);
                    if (teacherClasses.size() <= 0) throw new BizException("修改教师未填写班级信息");
                    if (bizTeacherClassService.addTeacherClassInfo(teacherClasses)) return true;
                }
            } else if (OriClassIds.size() < UpdateClassIds.size()) {
                if (finalData.get("equal").size() == 0) {
                    bizTeacherClassService.deleteOriTeacherClassInfo(teacherAddVo.getId());
                    List<TeacherClass> tc = setUpTeacherClassInfo(teacherAddVo);
                    if (tc.size() <= 0) throw new BizException("修改教师未填写班级信息");
                    if (bizTeacherClassService.addTeacherClassInfo(tc)) return true;
                } else {
                    bizTeacherClassService.deleteOriByRetain(teacherAddVo.getId(), finalData.get("equal"));
                    List<TeacherClass> teacherClasses = NewAddClassIds(finalData.get("diff"), teacherAddVo);
                    if (teacherClasses.size() <= 0) throw new BizException("修改教师未填写班级信息");
                    if (bizTeacherClassService.addTeacherClassInfo(teacherClasses)) return true;
                }
            } else {
                Map<String, Set<Long>> finalData1 = compareSet(OriClassIds, UpdateClassIds);

                if (finalData1.get("equal").size() == 0) {
                    bizTeacherClassService.deleteOriTeacherClassInfo(teacherAddVo.getId());
                    List<TeacherClass> teacherClasses = setUpTeacherClassInfo(teacherAddVo);
                    if (teacherClasses.size() <= 0) throw new BizException("修改教师未填写班级信息");
                    if (bizTeacherClassService.addTeacherClassInfo(teacherClasses)) return true;
                } else if (finalData1.get("equal").size() == UpdateClassIds.size()) {
                    bizTeacherClassService.deleteOriByRetain(teacherAddVo.getId(), finalData1.get("equal"));
                    return true;
                } else {
                    bizTeacherClassService.deleteOriByRetain(teacherAddVo.getId(), finalData1.get("equal"));
                    Set<Long> u = new HashSet<>(UpdateClassIds);
                    u.removeAll(finalData1.get("equal"));
                    List<TeacherClass> teacherClasses = NewAddClassIds(u, teacherAddVo);
                    if (teacherClasses.size() <= 0) throw new BizException("error", "修改教师未填写班级信息");
                    if (bizTeacherClassService.addTeacherClassInfo(teacherClasses)) return true;
                }
            }
        }
        return false;
    }


    private List<TeacherClass> NewAddClassIds(Set<Long> clsIds, TeacherAddVo teacherAddVo) {
        List<TeacherClass> tcs = new ArrayList<>();
        for (Long cId : clsIds) {
            TeacherClass teacherClass = new TeacherClass();
            teacherClass.setTeacherId(teacherAddVo.getId());
            teacherClass.setSubjectId(teacherAddVo.getSubjectId());
            teacherClass.setClassId(cId);
            tcs.add(teacherClass);
        }
        return tcs;
    }

    //比较两个Set
    private Map<String, Set<Long>> compareSet(Set<Long> oriClassIds, Set<Long> updateClassIds) {
        Map<String, Set<Long>> finalData = new HashMap<>();
        Set<Long> eids = new HashSet<>(updateClassIds);
        Set<Long> uids = new HashSet<>(updateClassIds);

        eids.removeAll(oriClassIds);

        finalData.put("diff", eids);

        uids.removeAll(eids);
        finalData.put("equal", uids);

        return finalData;
    }

    private List<TeacherClass> setUpTeacherClassInfo(TeacherAddVo teacherAddVo) {
        String classIds = teacherAddVo.getClassIds();
        Set<Long> AddClasssIds = BizUtil.strToLongs(classIds);
        List<TeacherClass> tcs = new ArrayList<>();
        for (Long classId : AddClasssIds) {
            TeacherClass teacherClass = new TeacherClass();
            teacherClass.setTeacherId(teacherAddVo.getId());
            teacherClass.setSubjectId(teacherAddVo.getSubjectId());
            teacherClass.setClassId(classId);
            teacherClass.setDelFlag(false);
            tcs.add(teacherClass);
        }
        return tcs;
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean updateTeacherService(TeacherAddVo teacherAddVo) throws Exception {
        Teacher teacher = new Teacher();
        BeanUtilsExt.copyProperties(teacher, teacherAddVo);

        boolean isUpdateMobile = false;
        if (StringUtils.isNotBlank(teacherAddVo.getMobileNo())) {
            Teacher dbTeacher = getById(teacher.getId());
            if (!dbTeacher.getMobileNo().equals(teacherAddVo.getMobileNo())) {
                isUpdateMobile = true;
            }
        }
        if (teacherMapper.updateByPrimaryKeySelective(teacher) <= 0) return false;

        if (isUpdateMobile) {
            //如果修改了手机号，需要重新生成token，如果已登录需要重新登录
            redisCacheService.setTeacherToken(teacher.getId(), BizUtil.genToken(new UserVo(teacher.getId(), UserEnum.TEACHER.getType())), BizConstant.UPDATE_TOKEN_WAY_UPDATE_MOBILE);
        }
        return true;
    }

    //根据老师ID查询习题列表
    public PageList<ExerciseVo> getExerciseInfoByTeacherId(long teacherId, Integer page, Integer limit) throws Exception {
        BaseQuery query = new BaseQuery();
        query.setId(teacherId);
        query.setPage(page);
        query.setLimit(limit);
        return bizExerciseService.getExerciseInfoByTeacherId(query);
    }

    public PageList<ExerciseVo> getEInfoByTId(Long teacherId, Integer page, Integer limit, Byte type) throws Exception {
        ExerciseQuery exerciseQuery = new ExerciseQuery();

        exerciseQuery.setId(teacherId);
        exerciseQuery.setPage(page);
        exerciseQuery.setLimit(limit);
        exerciseQuery.setType(type);
        return bizExerciseService.getEInfoByTId(exerciseQuery);
    }

    //根据TCP查询习题列表
    public PageList<ExerciseVo> getExerciseInfoByTCPId(Long teacherId, Long classId, Long packageId, Integer page, Integer limit) throws Exception {
        Long pcId = bizPackageClassService.queryPCIdByTCPId(teacherId, classId, packageId);
        Set<Long> exerciseIds = bizPackageExerciseService.getExerciseIdsByPCId(pcId);
        if (exerciseIds == null || exerciseIds.size() == 0) return PageList.newInstance();
        BaseQuery query = new BaseQuery();
        query.setId(null);
        query.setIds(exerciseIds);
        query.setPage(page);
        query.setLimit(limit);

        return bizExerciseService.getExerciseInfo(query);
    }

    //根据包ID查询习题
    public PageList<ExerciseVo> getExerciseInfoByPackageId(Long teacherClassId, Long packageId) throws Exception {
        return bizExerciseService.getExerciseInfoByPackageId(packageId, teacherClassId);
    }

    //根据packageClassId查询习题
    public PageList<ExerciseVo> getExerciseInfoByPCId(BaseQuery query) throws Exception {
        return bizExerciseService.getExerciseInfoByPCId(query);
    }

    public PageList<ExerciseVo> getEInfoByPCId(Long packageClassId, Integer pageNo, Integer pageSize, Byte type) throws Exception {

        ExerciseQuery exerciseQuery = new ExerciseQuery();
        exerciseQuery.setId(packageClassId);
        exerciseQuery.setPage(pageNo);
        exerciseQuery.setLimit(pageSize);
        exerciseQuery.setType(type);
        return bizExerciseService.getEInfoByPCId(exerciseQuery);
    }


    @Transactional(rollbackFor = Throwable.class)
    public void sendExerciseToClass(long teacherId, String teacherClassIds, String exerciseIds, String packageName) throws Exception {

        ParamCheckUtil.checkNotEmptyStr(teacherClassIds, exerciseIds, packageName);
        teacherClassIds = teacherClassIds.trim();
        exerciseIds = exerciseIds.trim();
        packageName = packageName.trim();
        //插入习题、包、班级之间关系
        Set<Long> tcIds = BizUtil.strToLongs(teacherClassIds);
        Set<Long> exIds = BizUtil.strToLongs(exerciseIds);

        if (exIds == null || exIds.size() == 0) throw new BizException("exercise is null");

        for (long tcId : tcIds) {

            //init  package_info
            PackageInfo pi = bizPackageInfoService.getOneByTeacherAndName(tcId, packageName);
            Long pid = pi == null ? bizPackageInfoService.add(teacherId, tcId, packageName) : pi.getId();

            if (pid == null) throw new BizException(VALUE_INVALID, "packageId为空，数据不正确");

            //init package_class
            PackageClass packageClass = new PackageClass();
            packageClass.setPackageId(pid);
            packageClass.setTeacherClassId(tcId);

            Long pcId = bizPackageClassService.checkPCMapper(packageClass);
            if (pcId == null) {
                bizPackageClassService.addPackageClassMapper(packageClass);
                pcId = packageClass.getId();
            }

            for (long exid : exIds) {

                //查询包内是否有相同习题
                if (bizPackageExerciseService.checkExerciseRepe(exid, pcId)) {
                    log.info("this exercise in package is exit" + "--exId:" + exid + "--packageClassId:" + pcId);
                    throw new BizException(FILE_ALREADY_EXIST_E, "包内存在相同习题");
                }
                //init package_exercise
                if (bizPackageExerciseService.insertPackageExercise(pcId, exid) <= 0) {
                    log.info("init package_exercise mapper error");
                    throw new BizException("插入包与习题关系表失败");
                }

                //init exercise_class
                ExerciseClass exerciseClass = new ExerciseClass();
                exerciseClass.setExerciseId(exid);
                exerciseClass.setTeacherClassId(tcId);
                bizExerciseClassService.addExerciseClassMapper(exerciseClass);
            }
        }
    }

    //教师下发习题 创建单元测试
    @Transactional(rollbackFor = Throwable.class)
    public UnitTestVo createUnitTest(String exerciseIds, long packageClassId) throws Exception {
        UnitTestVo ut = bizUnitTestService.createUnitTest(packageClassId, null);
        if (!isValidId(ut.getId())) throw new BizException(VALUE_INVALID, "数据不正确, unitTestId=null");
        bizUnitTestExerciseService.insertBatch(ut.getUnitTestId(), exerciseIds);
        return ut;
    }

    //获取资源包下的随堂测试列表
    public List<UnitTestVo> getUnitTestList(long packageClassId) throws Exception {
        List<UnitTestVo> list = bizUnitTestService.getVoListByPCId(packageClassId);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(o -> {
                List<ExerciseVo> quesList = bizUnitTestExerciseService.getExerciseVoListByUTId(o.getUnitTestId(), false);
                o.setExerciseList(quesList);
            });
        }
        return list;
    }

}
