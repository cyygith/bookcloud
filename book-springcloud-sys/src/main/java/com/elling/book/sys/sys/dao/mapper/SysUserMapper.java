package com.elling.book.sys.sys.dao.mapper;

import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.base.MyMapper;
import com.elling.book.sys.common.entity.TreeNode;
import com.elling.book.sys.sys.model.SysUser;

public interface SysUserMapper extends MyMapper<SysUser> {
	
	/**
	 * 根据用户id获取角色ID列表
	 * @param map
	 * @return
	 */
	List<TreeNode> getRoleByUserId(Map map);
	/**
	 * 保存用户的用户角色列表
	 * @param sysRole
	 * @return
	 */
	int saveUserRole(List list);
	/**
	 * 保存用户的部门列表
	 * @param sysRole
	 * @return
	 */
	int saveUserDept(List list);
	
	/**
	 * 根据userId删除sys_user_dept中的数据
	 * @param roleId
	 * @return
	 */
	int deleteUserDeptByUserId(Long userId);
	/**
	 * 根据userId删除sys_user_role中的数据
	 * @param roleId
	 * @return
	 */
	int deleteUserRoleByUserId(Long userId);
	
	/**
	 * 根据userIds删除sys_user_dept中的数据
	 * @param roleId
	 * @return
	 */
	int deleteUserDeptByUserIds(List<String> list);
	/**
	 * 根据userIds删除sys_user_role中的数据
	 * @param roleId
	 * @return
	 */
	int deleteUserRoleByUserIds(List<String> list);
	
	
}