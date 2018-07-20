package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

import java.util.Date;

/**
 *
 *
 */

@Data
public class ClassVo extends BaseDomain {

    private Byte grade;

    private Byte classNo;

    private Short studentNum;

    private Date termStartTime;

    private Date termEndTime;

    private Boolean delFlag;

    private Long schoolId;

    private String schoolName;

    public String getStatus(){
      if(delFlag){
          return "启用";
      }else{
          return "禁用";
      }
    }

    public String getTermStartTimeStr() {
        return formatDate(termStartTime);
    }

    public String getTermEndTimeStr() {
        return formatDate(termEndTime);
    }
}
