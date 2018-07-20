package com.booksroo.classroom.common.constant;


/**
 * 业务逻辑处理相关常量，也可以使用 enum
 *
 * @author liujianjian
 * @date 2018/6/3 14:48
 */
public class BizConstant {

    private BizConstant() {
    }

//    public static final Map<Integer, Integer> SINGLE_CHOICE_QUESTION_ANSWER_MIN_NUM_MAP = new HashMap<>();//单选题答案跟最少选项个数的对应关系
//
//    static {
//        int min = 65;//ascii - A
//        int max = 90;//-Z
//        int num = 1;
//        while (min <= max) {
//            SINGLE_CHOICE_QUESTION_ANSWER_MIN_NUM_MAP.put(min, num);
//            min++;
//            num++;
//        }
//    }

    public static final long TOKEN_EXPIRED_TIME_SECONDS = 3600L * 24 * 7;

    public static final String UPDATE_TOKEN_WAY_UPDATE_PWD = "update_token_way_updatePWD";
    public static final String UPDATE_TOKEN_WAY_UPDATE_MOBILE = "update_token_way_updateMobileNo";
    public static final String UPDATE_TOKEN_WAY_DO_LOGIN = "update_token_way_doLogin";

    public static final String PRIVATE_SECRET_KEY = "booksroo_kdkt@2018";

    public static final String SYSTEM_ADMIN_SESSION_USER_KEY = "admin_session_user_key";

    public static final String WEB_SESSION_USER_KEY = "web_session_user_key";

    public static final String SIGN_PRIVATE_KEY = "booksroo.classroom-1528179259557_SIGN_KEY";

    public static final String TOKEN_PRIVATE_KEY = "booksroo.classroom-1528179251987_TOKEN_KEY";

    public static final String PC_COOKIE_LOGIN_USER_KEY = "pcLoginUserObj";
    public static final String PC_COOKIE_LOGIN_TOKEN_KEY = "pcLoginUserToken";

    public static final String PC_REQUEST_URI_PREFIX = "/pc/";
    public static final String APP_REQUEST_URI_PREFIX = "/app/";
    public static final String COMMON_REQUEST_URI_PREFIX = "/common/";

    public static final String PC_TEACHER_REQUEST_URI_PREFIX = "/pc/teacher";
    public static final String APP_TEACHER_REQUEST_URI_PREFIX = "/app/teacher";
    public static final String APP_STUDENT_REQUEST_URI_PREFIX = "/app/student";

    public static final String TEMP_FILE_STORE_PATH = "/data/temp/files/";//ppt生成图片临时存放路径

    public static final String ALIYUN_OSS_ADDRESS_HOST = "http://kd-classroom.oss-cn-hangzhou.aliyuncs.com";

}
