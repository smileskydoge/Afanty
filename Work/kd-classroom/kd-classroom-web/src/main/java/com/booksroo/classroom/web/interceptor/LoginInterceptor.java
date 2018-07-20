package com.booksroo.classroom.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.constant.BizConstant;
import com.booksroo.classroom.common.enums.UserEnum;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.pojo.PropertyValue;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.CookieUtil;
import com.booksroo.classroom.common.vo.TeacherVo;
import com.booksroo.classroom.common.vo.TeacherVo;
import com.booksroo.classroom.service.BizStudentService;
import com.booksroo.classroom.service.BizTeacherService;
import com.booksroo.classroom.service.interf.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.booksroo.classroom.common.constant.BizConstant.PC_COOKIE_LOGIN_TOKEN_KEY;
import static com.booksroo.classroom.common.constant.BizConstant.PC_COOKIE_LOGIN_USER_KEY;
import static com.booksroo.classroom.common.constant.BizConstant.WEB_SESSION_USER_KEY;
import static com.booksroo.classroom.common.constant.PromptConstant.*;

/**
 * @author liujianjian
 * @date 2018/6/3 16:38
 */
public class LoginInterceptor implements HandlerInterceptor {
    private final Logger log = Logger.getLogger(this.getClass());

    public static Set<String> ALLOW_DOMAIN = null;

    static {
        ALLOW_DOMAIN = new HashSet<>(4);
        ALLOW_DOMAIN.add("http://localhost");
        ALLOW_DOMAIN.add("http://localhost:8080");
        ALLOW_DOMAIN.add("http://kdbooktest.koudairoo.com");
        ALLOW_DOMAIN.add("http://kdbook.koudairoo.com");
    }

    private BizTeacherService bizTeacherService;
    private BizStudentService bizStudentService;
    private CacheService redisCacheService;
    private PropertyValue propertyValue;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String originHeader = request.getHeader("Origin");

        if (ALLOW_DOMAIN.contains(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Allow", "*");
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            response.setHeader(
                    "Access-Control-Allow-Headers",
                    "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent, Authorization, X-Auth-Token");
            response.setHeader("Access-Control-Max-Age", "3600");
            // 接收跨域的cookie
            response.setHeader("Access-Control-Allow-Credentials", "true");
            if ("IE".equals(request.getParameter("type"))) {
                response.setHeader(
                        "XDomainRequestAllowed", "1");
            }
        }

        String uri = request.getRequestURI();
        if (BizUtil.isPCRequest(uri)) {
            return checkPCTeacherLogin(request, response);
        }

        String token = request.getParameter("token");
        checkToken(token);
        checkSign(request);

        long userId = BizUtil.getUserIdByToken(token);
        Object vo = null;
        if (BizUtil.isTeacherRequest(uri)) {
            vo = bizTeacherService.getVoById(userId);
        } else if (BizUtil.isStudentRequest(uri)) {
            vo = bizStudentService.getVoById(userId);
        }

        if (vo == null) {
            String userType = request.getParameter("userType");
            if (StringUtils.isNotBlank(userType)) {
                if (UserEnum.isStudent(userType)) {
                    vo = bizStudentService.getVoById(userId);
                } else if (UserEnum.isTeacher(userType)) {
                    vo = bizTeacherService.getVoById(userId);
                }
            }
            if (vo == null)
                throw new BizException(USER_NOT_LOGIN, "用户信息不存在");
        }
        request.setAttribute(WEB_SESSION_USER_KEY, vo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private boolean checkPCTeacherLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();

        long userId = 0;
        String token = CookieUtil.getCookieVal(PC_COOKIE_LOGIN_TOKEN_KEY, cookies);
        if (StringUtils.isNotBlank(token)) {
            if (token.contains("%")) token = URLDecoder.decode(token, "UTF-8");
            userId = BizUtil.getUserIdByToken(token);
        } else {
            String loginUserObjStr = CookieUtil.getCookieVal(PC_COOKIE_LOGIN_USER_KEY, cookies);
            if (StringUtils.isBlank(loginUserObjStr)) {
                throw new BizException(USER_NOT_LOGIN, "用户信息不存在");
            }
            loginUserObjStr = URLDecoder.decode(loginUserObjStr, "UTF-8");
            TeacherVo teacherVo = JSON.parseObject(loginUserObjStr, TeacherVo.class);
            userId = teacherVo.getId();
            token = teacherVo.getToken();
        }
        TeacherVo dbVo = bizTeacherService.getVoById(userId);
        if (dbVo == null) throw new BizException(USER_NOT_LOGIN, "用户信息不存在");
        checkToken(token);
        request.setAttribute(WEB_SESSION_USER_KEY, dbVo);
        return true;
    }

    private void checkToken(String token) throws Exception {
        if (isDev()) return;

        if (StringUtils.isBlank(token)) throw new BizException(USER_TOKEN_INVALID, "token无效");

        String[] arr = token.split("-");
        String userType = arr[0];

        String trueToken = null;
        if (UserEnum.isTeacher(userType)) {
            trueToken = redisCacheService.getTeacherToken(Long.parseLong(arr[1]));
        } else if (UserEnum.isStudent(userType)) {
            trueToken = redisCacheService.getStudentToken(Long.parseLong(arr[1]));
        }

        if (StringUtils.isBlank(trueToken)) throw new BizException(USER_TOKEN_INVALID, "token已过期，请重新登录");

        if (trueToken.startsWith("\"")) {
            token = "\"" + token + "\"";
        }

        String tokenAction = redisCacheService.getTokenAction(trueToken);
        boolean upd = StringUtils.equals(tokenAction, BizConstant.UPDATE_TOKEN_WAY_UPDATE_PWD);
//        log.info("cacheToken:" + trueToken + ", tokenAction:" + tokenAction + ", upd:" + upd);

        if (!trueToken.equals(token))
            throw new BizException(USER_TOKEN_INVALID, upd ? "密码失效请重新登陆" : "token失效，请重新登录");
    }

    private void checkSign(HttpServletRequest request) throws Exception {
//        if (StringUtils.isBlank(request.getHeader("x-requested-with"))) return;

//        if (null == request.getParameter("sign")) throw new BizException(SIGN_INVALID, "sign无效");
        //验签操作, md5加密一个私key和时间（精确到分）或者用户id等 ...
    }

    private boolean isDev() {
        return propertyValue != null && "dev".equals(propertyValue.getEnv());
    }

    public BizTeacherService getBizTeacherService() {
        return bizTeacherService;
    }

    public void setBizTeacherService(BizTeacherService bizTeacherService) {
        this.bizTeacherService = bizTeacherService;
    }

    public BizStudentService getBizStudentService() {
        return bizStudentService;
    }

    public void setBizStudentService(BizStudentService bizStudentService) {
        this.bizStudentService = bizStudentService;
    }

    public void setRedisCacheService(CacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
    }

    public PropertyValue getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(PropertyValue propertyValue) {
        this.propertyValue = propertyValue;
    }
}
