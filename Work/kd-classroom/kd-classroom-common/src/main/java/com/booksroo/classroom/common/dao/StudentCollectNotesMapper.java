package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.StudentCollectNotes;
import com.booksroo.classroom.common.query.StudentCollectQuery;

import java.util.List;

public interface StudentCollectNotesMapper {

    List<StudentCollectNotes> select(StudentCollectQuery query);
    int count(StudentCollectQuery query);

    int delCollected(StudentCollectNotes record);

    /**
     * student_collect_notes数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-14 10:11:16
     */
    int deleteByPrimaryKey(Long id);

    /**
     * student_collect_notes数据表的操作方法: insert  
     * 创建时间 : 2018-06-14 10:11:16
     */
    int insert(StudentCollectNotes record);

    /**
     * student_collect_notes数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-14 10:11:16
     */
    int insertSelective(StudentCollectNotes record);

    /**
     * student_collect_notes数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-14 10:11:16
     */
    StudentCollectNotes selectByPrimaryKey(Long id);

    /**
     * student_collect_notes数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-14 10:11:16
     */
    int updateByPrimaryKeySelective(StudentCollectNotes record);

    /**
     * student_collect_notes数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-14 10:11:16
     */
    int updateByPrimaryKey(StudentCollectNotes record);
}