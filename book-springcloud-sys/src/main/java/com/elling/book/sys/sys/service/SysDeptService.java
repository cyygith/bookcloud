package com.elling.book.sys.sys.service;
import com.elling.book.sys.sys.model.SysDept;
import com.elling.book.sys.sys.model.SysRole;

import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.base.Service;

/**
 *
 * Created by cyy on 2019/08/14.
 */
public interface SysDeptService extends Service<SysDept> {
	/**
	 * .获取dept层级
	 * @return
	 */
	public List<SysDept> getDeptLevel(SysDept sysDept);
	
	/**
	 * 获取左边菜单数据，根据传入用户id进行筛选(组装成树形结构)
	 * @param map
	 * @return
	 */
	public List getDeptDataByUserID(Map map);
	
	/**
	 * 获取左边菜单数据，根据传入用户id进行筛选
	 * @param map
	 * @return
	 */
	public List getDeptByUserID(Map map);
	
	
	/**
	 * 获取所有的部门节点数据
	 * @param map
	 * @return
	 */
	public List getAllDeptData(Map map);
	
	
}
