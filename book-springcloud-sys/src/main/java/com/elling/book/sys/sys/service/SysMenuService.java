package com.elling.book.sys.sys.service;
import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.base.Service;
import com.elling.book.sys.common.entity.TreeNode;
import com.elling.book.sys.sys.model.SysMenu;
import com.github.pagehelper.PageInfo;

/**
 *
 * Created by cyy on 2019/08/01.
 */
public interface SysMenuService extends Service<SysMenu> {
	/**
	 * .获取菜单层级
	 * @return
	 */
	public List<SysMenu> getMenuLevel(Map map);
	
	/**
	 * 获取左边菜单数据，根据传入角色id进行筛选
	 * @param map
	 * @return
	 */
	public List getMenuData(Map map);
	
	/**
	 * 获取所有的菜单节点数据
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
	 *	根据自定义条件查询
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getByCondition(SysMenu sysMenu);
	
}
