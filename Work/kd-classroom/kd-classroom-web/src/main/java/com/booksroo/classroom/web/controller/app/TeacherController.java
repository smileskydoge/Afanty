package com.booksroo.classroom.web.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.common.domain.FileResourceChild;
import com.booksroo.classroom.common.enums.FileEnum;
import com.booksroo.classroom.common.query.BaseQuery;
import com.booksroo.classroom.common.query.PackageInfoQuery;
import com.booksroo.classroom.common.query.ResourceQuery;
import com.booksroo.classroom.common.request.AppBaseRequest;
import com.booksroo.classroom.common.request.UploadNotesRequest;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.ParamCheckUtil;
import com.booksroo.classroom.common.vo.*;
import com.booksroo.classroom.service.*;
import com.booksroo.classroom.web.controller.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author liujianjian
 * @date 2018/6/4 17:08
 */
@RequestMapping("/app/teacher")
@RestController("appTeacherController")
public class TeacherController extends BaseController {

    @Autowired
    private BizTeacherService bizTeacherService;
    @Autowired
    private BizStatisticsService bizStatisticsService;
    @Autowired
    private BizStudentExerciseSubmitService bizStudentExerciseSubmitService;

    //获取班级和学科信息
    @RequestMapping("/getClassSubjectInfo")
    public Object getClassSubjectInfo(AppBaseRequest ar) throws Exception {
        return success(bizTeacherService.getClassList(ar.getUserId(), true));
    }

    //获取班级下的包列表
    @RequestMapping("/getPackageList")
    public Object getPackageList(AppBaseRequest ar, long classId) throws Exception {
        PackageInfoQuery query = new PackageInfoQuery();
        query.setClassId(classId);
        query.setPage(ar.getPageNo());
        query.setLimit(ar.getPageSize());

        PageList<PackageVo> pageList = bizTeacherService.getPackageListByClass(query);
        BizUtil.orderPackageList(pageList.getRows());

        return success(pageList);
    }

    //获取资源文件列表
    @RequestMapping("/getResourceList")
    public Object getResourceList(HttpServletRequest request, AppBaseRequest ar, Long classId, Integer resourceType, Long packageId, Boolean listAll, Boolean mergeByType) throws Exception {
        ResourceQuery query = new ResourceQuery();
        query.setPage(ar.getPageNo());
        query.setLimit(ar.getPageSize());
        query.setResourceType(resourceType);
        query.setNeedReturnChildList(true);
        query.setTeacherId(getLoginTeacher(request).getId());

        boolean merge = mergeByType != null && mergeByType;

        if (merge) query.noLimit();

        PageList<FileResourceVo> pageList = null;
        if (listAll != null && listAll) {
//            query.setNeedReturnChildList(true);
        } else if (packageId != null && packageId > 0) {
            query.setPackageId(packageId);
//            query.setNeedReturnChildList(true);
        } else {
            query.setClassId(classId);
        }

//        if (merge) query.setNeedReturnChildList(true);

        pageList = bizTeacherService.getResourceList(query);
        if (CollectionUtils.isEmpty(pageList.getRows())) {
            return success(pageList);
        }

        BizUtil.sortOssFile(pageList.getRows());
        BizUtil.replaceOSSHost(pageList.getRows());

        if (!merge) return success(pageList);

        List<FileResourceChild> imgList = new ArrayList<>();
        FileResourceVo imgVo = null;
        List<FileResourceVo> resultList = new ArrayList<>();
        for (FileResourceVo vo : pageList.getRows()) {
            if (vo.getResourceType() == null) continue;
            if (vo.getResourceType().equals(FileEnum.IMAGE.getType())) {
                imgList.addAll(vo.getChildResourceList());
                if (imgVo == null) imgVo = vo;
            }
            if (vo.getResourceType().equals(FileEnum.PPT.getType()))
                resultList.add(vo);
        }
        if (imgVo != null) {
            imgVo.setChildResourceList(imgList);
            resultList.add(imgVo);
        }
        BizUtil.sortOssFile(resultList);
        pageList.setTotal(resultList.size());
        pageList.setRows(resultList);
        return success(pageList);
    }

    //修改资源包的状态
    @RequestMapping("/updatePackageStatus")
    public Object updatePackageStatus(long packageClassId, int status) {
        bizTeacherService.updatePackageStatus(packageClassId, (byte) status);
        return success();
    }

    //结束上课
    @RequestMapping("/endClass")
    public Object endClass(HttpServletRequest request, long packageClassId, Long resourceId, Integer currentPPTNo) throws Exception {
        bizTeacherService.endClass(getLoginTeacher(request), packageClassId, resourceId, currentPPTNo);
        return success();
    }

