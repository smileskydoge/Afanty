package com.booksroo.classroom.service;

import com.booksroo.classroom.common.dao.CatalogResourcesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liujianjian
 * @date 2018/6/4 17:33
 */
@Service("bizCatalogResourceService")
public class BizCatalogResourceService extends BaseService{

    @Autowired
    private CatalogResourcesMapper catalogResourcesMapper;

}
