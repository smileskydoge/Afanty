package com.booksroo.classroom.web.controller;

import com.booksroo.classroom.common.enums.PlatformEnum;
import com.booksroo.classroom.common.enums.UserEnum;
import com.booksroo.classroom.common.util.ParamCheckUtil;
import com.booksroo.classroom.common.vo.StudentVo;
import com.booksroo.classroom.common.vo.TeacherVo;
import com.booksroo.classroom.service.BizStudentService;
import com.booksroo.classroom.service.BizTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.booksroo.classroom.common.constant.BizConstant.WEB_SESSION_USER_KEY;
import static com.booksroo.classroom.common.constant.PromptConstant.PARAM_INVALID;
import static com.booksroo.classroom.common.constant.PromptConstant.SAME_PHONE_MULTIPLE_STUDENT;

/**
 * @author liujianjian
 * @date 2018/6/3 16:40
 */
@RestController
@RequestMapping("/common")
@CrossOrigin
public class LoginController extends BaseController {

    @Autowired
    private BizTeacherService bizTeacherService;
    @Autowired
    private BizStudentService bizStudentService;

    @RequestMapping("/login")
    public Object login(String userType, String mobileNo, String password, int platform, String deviceNo) throws Exception {

        ParamCheckUtil.checkNotEmptyStr(userType, mobileNo, password);

        if (UserEnum.TEACHER.getType().equalsIgnoreCase(userType)) {
            TeacherVo t = bizTeacherService.doLogin(mobileNo, password, platform, deviceNo);
            if (!PlatformEnum.isMobile(platform)) {
                TeacherVo v = new TeacherVo();
                v.setToken(t.getToken());
                v.setUserType(t.getUserType());
                v.setFirstLoginTime(t.getFirstLoginTime());
                v.setTeacherName(t.getTeacherName());
                v.setHeadImg(t.getHeadImg());
                v.setId(t.getId());
                return success(v);
            }
            return success(t);
        }

        if (UserEnum.STUDENT.getType().equalsIgnoreCase(userType)) {
            return success(bizStudentService.doLogin(mobileNo, password, platform, deviceNo));
        }

        return fail(PARAM_INVALID, "参数不正确");
    }

    @RequestMapping("/logout")
    public Object logout(HttpSession session) throws Exception {

        session.removeAttribute(WEB_SESSION_USER_KEY);
        return success();
    }

}
