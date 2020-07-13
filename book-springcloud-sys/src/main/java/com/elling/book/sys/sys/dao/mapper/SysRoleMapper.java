package com.elling.book.sys.sys.dao.mapper;

import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.base.MyMapper;
import com.elling.book.sys.sys.model.SysRole;

public interface SysRoleMapper extends MyMapper<SysRole> {
	
	List<SysRole> getSysRolebyUserId(Map<String,Object> map);
	
	/**
	 * 保存用户的菜单列表
	 * @param sysRole
	 * @return
	 */
	int saveRoleMenus(List list);
	
	/**
	 * 根据role删除sys_role_menu中的数据
	 * @param roleId
	 * @return
	 */
	int deletMenuRoleByRoleId(Long roleId);
	
	/**
	 * 根据roleIds删除sys_role_menu中的数据
	 * @param roleId
	 * @return
	 */
	int deletMenuRoleByRoleIds(List<String> list);
}