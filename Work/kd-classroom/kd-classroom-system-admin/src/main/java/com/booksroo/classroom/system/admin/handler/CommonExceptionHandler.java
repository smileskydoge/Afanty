package com.booksroo.classroom.system.admin.handler;

import com.alibaba.fastjson.JSON;
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
            boolean noLogin = USER_NOT_LOGIN.equalsIgnoreCase(bex.getCode());
            print(response, BaseResponse.fail(bex.getCode(), noLogin ? "登录失效，请重新登录" : bex.getMessage()));
            return null;
        }

        print(response, BaseResponse.error(ex.getMessage()));
        log.error(getLogPre(request) + ", " + ex.getMessage(), ex);
        return null;
    }

    private String getLogPre(HttpServletRequest request) {
        return "";
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
