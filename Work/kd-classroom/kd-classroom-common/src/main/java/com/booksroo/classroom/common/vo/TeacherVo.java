package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.enums.UserEnum;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author liujianjian
 * @date 2018/6/3 17:03
 */
@Data
public class TeacherVo extends UserVo {

    private String schoolName;

    private Long schoolId;

    private Byte grade;

    private Byte classNo;

    private Long classId;

    private List classIds;

    private List<ClassDomain> classDomains;

    private String subjectId;

    private String subjectName;

    private String teacherName;

    private String mobileNo;

    private Byte age;

    private String headImg;

    private Byte jobTitle;

    private Boolean delFlag;

    private String userType = UserEnum.TEACHER.getType();

    public String getClassName() {
        return toViewStr(grade) + "年级" + toViewStr(classNo) + "班";
    }

    public String getClassNames() {
        if (classDomains == null || classDomains.size() <= 0) return "";
        StringBuilder classNames = new StringBuilder();
        for (ClassDomain c : classDomains) {
            classNames.append(toViewStr(c.getGrade())).append("年级").append(toViewStr(c.getClassNo())).append("班");
            classNames.append("|");
        }
        return classNames.toString();
    }

    public String getClsIds() {
        if (classDomains == null || classDomains.size() <= 0) return "";

        StringBuilder clsIds = new StringBuilder();
        for (int i = 0; i < classDomains.size(); i++) {
            ClassDomain c = classDomains.get(i);
            if (i == classDomains.size() - 1) {
                clsIds.append(c.getId());
            } else {
                clsIds.append(c.getId()).append(",");
            }
        }
        return clsIds.toString();
    }

    public String getJobTitleName() {
        if (jobTitle == null) return "否";
        switch (jobTitle) {
            case 1:
                return "是";
            default:
                return "否";
        }
    }

}
