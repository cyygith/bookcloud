package com.elling.book.sys.sys.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elling.book.sys.common.base.AbstractService;
import com.elling.book.sys.common.entity.TreeNode;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.sys.dao.mapper.SysMenuMapper;
import com.elling.book.sys.sys.dao.mapper.SysRoleMapper;
import com.elling.book.sys.sys.dao.mapper.SysRoleMenuMapper;
import com.elling.book.sys.sys.model.SysRole;
import com.elling.book.sys.sys.model.SysRoleMenu;
import com.elling.book.sys.sys.service.SysRoleService;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by cyy on 2019/08/01.
 */
@Service
public class SysRoleServiceImpl extends AbstractService<SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

	@Override
	public List<SysRole> getSysRolebyCondition(Map<String,Object> map) {
		return sysRoleMapper.getSysRolebyUserId(map);
	}
	
	@Override
	public List<TreeNode> getMenuDataByRoleId(Map map){
		return sysMenuMapper.getMenuData(map);
	}

	@Override
	public int saveModelAndMenu(SysRole sysRole) {
		this.save(sysRole);
		//保存完设置roleId
		Long roleId = sysRole.getRoleId();
		sysRole.getMenus().forEach(item->{
			item.setRoleId(roleId);
		});
		//保存sys_role_men表
		int count = sysRoleMapper.saveRoleMenus(sysRole.getMenus());
		return count;
	}
	
	@Override
	public int updateModelAndMenu(SysRole sysRole) {
		this.update(sysRole);//更新sysrole
		sysRoleMapper.deletMenuRoleByRoleId(sysRole.getRoleId());//删除sys_role_menu中的数据
		//保存sys_role_men表
		int count = sysRoleMapper.saveRoleMenus(sysRole.getMenus());
		return count;
	}
	
	@Override
	public int deleteModelAndMenu(Map map) {
		String ids = StringUtil.getString(map.get("ids"));
		this.deleteByIds(ids);//1.删除sys_role   暂时注释掉
		List<String> idsList = Arrays.asList(ids.split(","));
		sysRoleMapper.deletMenuRoleByRoleIds(idsList);//2.删除sys_role_menu中的数据
		return 0;
	}
}
