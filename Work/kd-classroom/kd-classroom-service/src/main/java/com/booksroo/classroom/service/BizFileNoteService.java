/*
package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.FileNotesMapper;
import com.booksroo.classroom.common.domain.FileNotes;
import com.booksroo.classroom.common.domain.FileResourceChild;
import com.booksroo.classroom.common.domain.FileResources;
import com.booksroo.classroom.common.domain.PackageResourceNote;
import com.booksroo.classroom.common.query.FileNotesQuery;
import com.booksroo.classroom.common.query.PackageResourceNoteQuery;
import com.booksroo.classroom.common.vo.FileResourceVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


*/
/**
 * @author liujianjian
 * @date 2018/6/13 19:16
 *//*

@Service("bizFileNoteService")
public class BizFileNoteService extends BaseService {

    @Autowired
    private FileNotesMapper fileNotesMapper;
    @Autowired
    private BizPackageResourceNoteService bizPackageResourceNoteService;
    @Autowired
    private BizFileResourceService bizFileResourceService;

    public List<FileResourceVo> getNotesByPackage(PackageResourceNoteQuery query) {
        query.setLimit(1000);
        List<PackageResourceNote> prnList = bizPackageResourceNoteService.getList(query);
        if (CollectionUtils.isEmpty(prnList)) return null;

        Map<Long, Set<Long>> rnMap = new HashMap<>();
        for (PackageResourceNote prn : prnList) {
            Set<Long> nids = rnMap.get(prn.getResourceId());
            if (nids == null) nids = new HashSet<>();

//            nids.add(prn.getFileNoteId());
            rnMap.put(prn.getResourceId(), nids);
        }
        return bindResourceVoListByRNMap(rnMap);
    }

    public List<FileResourceVo> bindResourceVoListByRNMap(Map<Long, Set<Long>> rnMap) {
        if (MapUtils.isEmpty(rnMap)) return null;

        Set<Long> rids = rnMap.keySet();
        Map<Long, FileResources> resourceMap = bizFileResourceService.getMapByIds(rids);

        if (MapUtils.isEmpty(resourceMap)) return null;

        Set<Long> nids = new HashSet<>();
        for (Set<Long> set : rnMap.values()) {
            nids.addAll(set);
        }
        Map<Long, FileNotes> fnMap = getMapByIds(nids);
        if (MapUtils.isEmpty(fnMap)) return null;

        List<FileResourceVo> voList = new ArrayList<>();
        for (Map.Entry<Long, Set<Long>> entry : rnMap.entrySet()) {
            FileResources r = resourceMap.get(entry.getKey());
            if (r == null) continue;

            FileResourceVo vo = new FileResourceVo(r.getId(), r.getResourceName(), r.getResourceType().intValue());
            List<FileResourceChild> childList = new ArrayList<>();
            for (long nid : entry.getValue()) {
                FileNotes n = fnMap.get(nid);
                if (n == null) continue;

                FileResourceChild c = new FileResourceChild(r.getId(), n.getNoteContent(), 0);
                childList.add(c);
            }
            vo.setChildResourceList(childList);
            voList.add(vo);
        }
        return voList;
    }

    public Map<Long, FileNotes> getMapByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;

        FileNotesQuery query = new FileNotesQuery();
        query.setIds(ids);
        query.noLimit();
        List<FileNotes> list = getList(query);
        if (CollectionUtils.isEmpty(list)) return null;

        Map<Long, FileNotes> map = new HashMap<>();
        for (FileNotes fn : list) {
            map.put(fn.getId(), fn);
        }
        return map;
    }

    public List<FileNotes> getList(FileNotesQuery query) {
        return fileNotesMapper.select(query);
    }

    public int count(FileNotesQuery query) {
        return fileNotesMapper.count(query);
    }

    public int saveOrUpdate(FileNotes record) {
        FileNotesQuery query = new FileNotesQuery();
        query.setChildResourceId(record.getChildResourceId());
        query.noLimit();
        query.setOrderByStr("id desc");
        List<FileNotes> list = getList(query);

        if (CollectionUtils.isNotEmpty(list)) {
            FileNotes dbRecord = list.get(0);
            record.setId(dbRecord.getId());
            return updateByPK(record);
        }
        return add(record);
    }

    public int add(FileNotes record) {
        return fileNotesMapper.insertSelective(record);
    }

    public int updateByPK(FileNotes record) {
        return fileNotesMapper.updateByPrimaryKeySelective(record);
    }
}
*/
