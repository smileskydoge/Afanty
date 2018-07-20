package com.booksroo.classroom.common.request;

import com.booksroo.classroom.common.util.BizUtil;
import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/13 11:58
 */
@Data
public class AppBaseRequest extends BaseRequest {
    private String token;
    private String sign;

    public long getUserId() {
        return BizUtil.getUserIdByToken(token);
    }
}
