package com.elling.book.sys.goods.service;
import com.elling.book.sys.goods.model.GCatalog;

import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.base.Service;
import com.elling.book.sys.common.entity.TreeNode;

/**
 *
 * Created by cyy on 2019/08/01.
 */
public interface GCatalogService extends Service<GCatalog> {
	/**
	 * 根据uuid批量删除
	 * @param ids
	 * @return
	 */
	int deleteByUuids(List<String> ids);
	
	/**
	 *  根据catalog ID获取对应拥有的树信息
	 * @param map
	 * @return
	 */
	public List<TreeNode> getCatalogTree(GCatalog gCatalog);
	/**
	 * 获取单个节点信息（根据对应的查询条件）
	 * @param gCatalog
	 * @return
	 */
	public GCatalog getCatalogById(GCatalog gCatalog);
}
