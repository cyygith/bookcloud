package com.elling.book.sys.sys.dao.mapper;

import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.base.MyMapper;
import com.elling.book.sys.common.entity.TreeNode;
import com.elling.book.sys.goods.model.GCatalog;
import com.elling.book.sys.sys.model.SysMenu;

public interface SysMenuMapper extends MyMapper<SysMenu> {
	
	public List<SysMenu> getMenuLevel(Map map);
	public Integer getMenuLevelCount(Map map);
	
	/**
	 * 根据角色ID获取对应拥有的菜单信息
	 * @param map
	 * @return
	 */
	public List<TreeNode> getMenuData(Map map);
	
	/**
	 * 获取所有菜单信息
	 * @param map
	 * @return
	 */
	
	public List getAllMenuData(Map map);
	
	/**
	 *  根据Menu ID获取对应拥有的树信息
	 * @param map
	 * @return
	 */
	public List<TreeNode> getMenuTree(SysMenu sysMenu);
	/**
	 * 	根据自定义条件查询
	 * @param sysMenu
	 * @return
	 */
	public List<Map<String,Object>> getByCondition(SysMenu sysMenu);
}