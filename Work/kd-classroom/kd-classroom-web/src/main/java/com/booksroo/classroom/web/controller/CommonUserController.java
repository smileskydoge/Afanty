package com.booksroo.classroom.web.controller;

import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.booksroo.classroom.common.enums.UserEnum;
import com.booksroo.classroom.common.request.AppBaseRequest;
import com.booksroo.classroom.service.BizStudentService;
import com.booksroo.classroom.service.BizTeacherService;
import com.booksroo.classroom.web.third.AppTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liujianjian
 * @date 2018/6/14 11:33
 */
@RestController
@RequestMapping("/common/user")
public class CommonUserController extends BaseController {
    @Autowired
    private BizTeacherService bizTeacherService;
    @Autowired
    private BizStudentService bizStudentService;
    @Autowired
    private AppTokenService appTokenService;

    @RequestMapping("/updatePWD")
    public Object updatePWD(HttpServletRequest request, String userType, String oldPassword, String newPassword) throws Exception {
        if (UserEnum.isTeacher(userType)) {
            bizTeacherService.updatePwd(getLoginTeacher(request), oldPassword, newPassword);
        } else if (UserEnum.isStudent(userType)) {
            bizStudentService.updatePwd(getLoginStudent(request), oldPassword, newPassword);
        }
        return success();
    }

    @RequestMapping("/getOssAppToken")
    public Object getOssAppToken(String userType, AppBaseRequest req) throws Exception {
        AssumeRoleResponse.Credentials resp = appTokenService.getSTSResp(userType + "_" + req.getUserId());
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        respMap.put("StatusCode", "200");
        respMap.put("AccessKeyId", resp.getAccessKeyId());
        respMap.put("AccessKeySecret", resp.getAccessKeySecret());
        respMap.put("SecurityToken", resp.getSecurityToken());
        respMap.put("Expiration", resp.getExpiration());
        return respMap;
    }
}
