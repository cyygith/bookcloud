package com.elling.book.sys.sys.service;
import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.base.Service;
import com.elling.book.sys.common.entity.TreeNode;
import com.elling.book.sys.sys.model.SysRole;

/**
 *
 * Created by cyy on 2019/08/01.
 */
public interface SysRoleService extends Service<SysRole> {
	
	List<SysRole> getSysRolebyCondition(Map<String,Object> map);
	
	/**
	 * .根据角色ID获取用户对应的菜单信息
	 * @param map
	 * @return
	 */
	List<TreeNode> getMenuDataByRoleId(Map map);
	/**
	 * 保存用户的菜单列表
	 * @param sysRole
	 * @return
	 */
	int saveModelAndMenu(SysRole sysRole);
	/**
	 * 更新用户的菜单列表
	 * @param sysRole
	 * @return
	 */
	int updateModelAndMenu(SysRole sysRole);
	
	/**
	 * 删除用户的菜单列表
	 * @param sysRole
	 * @return
	 */
	int deleteModelAndMenu(Map map);
	
	
}
