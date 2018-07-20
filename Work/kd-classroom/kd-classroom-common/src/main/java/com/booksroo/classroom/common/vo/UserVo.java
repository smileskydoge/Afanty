package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liujianjian
 * @date 2018/6/11 11:01
 */
@Data
public class UserVo extends BaseDomain {

    private Integer platform;

    private Timestamp firstLoginTime;

    private String token;

    private String userType;

    public UserVo() {
    }

    public UserVo(long userId, String userType) {
        super();
        super.setId(userId);
        this.userType = userType;
    }

    public boolean getFirstLoginFLag() {
        return firstLoginTime == null;
    }
}
