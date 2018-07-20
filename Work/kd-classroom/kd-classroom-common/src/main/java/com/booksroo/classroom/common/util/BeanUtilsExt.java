package com.booksroo.classroom.common.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author liujianjian
 * @date 2018/6/3 11:24
 */
public class BeanUtilsExt {
    static {
        // 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.util.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlTimestampConverter(null),
                java.sql.Timestamp.class);
        // 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
    }

    //有些属性是null的会被设置成默认值，比如数值被设置成了0
    public static void copyProperties(Object target, Object source)
            throws Exception {
        if (target == null || source == null) return;
        // 支持对日期copy
//        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
        propertyUtilsCopy(target, source);
    }

    public static void propertyUtilsCopy(Object target, Object source) throws Exception {
        if (target == null || source == null) return;
        PropertyUtils.copyProperties(target, source);
    }
}
