package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

@Data
public class SchoolVo extends BaseDomain {

    private Long parentId;
    private String schoolName;
    private String area;
    private Byte educationLength;
//    private String educationLengthInfo;

    public String getEducationLengthInfo(){
        switch (educationLength){
            case 1:return "五年制";
            case 2:return "六年制";
            case 3:return "八年制";
            case 4:return "九年制";
            case 5:return "初中";
            case 6:return "高中";
            case 7:return "十一年制";
            case 8:return "十二年制";
            default:return "-";
        }
    }

}
