package com.elling.book.sys.goods.service.impl;

import com.elling.book.sys.goods.dao.mapper.GCatalogMapper;
import com.elling.book.sys.goods.model.GCatalog;
import com.elling.book.sys.goods.service.GCatalogService;
import com.elling.book.sys.common.base.AbstractService;
import com.elling.book.sys.common.entity.TreeNode;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by cyy on 2019/08/01.
 */
@Service
public class GCatalogServiceImpl extends AbstractService<GCatalog> implements GCatalogService {

    @Autowired
    private GCatalogMapper gCatalogMapper;
    
    @Override
	public int deleteByUuids(List<String> ids) {
    	int count = gCatalogMapper.deleteByUuids(ids);
		return count;
	}

	@Override
	public List<TreeNode> getCatalogTree(GCatalog gCatalog) {
		return gCatalogMapper.getCatalogTree(gCatalog);
	}

	@Override
	public GCatalog getCatalogById(GCatalog gCatalog) {
		return gCatalogMapper.getCatalogById(gCatalog);
	}
    
    

}
