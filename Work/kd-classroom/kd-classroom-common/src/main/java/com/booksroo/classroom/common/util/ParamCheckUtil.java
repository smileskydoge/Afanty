package com.booksroo.classroom.common.util;

import com.booksroo.classroom.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;

import static com.booksroo.classroom.common.constant.PromptConstant.PARAMS_MUST_BE_NOT_NULL;
import static com.booksroo.classroom.common.constant.PromptConstant.PARAM_INVALID;

/**
 * @author liujianjian
 * @date 2018/6/3 17:12
 */
public class ParamCheckUtil {

    public static void checkNotNull(Object... objs) throws BizException {
        for (Object o : objs) {
            if (o == null) throw new BizException(PARAMS_MUST_BE_NOT_NULL, "参数不能为空");
        }
    }

    public static void checkNotEmptyStr(String... str) throws Exception {
        for (String s : str) {
            if (StringUtils.isBlank(s)) throw new BizException(PARAMS_MUST_BE_NOT_NULL, "参数不能为空");
        }
    }

    public static void noNegativeLong(Long... ls) throws Exception {
        for (Long l : ls) {
            if (l == null || l <= 0) throw new BizException(PARAM_INVALID, "参数值不正确");
        }
    }

    public static void checkJsonArrStr(String... str) throws Exception {
        checkNotEmptyStr(str);
        for (String s : str) {
            if (!(s.startsWith("[") || s.startsWith("%5b"))) throw new BizException(PARAM_INVALID, "非json字符串数组");
        }
    }
}
