package com.booksroo.classroom.web.controller;

import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.response.BaseResponse;
import com.booksroo.classroom.common.util.LayuiUtil;
import com.booksroo.classroom.common.vo.PageList;
import com.booksroo.classroom.common.vo.StudentVo;
import com.booksroo.classroom.common.vo.TeacherVo;
import com.booksroo.classroom.common.vo.TeacherVo;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

import static com.booksroo.classroom.common.constant.BizConstant.WEB_SESSION_USER_KEY;
import static com.booksroo.classroom.common.constant.PromptConstant.USER_NOT_LOGIN;

/**
 * @author liujianjian
 * @date 2018/6/2 23:19
 */
public class BaseController {
    protected final Logger log = Logger.getLogger(this.getClass());

    protected TeacherVo getLoginTeacher(HttpServletRequest request) throws Exception {
        TeacherVo vo = (TeacherVo) request.getAttribute(WEB_SESSION_USER_KEY);
        if (vo == null) throw new BizException(USER_NOT_LOGIN, "用户未登录");
        return vo;
    }

    protected StudentVo getLoginStudent(HttpServletRequest request) throws Exception {
        StudentVo vo = (StudentVo) request.getAttribute(WEB_SESSION_USER_KEY);
        if (vo == null) throw new BizException(USER_NOT_LOGIN, "用户未登录");
        return vo;
    }

    protected List<MultipartFile> getUploadFiles(HttpServletRequest request) {
        List<MultipartFile> list = new ArrayList<>();

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    protected BaseResponse success() {
        return BaseResponse.success();
    }

    protected <T> BaseResponse<T> success(String code, T data) {
        return BaseResponse.success(code, data);
    }

    protected <T> BaseResponse<T> success(T data) {
        return BaseResponse.success(data);
    }

    protected BaseResponse fail(String code, String msg) {
        return BaseResponse.fail(code, msg);
    }

    protected BaseResponse error(String msg) {
        return BaseResponse.error(msg);
    }

    protected Map<String, Object> tableResp(PageList pageList) {
        return LayuiUtil.tableResp(pageList);
    }
}
