package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

/**
 * 学科
 */

@Data
public class SubjectVo extends BaseDomain {

    private Long id;
    private String subjectName;
    private String subjectNamePinyin;

    private Long subjectId;//同id

    private Long teacherId;
    private Long teacherClassId;
    private boolean onClass;//是否有教师正在上课
    private int orderNo;
    private String coverImg;

    public SubjectVo() {
    }

    public SubjectVo(String subjectNamePinyin, int orderNo) {
        this.subjectNamePinyin = subjectNamePinyin;
        this.orderNo = orderNo;
    }

    public SubjectVo(Long subjectId, String subjectName) {
        this.id = subjectId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public SubjectVo(Long subjectId, String subjectName, Long teacherId, Long teacherClassId) {
        this.id = subjectId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.teacherId = teacherId;
        this.teacherClassId = teacherClassId;
    }
}
