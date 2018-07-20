package com.booksroo.classroom.common.request;

import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/13 21:51
 */
@Data
public class HistoryPackageListGetRequest extends BaseRequest {
    private Long subjectId;

    private Long classId;

    private Long teacherClassId;

    public String getSubjectIdStr() {
        return subjectId != null ? "" + subjectId : null;
    }
}
