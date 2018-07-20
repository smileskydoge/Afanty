package com.booksroo.classroom.system.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.common.domain.AdminDomain;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.CookieUtil;
import com.booksroo.classroom.common.vo.AdminVo;
import com.booksroo.classroom.service.BizAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Set;

import static com.booksroo.classroom.common.constant.BizConstant.PC_COOKIE_LOGIN_USER_KEY;
import static com.booksroo.classroom.common.constant.BizConstant.SYSTEM_ADMIN_SESSION_USER_KEY;
import static com.booksroo.classroom.common.constant.PromptConstant.USER_NOT_LOGIN;

/**
 * @author liujianjian
 * @date 2018/6/3 14:14
 */
public class LoginInterceptor implements HandlerInterceptor {

    private BizAdminService bizAdminService;
    private Set<String> excludeUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        System.out.println("uri:"+request.getRequestURI());
        if (isExcludeUrl(request.getRequestURI())) return true;

        String loginUserObjStr = CookieUtil.getCookieVal(SYSTEM_ADMIN_SESSION_USER_KEY, request.getCookies());
        if (StringUtils.isBlank(loginUserObjStr)) {
            response.sendRedirect(BizUtil.getAdminLoginUrl());
            return false;
        }
        loginUserObjStr = URLDecoder.decode(loginUserObjStr, "UTF-8");

        AdminVo adminVo = JSON.parseObject(loginUserObjStr, AdminVo.class);
        if (adminVo == null) {
            response.sendRedirect(BizUtil.getAdminLoginUrl());
            return false;
        }

        AdminDomain admin = bizAdminService.getById(adminVo.getId());
        if (admin == null) {
            response.sendRedirect(BizUtil.getAdminLoginUrl());
            return false;
        }
        request.getSession().setAttribute(SYSTEM_ADMIN_SESSION_USER_KEY, admin);

        // 判断是否登录
        if (request.getSession().getAttribute(SYSTEM_ADMIN_SESSION_USER_KEY) == null) {
            response.sendRedirect(BizUtil.getAdminLoginUrl());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private boolean isExcludeUrl(String uri) {
        return excludeUrls.contains(uri);
    }

    public Set<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(Set<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public void setBizAdminService(BizAdminService bizAdminService) {
        this.bizAdminService = bizAdminService;
    }
}
