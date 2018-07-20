package com.booksroo.classroom.web.handler;

import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.response.BaseResponse;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.HttpUtil;
import com.booksroo.classroom.common.vo.StudentVo;
import com.booksroo.classroom.common.vo.TeacherVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.booksroo.classroom.common.constant.BizConstant.WEB_SESSION_USER_KEY;
import static com.booksroo.classroom.common.constant.PromptConstant.USER_NOT_LOGIN;
import static com.booksroo.classroom.common.constant.PromptConstant.USER_TOKEN_INVALID;

/**
 * @author liujianjian
 * @date 2018/6/3 9:47
 */
@Component
public class CommonExceptionHandler implements HandlerExceptionResolver {

    private static final Logger log = Logger.getLogger(CommonExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/plain;charset=UTF-8");

        if (ex instanceof BizException) {

            BizException bex = (BizException) ex;

            boolean notLogin = USER_NOT_LOGIN.equalsIgnoreCase(bex.getCode());
            boolean tokenInvalid = USER_TOKEN_INVALID.equalsIgnoreCase(bex.getCode());
            boolean noLogin = notLogin || tokenInvalid;

            print(response, BaseResponse.fail(bex.getCode(), noLogin ? "登录失效，请重新登录" : bex.getMessage()));
            return null;

//            if (HttpUtil.isAjaxRequest(request) || BizUtil.isAPPRequest(request.getRequestURI()) || BizUtil.isCommonRequest(request.getRequestURI())) {
//                print(response, BaseResponse.fail(bex.getCode(), notLogin ? "登录失效，请重新登录" : bex.getMessage()));
//                return null;
//            }
//
//            if (noLogin) {
//                response.setHeader("content-type", "text/html;charset=UTF-8");
//                ModelAndView mav = new ModelAndView();
//                mav.setViewName(BizUtil.getLoginUrl(request, false));
//                return mav;
//            }
//
//            print(response, BaseResponse.fail(bex.getCode(), bex.getMessage()));
//            return null;
        }

        print(response, BaseResponse.error(ex.getMessage()));
        log.error(getLogPre(request) + ", " + ex.getMessage(), ex);
        return null;
    }

    private String getLogPre(HttpServletRequest request) {
        Object obj = request.getAttribute(WEB_SESSION_USER_KEY);
        if (obj == null) return "";
        String logPre = "";
        try {
            logPre = BizUtil.getUserLogPre(obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return logPre;
    }

    private void print(HttpServletResponse response, BaseResponse br) {
        try {
            PrintWriter pw = response.getWriter();
            BizUtil.print(pw, br);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
