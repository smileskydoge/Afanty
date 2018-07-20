package com.booksroo.classroom.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author liujianjian
 * @date 2018/6/4 21:48
 */
@Data
public class UserActionDataVo implements Serializable {

    private Long userId;

    private Integer userType;//1-教师，2-学生

    private Timestamp lastLoginTime;//最近一次登录时间

    private Timestamp lastUpdatePwdTime;//最近一次修改密码时间
}
