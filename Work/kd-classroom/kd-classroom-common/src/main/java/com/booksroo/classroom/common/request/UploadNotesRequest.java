package com.booksroo.classroom.common.request;

import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/13 19:27
 */
@Data
public class UploadNotesRequest extends BaseRequest {

    private byte[] fileBytes;

    private Long childResourceId;

    private String fileName;

    private String fileUrl;

    private Long packageClassId;

    private Long resourceId;

    private Long createTime;

}
