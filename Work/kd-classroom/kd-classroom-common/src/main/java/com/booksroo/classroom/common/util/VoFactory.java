package com.booksroo.classroom.common.util;

import com.booksroo.classroom.common.domain.ExerciseDomain;
import com.booksroo.classroom.common.domain.StatisticsInfo;
import com.booksroo.classroom.common.domain.UnitTest;
import com.booksroo.classroom.common.vo.ExerciseVo;
import com.booksroo.classroom.common.vo.StatisticsVo;
import com.booksroo.classroom.common.vo.UnitTestVo;
import org.apache.log4j.Logger;

/**
 * @author liujianjian
 * @date 2018/7/12 14:28
 */
public class VoFactory {
    private static final Logger log = Logger.getLogger(VoFactory.class);

    public static ExerciseVo newExerciseVo(ExerciseDomain o) {
        ExerciseVo vo = new ExerciseVo();
        copyVo(vo, o);
        vo.setExerciseId(o.getId());
        return vo;
    }

    public static StatisticsVo newStatisticsVo(StatisticsInfo o) {
        StatisticsVo vo = new StatisticsVo();
        copyVo(vo, o);
        return vo;
    }

    public static UnitTestVo newUnitTestVo(UnitTest o) {
        UnitTestVo vo = new UnitTestVo();
        copyVo(vo, o);
        vo.setUnitTestId(o.getId());
        return vo;
    }

    private static void copyVo(Object vo, Object o) {
        try {
            BeanUtilsExt.copyProperties(vo, o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private VoFactory() {
    }
}
