package com.booksroo.classroom.web.controller.pc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.common.domain.ExerciseDomain;
import com.booksroo.classroom.common.domain.FileResourceChild;
import com.booksroo.classroom.common.enums.FileEnum;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.BaseQuery;
import com.booksroo.classroom.common.query.PackageInfoQuery;
import com.booksroo.classroom.common.query.ResourceQuery;
import com.booksroo.classroom.common.request.UploadQuestionRequest;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.FileUtil;
import com.booksroo.classroom.common.util.ParamCheckUtil;
import com.booksroo.classroom.common.vo.*;
import com.booksroo.classroom.service.*;
import com.booksroo.classroom.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.booksroo.classroom.common.constant.PromptConstant.FILE_TYPE_INCORRECT;

/**
 * @author liujianjian
 * @date 2018/6/3 0:42
 */
@RestController
@RequestMapping("/pc/teacher")
public class TeacherController extends BaseController {

    @Autowired
    private BizTeacherService bizTeacherService;
    @Autowired
    private BizFileResourceService bizFileResourceService;
    @Autowired
    private BizPackageResourceService bizPackageResourceService;
    @Autowired
    private BizExerciseService bizExerciseService;

    //获取登录用户信息
    @RequestMapping("/getUserInfo")
    public Object getUserInfo(HttpServletRequest request) throws Exception {
        return success(getLoginTeacher(request));
    }

    //获取班级列表
    @RequestMapping("/getClassList")
    public Object getClassList(HttpServletRequest request) throws Exception {
        TeacherVo user = getLoginTeacher(request);
        return success(BizUtil.orderClassList(bizTeacherService.getClassList(user.getId(), true)));
    }

    //上传文件资源
    @RequestMapping("/uploadFile")
    public Object uploadFile(HttpServletRequest request, String title, Long classId) throws Exception {
        if (StringUtils.isBlank(title)) throw new BizException("名称不能为空");

        TeacherVo user = getLoginTeacher(request);
        List<MultipartFile> files = getUploadFiles(request);
        List<FileResourceVo> data = new ArrayList<>();
        for (MultipartFile file : files) {
            //取得当前上传文件的文件名称
            String originName = file.getOriginalFilename();
            String suffix = FileUtil.getFileSuffix(originName);
            if (StringUtils.isBlank(suffix))
                throw new BizException(FILE_TYPE_INCORRECT, "未获取到文件后缀, 请选择正确的文件");

            title = title.contains(".") ? title.substring(0, title.lastIndexOf(".")) : title;
            String fileName = title.trim() + suffix.trim();
            if (bizTeacherService.isResourceNameExist(user.getId(), fileName)) {
                throw new BizException("您的资源库里已存在与该名称相同的文件，请重新命名");
            }
            List<String> urls = bizTeacherService.uploadFile(file.getInputStream(), fileName, user.getId(), null);
            long resourceId = 0;
            List<FileResourceChild> list = new ArrayList<>();
            for (String url : urls) {
                String u = url.substring(0, url.indexOf("?"));
                u = u.substring(u.lastIndexOf("/") + 1);
                String[] arr = u.split("_");
                if (resourceId == 0)
                    resourceId = Long.parseLong(arr[2]);
                int orderNo = Integer.parseInt(arr[3]);
                FileResourceChild frc = new FileResourceChild(resourceId, url, orderNo);
                list.add(frc);
            }
            FileResourceVo vo = new FileResourceVo(user.getId(), resourceId, fileName, FileEnum.getType(suffix), list);
            data.add(vo);
        }
        return success(data);
    }

    //发送资源文件到班级
    @RequestMapping("/sendToClass")
    public Object sendToClass(HttpServletRequest request, String classIds, String resourceIds, String packageName, String packageId) throws Exception {
        TeacherVo user = getLoginTeacher(request);
        bizTeacherService.sendToClass(user.getId(), classIds, resourceIds, packageName, packageId);
        return success();
    }

    @RequestMapping("/isResourceTypeExist")
    public Object isResourceTypeExist(String packageName, String types) {
        return success();
    }

