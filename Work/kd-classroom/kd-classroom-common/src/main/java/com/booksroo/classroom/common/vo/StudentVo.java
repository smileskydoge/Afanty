package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.enums.UserEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author liujianjian
 * @date 2018/6/2 23:42
 */
@Data
public class StudentVo extends UserVo {

    private String schoolName;

    private Byte grade;

    private Byte classNo;

    private Long classId;

    private Long schoolId;

    private String studentNo;

    private String studentName;

    private String headImg;

    private String parentPhone;

    private Date beginTime;

    private Byte age;

    private Boolean delFlag;

    private String userType = UserEnum.STUDENT.getType();

    public String getClassName() {
        return toViewStr(grade) + "年级" + toViewStr(classNo) + "班";
    }

    public String getBeginTimeStr() {
        return formatDate(beginTime);
    }
}
