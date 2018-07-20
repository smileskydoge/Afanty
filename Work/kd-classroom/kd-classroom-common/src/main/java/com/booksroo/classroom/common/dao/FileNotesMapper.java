package com.booksroo.classroom.common.dao;

import com.booksroo.classroom.common.domain.FileNotes;
import com.booksroo.classroom.common.query.FileNotesQuery;

import java.util.List;

public interface FileNotesMapper {

    List<FileNotes> select(FileNotesQuery query);

    int count(FileNotesQuery query);

    int updateByCRId(FileNotes record);

    /**
     * file_notes数据表的操作方法: deleteByPrimaryKey  
     * 创建时间 : 2018-06-13 19:14:58
     */
    int deleteByPrimaryKey(Long id);

    /**
     * file_notes数据表的操作方法: insert  
     * 创建时间 : 2018-06-13 19:14:58
     */
    int insert(FileNotes record);

    /**
     * file_notes数据表的操作方法: insertSelective  
     * 创建时间 : 2018-06-13 19:14:58
     */
    int insertSelective(FileNotes record);

    /**
     * file_notes数据表的操作方法: selectByPrimaryKey  
     * 创建时间 : 2018-06-13 19:14:58
     */
    FileNotes selectByPrimaryKey(Long id);

    /**
     * file_notes数据表的操作方法: updateByPrimaryKeySelective  
     * 创建时间 : 2018-06-13 19:14:58
     */
    int updateByPrimaryKeySelective(FileNotes record);

    /**
     * file_notes数据表的操作方法: updateByPrimaryKey  
     * 创建时间 : 2018-06-13 19:14:58
     */
    int updateByPrimaryKey(FileNotes record);
}