    //查询资源文件列表
    @RequestMapping("/getResourceList")
    public Object getResourceList(HttpServletRequest request, ResourceQuery query) throws Exception {
        query.setTeacherId(getLoginTeacher(request).getId());
        return tableResp(bizTeacherService.getResourceList(query));
    }

    //获取班级下的包列表
    @RequestMapping("/getPackageList")
    public Object getPackageList(HttpServletRequest request, PackageInfoQuery query) throws Exception {
        if (query.getClassId() == null || query.getClassId() <= 0) return tableResp(PageList.newInstance());
        PageList<PackageVo> pageList = bizTeacherService.getPackageListByClass(query);
        BizUtil.orderPackageList(pageList.getRows());
        return tableResp(pageList);
    }

    //添加资源文件到包
    @RequestMapping("/addPackageResource")
    public Object addPackageResource(long classId, long packageClassId, String resourceIds) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(resourceIds);
        bizTeacherService.addResourceByPackage(classId, packageClassId, BizUtil.strToArr(resourceIds));
        return success();
    }

    //资源文件的详情
    @RequestMapping("/resourceDetail")
    public Object resourceDetail(Long packageClassId, Long teacherClassId, long resourceId) {

        return success(bizTeacherService.getResourceDetail(packageClassId, teacherClassId, resourceId));
    }

    //资源文件名是否存在
    @RequestMapping("/isResourceNameExist")
    public Object isResourceNameExist(HttpServletRequest request, String title) throws Exception {
        return success(bizTeacherService.isResourceNameExist(getLoginTeacher(request).getId(), title));
    }

    //删除资源文件
    @RequestMapping("/delResource")
    public Object delResource(HttpServletRequest request, Long classId, String resourceIds) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(resourceIds);
        TeacherVo user = getLoginTeacher(request);

        String[] arr = resourceIds.contains(";") ? resourceIds.split(";") : resourceIds.split(",");
        for (String s : arr) {
            if (StringUtils.isBlank(s)) continue;

            String[] split = s.split("_");

            long rid = Long.parseLong(split[0].trim());
            String pids = null;
            if (split.length > 1 && StringUtils.isNotBlank(split[1])) {
                pids = split[1];
            }
            bizTeacherService.delResource(user, classId, rid, pids);
        }
        return success();
    }

    //根据资源id获取所属的包id
    @RequestMapping("/getPCIdsByResourceId")
    public Object isResourceHasSend(long resourceId) {
        return success(bizPackageResourceService.getPCIdsByResourceId(resourceId));
    }

    //修改包名
    @RequestMapping("/updatePackageName")
    public Object updatePackageName(HttpServletRequest request, long packageId, long classId, String packageName) throws Exception {
        return success(bizTeacherService.updateClassPackageName(getLoginTeacher(request).getId(), classId, packageId, packageName));
    }

    //修改资源文件名
    @RequestMapping("/updateResourceName")
    public Object updateResourceName(HttpServletRequest request, long resourceId, String resourceName) throws Exception {

        bizFileResourceService.updateResourceName(getLoginTeacher(request).getId(), resourceId, resourceName);
        return success();
    }

    @RequestMapping("/delPackage")
    public Object delPackage(HttpServletRequest request, long classId, String packageIds) throws Exception {
        if (StringUtils.isBlank(packageIds)) return success();

        Set<Long> ids = BizUtil.strToLongs(packageIds);
        for (long pid : ids) {
//            bizTeacherService.delClassPackage(getLoginTeacher(request), classId, pid);
        }
        return success();
    }

    //修改密码
    @RequestMapping("/updatePwd")
    public Object updatePwd(HttpServletRequest request, String oldPwd, String newPwd) throws Exception {
        bizTeacherService.updatePwd(getLoginTeacher(request), oldPwd, newPwd);
        return success();
    }

    /*----习题模块 全部资源------*/
    //上传习题信息
    @RequestMapping(value = "/uploadExercise", method = RequestMethod.POST)
    public Object uploadExercise(HttpServletRequest request, String uploadData) throws Exception {
        ParamCheckUtil.checkNotEmptyStr(uploadData);
        UploadQuestionRequest req = JSON.parseObject(uploadData, UploadQuestionRequest.class);
        if (bizExerciseService.uploadExerciseInfo(getLoginTeacher(request).getId(), req)) return success();
        return fail("-1", "上传习题失败");
    }

    //发送习题资源给班级
    @RequestMapping("/sendExerciseToClass")
    public Object sendExerciseToClass(HttpServletRequest request, String classIds, String resourceIds, String packageName) throws Exception {
        if (StringUtils.isBlank(resourceIds)) return success();
        TeacherVo user = getLoginTeacher(request);
        bizTeacherService.sendExerciseToClass(user.getId(), classIds, resourceIds, packageName);
        return success();
    }

    //获取教师全部习题by teacherID
    @RequestMapping("/getExerciseByTeacherID")
    public Object getExerciseListByTeacherID(HttpServletRequest request, BaseQuery qry) throws Exception {
        TeacherVo user = getLoginTeacher(request);
        return tableResp(bizTeacherService.getExerciseInfoByTeacherId(user.getId(), qry.getPage(), qry.getLimit()));
    }

    //全部资源中删除习题
    @RequestMapping("/deleteExerciseOnAll")
    public Object deleteExerciseOnAll(Long exerciseId) {
        if (exerciseId == null) return fail("-1", "未选择习题");
        if (bizExerciseService.delExerciseOnAll(exerciseId)) return success();
        return fail("-1", "delete error");
    }

    @RequestMapping("/getExerciseInfoById")
    public Object getExerciseById(Long exerciseId) {
        return success(bizExerciseService.getExerciseInfoById(exerciseId));
    }

    /*---班级资源包下----*/
    // 根据 teacherId + classId + packageId 查询习题列表
    @RequestMapping("/getExerciseByClassId")
    public Object getPackageClassIdByTCAndP(HttpServletRequest request, Long classId, Long packageId, Integer page, Integer limit) throws Exception {
        TeacherVo user = getLoginTeacher(request);
        if (user == null) throw new BizException("teacher info is null");
        return tableResp(bizTeacherService.getExerciseInfoByTCPId(user.getId(), classId, packageId, page, limit));
    }

    //获取习题资源by packageClassId
    @RequestMapping("/getExerciseByPackageClassId")
    public Object getExerciseListByPCId(Long packageClassId, Integer page, Integer limit) throws Exception {
        BaseQuery query = new BaseQuery();
        query.setId(packageClassId);
        query.setPage(page);
        query.setLimit(limit);
        if (query.getId() == null) return PageList.newInstance();
        return tableResp(bizTeacherService.getExerciseInfoByPCId(query));
    }

    // 删除班级资源包下习题()
    @RequestMapping("/deleteExerciseOnCP")
    public Object deleteExerciseOnCP(Long packageClassId, Long exerciseId) throws Exception {
        if (bizExerciseService.delExerciseOnCP(packageClassId, exerciseId)) return success();
        return fail("-1", "delete error");
    }

    //获取习题资源by packageID
    @RequestMapping("/getExerciseByPackageID")
    public Object getExerciseInfoByPackageID(Long teacherClassId, Long packageId) throws Exception {
        return tableResp(bizTeacherService.getExerciseInfoByPackageId(teacherClassId, packageId));
    }

    //获取班级下全部习题资源
    @RequestMapping("/getExerciseByClass")
    public Object getExerciseByClass(Long classId, Integer page, Integer limit) throws Exception {
        return tableResp(bizExerciseService.getExerciseInfoByTCId(classId, page, limit));
    }

    //删除班级下习题资源
    @RequestMapping("/deleteExerciseOnClass")
    public Object deleteExerciseOnClass(Long teacherClassId, Long exerciseId) throws BizException {
        if (bizExerciseService.delExerciseOnTC(teacherClassId, exerciseId)) return success();
        return fail("-1", "delete ExerciseOnClass is fail");
    }
}
