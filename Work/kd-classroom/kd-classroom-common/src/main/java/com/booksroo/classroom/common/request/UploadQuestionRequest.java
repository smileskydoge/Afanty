package com.booksroo.classroom.common.request;

import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/13 19:27
 */
@Data
public class UploadQuestionRequest extends BaseRequest {

    private Long id;//习题ID  （新增时传空，编辑保存时传值）
    private Byte type;//习题类型（1.选择题 2.判断题 3.填空题 4.主观题）
    private String content;//习题数据
    private String analyze;//习题分析数据
    private String answer;//习题答案
    private Byte answerNum;//答案个数
    private String contentImage;//习题图片,base64 str
    private String analyzeImage;//解析图片

}
