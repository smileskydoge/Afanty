package com.booksroo.classroom.common.vo;

import com.booksroo.classroom.common.domain.BaseDomain;
import lombok.Data;

/**
 * @author liujianjian
 * @date 2018/6/25 15:29
 */
@Data
public class AdminVo extends BaseDomain {
    private Long id;

    private String account;

    public AdminVo(Long id, String account) {
        this.id = id;
        this.account = account;
    }
}