    //上传笔记信息
    @RequestMapping("/uploadFile")
    public Object uploadFile(HttpServletRequest request, String fileArr) throws Exception {

        ParamCheckUtil.checkNotEmptyStr(fileArr);
        fileArr = BizUtil.decodeUrlJsonArr(fileArr);
        //[{fileName:文件名.后缀, fileUrl:文件地址, childResourceId:子资源id, resourceId:父资源的id, packageClassId:包id，1.3返回的，createTime:图片创建时间戳格式long型}]
        JSONArray arr = JSON.parseArray(fileArr);
        List<UploadNotesRequest> list = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = JSON.parseObject(arr.getString(i));
            String fileName = obj.getString("fileName");
            String fileUrl = obj.getString("fileUrl");
            long childResourceId = obj.getLong("childResourceId");
            long packageClassId = obj.getLong("packageClassId");
            long resourceId = obj.getLong("resourceId");
            long createTime = obj.getLong("createTime");

            ParamCheckUtil.noNegativeLong(packageClassId, childResourceId, resourceId, createTime);

//            byte[] bytes = FileUtil.genBase64StrByBytes(imgStr);

            UploadNotesRequest req = new UploadNotesRequest();
            req.setFileUrl(fileUrl);
            req.setChildResourceId(childResourceId);
            req.setFileName(fileName);
            req.setPackageClassId(packageClassId);
            req.setResourceId(resourceId);
            req.setCreateTime(createTime);

            list.add(req);
        }
        bizTeacherService.uploadNotesFile(getLoginTeacher(request).getId(), list);

        return success(arr.size());
    }

    //添加资源信息到包下
    @RequestMapping("/addPackageResource")
    public Object addPackageResource(HttpServletRequest request, String fileArr) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(fileArr);
        fileArr = BizUtil.decodeUrlJsonArr(fileArr);

        JSONArray arr = JSON.parseArray(fileArr);
        Map<Long, Map<String, String>> map = new HashMap<>();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = JSON.parseObject(arr.getString(i));
            String fileName = obj.getString("fileName");
            String fileUrl = obj.getString("fileUrl");

            ParamCheckUtil.checkNotEmptyStr(fileName, fileUrl);
//            long createTime = obj.getLong("createTime");
            long packageClassId = obj.getLong("packageClassId");

            Map<String, String> ssMap = map.get(packageClassId);
            if (ssMap == null) ssMap = new LinkedHashMap<>();

            ssMap.put(fileName, fileUrl);

            map.put(packageClassId, ssMap);
        }

        long teacherId = getLoginTeacher(request).getId();

        if (MapUtils.isEmpty(map)) return success();

        bizTeacherService.addNewResourceToPackageBatch(teacherId, map);
        return success();
    }

    //获取习题资源by packageID
    @RequestMapping("/getExerciseByPackageID")
    public Object getExerciseList(Long packageId, Long teacherClassId) throws Exception {
        if (packageId == null) return PageList.newInstance();
        BaseQuery query = new BaseQuery();
        query.setId(packageId);
        return bizTeacherService.getExerciseInfoByPackageId(packageId, teacherClassId);
    }

    //获取习题资源by packageClassId
    @RequestMapping("/getExerciseList")
    public Object getExerciseListByPCId(Long packageClassId, Integer pageNo, Integer pageSize) throws Exception {
        BaseQuery query = new BaseQuery();
        query.setId(packageClassId);
        query.setPage(pageNo);
        query.setLimit(pageSize);
        if (query.getId() == null) return PageList.newInstance();
        return success(bizTeacherService.getExerciseInfoByPCId(query));
    }

    //资源库获取习题列表  GET
    @RequestMapping("/getExerciseByCondition")
    public Object getExerciseByCondition(HttpServletRequest request, Integer pageNo, Integer pageSize, Boolean listAll, Long packageClassId, Byte type) throws Exception {
        TeacherVo user = getLoginTeacher(request);
        //是否为全部习题
        if (listAll || packageClassId == null) {
            return success(bizTeacherService.getEInfoByTId(user.getId(), pageNo, pageSize, type));
        } else {
            return success(bizTeacherService.getEInfoByPCId(packageClassId, pageNo, pageSize, type));
        }
    }

    //发送习题后创建单元测试
    @RequestMapping("/sendExerciseIds")
    public Object sendExerciseIds(String exerciseIds, long packageClassId) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(exerciseIds);
        return success(bizTeacherService.createUnitTest(exerciseIds, packageClassId));
    }

    //获取资源包下的随堂测试列表
    @RequestMapping("/getUnitTestList")
    public Object getUnitTestList(long packageClassId) throws Exception {
        List<UnitTestVo> list = bizTeacherService.getUnitTestList(packageClassId);
        return success(PageList.newInstance(list != null ? list.size() : 0, list, 1));
    }

    //获取统计
    @RequestMapping("/getStatisticsInfo")
    public Object getStatisticsInfo(Long packageClassId, long unitTestId, Boolean detail) throws Exception {
        List<StatisticsVo> list = bizStatisticsService.getVoListByUnitTestId(packageClassId, unitTestId, detail);
        return success(PageList.newInstance(list != null ? list.size() : 0, list, 1));
    }

    //获取统计详情
    @RequestMapping("/getStatisticsDetail")
    public Object getStatisticsDetail(long packageClassId, long unitTestExerciseId) throws Exception {
        return success(bizStudentExerciseSubmitService.countSubmitDetailByUTEId(packageClassId, unitTestExerciseId));
    }

    //获取随堂测试下某道题的学生提交情况
    @RequestMapping("/getUTESubmitInfo")
    public Object getUTESubmitInfo(long unitTestId, long exerciseId, Integer quesType) {
        List<UTESubmitVo> list = bizStatisticsService.getStuSubmitListByUTE(unitTestId, exerciseId);
        return success(PageList.newInstance(list != null ? list.size() : 0, list, 1));
    }

}
