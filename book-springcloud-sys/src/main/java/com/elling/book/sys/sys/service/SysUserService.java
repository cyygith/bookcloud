package com.elling.book.sys.sys.service;
import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.base.Service;
import com.elling.book.sys.common.entity.TreeNode;
import com.elling.book.sys.sys.model.SysUser;

/**
 *
 * Created by cyy on 2019/08/14.
 */
public interface SysUserService extends Service<SysUser> {
	
	List<TreeNode> getRoleByUserId(Map map);
	
	/**
	 * 保存用户的部门列表
	 * @param sysRole
	 * @return
	 */
	int saveModelAndDept(SysUser sysUser);
	/**
	 * 更新用户的部门列表
	 * @param sysRole
	 * @return
	 */
	int updateModelAndDept(SysUser sysUser);
	
	/**
	 * 删除用户的部门列表
	 * @param sysRole
	 * @return
	 */
	int deleteModelAndDept(Map map);
}
