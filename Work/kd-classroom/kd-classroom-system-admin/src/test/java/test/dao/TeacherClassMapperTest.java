package test.dao;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.common.dao.TeacherClassMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunitTest;

import java.util.*;

public class TeacherClassMapperTest extends BaseJunitTest {

    @Autowired
    private TeacherClassMapper teacherClassMapper;


    @Test
    public void testQueryClassIdsByTeacher() {
        Set<Long> ClassIds = teacherClassMapper.queryClassIdsByTeacherId(10000090L);
        for (Long l : ClassIds) {
            System.out.println(l);
        }
    }

    @Test
    public void test() {
        Set<Long> ori = new HashSet<>();
        Set<Long> up = new HashSet<>();

        ori.add(1001L);
        ori.add(1002L);
        ori.add(1003L);

        up.add(1001L);
        up.add(1002L);
        up.add(1003L);

        Collection con1 = new HashSet<>(up);
        Collection con2 = new HashSet<>(up);


        con1.removeAll(ori);
        System.out.println(JSON.toJSONString(con1));

        con2.removeAll(con1);
        System.out.println(JSON.toJSONString(con2));
    }


    @Test
    public void test2() {
        Set<Long> ori = new HashSet<>();
        Set<Long> up = new HashSet<>();

        ori.add(1002L);
        ori.add(1004L);


        up.add(1002L);
        up.add(1000L);
        up.add(1008L);
        up.add(1009L);

        Map<String, Collection> re = compareSet(ori, up);

        Map<String, Set<Long>> d = compareSt(ori, up);

        System.out.println(JSON.toJSONString(re));
//        System.out.println(JSON.toJSONString(d));

    }

    private Map<String, Collection> compareSet(Set<Long> oriClassIds, Set<Long> updateClassIds) {
        Map<String, Collection> finalData = new HashMap<>();
        Collection eids = new HashSet<>(updateClassIds);
        Collection uids = new HashSet<>(updateClassIds);

        eids.removeAll(oriClassIds);
        finalData.put("diff", eids);

        uids.removeAll(eids);
        finalData.put("equal", uids);

        return finalData;
    }

    private Map<String, Set<Long>> compareSt(Set<Long> oriClassIds, Set<Long> updateClassIds) {
        Map<String, Set<Long>> finalData = new HashMap<>();
        for (Long ori : oriClassIds) {
            for (Long up : updateClassIds) {
                if (ori == up) {
                    Set<Long> equ = new HashSet<>();
                    equ.add(ori);
                    finalData.put("equ", equ);
                } else {
                    Set<Long> diff = new HashSet<>();
                    diff.add(ori);
                    finalData.put("diff", diff);
                }
            }
        }

        return finalData;
    }

}
