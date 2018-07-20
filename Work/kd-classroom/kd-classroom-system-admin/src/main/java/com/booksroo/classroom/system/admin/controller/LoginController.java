package com.booksroo.classroom.system.admin.controller;

import com.booksroo.classroom.common.domain.AdminDomain;
import com.booksroo.classroom.common.response.BaseResponse;
import com.booksroo.classroom.common.vo.AdminVo;
import com.booksroo.classroom.service.BizAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.booksroo.classroom.common.constant.BizConstant.SYSTEM_ADMIN_SESSION_USER_KEY;

/**
 * @author liujianjian
 * @date 2018/6/3 14:18
 */
@Controller
@RequestMapping("/common")
public class LoginController extends BaseController {

    @Autowired
    private BizAdminService bizAdminService;

    @RequestMapping("/login")
    public String index() {
        return "LoginDemo";
    }

    @RequestMapping("/toLogin")
    @ResponseBody
    public BaseResponse checkLoginInfo(HttpServletRequest request, String account, String password)
            throws Exception {
        AdminDomain admin = bizAdminService.checkLoginInfo(account, password);
        if (admin != null) {
            request.getSession().setAttribute(SYSTEM_ADMIN_SESSION_USER_KEY, admin);
            return success(new AdminVo(admin.getId(), admin.getAccount()));
        }
        return error("登陆失败");
    }

}
