package com.booksroo.classroom.common.constant;

/**
 * @author liujianjian
 * @date 2018/6/24 13:07
 */
public class CacheKeyConstant {
    private CacheKeyConstant() {
    }

    //老师开始上课指令
    public static final String TEACHER_START_CLASS_DATA_CACHE_KEY = "teacher_start_class_data_cache_key";

    //老师当前上课时的数据缓存key
    public static final String TEACHER_ON_CLASS_DATA_CACHE_KEY_PRE = "teacher_on_class_data_cache_key_";

    public static final String TEACHER_TOKEN_CACHE_KEY_PRE = "teacher_token_cache_key_";

    public static final String STUDENT_TOKEN_CACHE_KEY_PRE = "student_token_cache_key_";

}
