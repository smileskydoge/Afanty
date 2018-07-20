package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {

    public static void main(String[] args) {

        Set<Long> oriClassIds = new HashSet<>();
        oriClassIds.add(1001L);
        oriClassIds.add(1002L);
        oriClassIds.add(1003L);
        oriClassIds.add(1004L);

        Set<Long> updateClassIds = new HashSet<>();
        updateClassIds.add(1003L);
        updateClassIds.add(1009L);

        Map<String,Set<Long>> re = compareSet(updateClassIds,oriClassIds);

        System.out.println(re);
    }


    //比较两个Set
    public static Map<String, Set<Long>> compareSet(Set<Long> oriClassIds, Set<Long> updateClassIds) {
        Map<String, Set<Long>> finalData = new HashMap<>();
        Set<Long> eids = new HashSet<>(updateClassIds);
        Set<Long> uids = new HashSet<>(updateClassIds);

        eids.removeAll(oriClassIds);

        finalData.put("diff", eids);

        uids.removeAll(eids);
        finalData.put("equal", uids);

        return finalData;
    }


}
