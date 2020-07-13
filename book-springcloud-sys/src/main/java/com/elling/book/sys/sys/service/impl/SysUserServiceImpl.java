package com.elling.book.sys.sys.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elling.book.sys.common.base.AbstractService;
import com.elling.book.sys.common.entity.TreeNode;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.sys.dao.mapper.SysDeptMapper;
import com.elling.book.sys.sys.dao.mapper.SysUserMapper;
import com.elling.book.sys.sys.model.SysUser;
import com.elling.book.sys.sys.service.SysUserService;

/**
 *
 * Created by cyy on 2019/08/14.
 */
@Service
public class SysUserServiceImpl extends AbstractService<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
	public int saveModelAndDept(SysUser sysUser) {
		this.save(sysUser);
		//保存完设置userId
		Long userId = sysUser.getUserId();
		sysUser.getDepts().forEach(item->{
			item.setUserId(userId);
		});
		sysUser.getRoles().forEach(item->{
			item.setUserId(userId);
		});
		
		//保存sys_user_dept表
		int count = 0;
		if(sysUser.getDepts().size()>0) {
			count = sysUserMapper.saveUserDept(sysUser.getDepts());
		}
		//保存sys_user_role表
		if(sysUser.getRoles().size()>0) {
			sysUserMapper.saveUserRole(sysUser.getRoles());
		}
		return count;
	}

	@Override
	public int updateModelAndDept(SysUser sysUser) {
		this.update(sysUser);//更新sysUser
		sysUserMapper.deleteUserDeptByUserId(sysUser.getUserId());//删除sys_user_dept中的数据
		sysUserMapper.deleteUserRoleByUserId(sysUser.getUserId());//删除sys_user_role中的数据
		//保存sys_user_dept表
		int count = 0;
		if(sysUser.getDepts().size()>0) {
			count = sysUserMapper.saveUserDept(sysUser.getDepts());
		}
		//保存sys_user_role表
		if(sysUser.getRoles().size()>0) {
			sysUserMapper.saveUserRole(sysUser.getRoles());
		}
		return count;
	}

	@Override
	public int deleteModelAndDept(Map map) {
		String ids = StringUtil.getString(map.get("ids"));
		this.deleteByIds(ids);//1.删除sysUser
		List<String> idsList = Arrays.asList(ids.split(","));
		sysUserMapper.deleteUserDeptByUserIds(idsList);//2.删除sys_user_dept中的数据
		sysUserMapper.deleteUserRoleByUserIds(idsList);//2.删除sys_user_dept中的数据
		return 0;
	}
	
	@Override
	public List<TreeNode> getRoleByUserId(Map map) {
		return sysUserMapper.getRoleByUserId(map);
	}
}
