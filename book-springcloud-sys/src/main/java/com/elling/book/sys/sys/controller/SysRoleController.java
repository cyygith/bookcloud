package com.elling.book.sys.sys.controller;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elling.book.sys.common.entity.Result;
import com.elling.book.sys.common.utils.DateUtil;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.sys.model.SysRole;
import com.elling.book.sys.sys.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by cyy on 2019/08/01.
 */
@CrossOrigin
@RestController
@RequestMapping("/sysRole/")
public class SysRoleController {
	private static Logger logger = Logger.getLogger(SysRoleController.class);
    @Autowired
    SysRoleService sysRoleService;
    
    @CrossOrigin
    @RequestMapping("add")
    public Result add(@RequestBody SysRole sysRole) {
        try {
        	sysRole.setCreateTime(DateUtil.getNowTime());
            sysRoleService.saveModelAndMenu(sysRole);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("delete")
    public Result delete(@RequestParam Integer id) {
	    try {
	    	sysRoleService.deleteById(id);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }
    
    @RequestMapping("deleteByIds")
    public Result deleteByIds(@RequestBody Map map) {
    	try {
    		sysRoleService.deleteModelAndMenu(map);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("update")
    public Result update(@RequestBody SysRole sysRole) {
    	try {
    		sysRoleService.updateModelAndMenu(sysRole);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("detail")
    public Result detail(Long id) {
    	SysRole sysRole = null;
    	List menuList = null;
    	try {
    		sysRole = sysRoleService.findById(id);
    		if(null!=sysRole) {
    			Map map = new HashMap();
    			map.put("roleId", sysRole.getRoleId());
    			menuList = sysRoleService.getMenuDataByRoleId(map);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success(sysRole,menuList);
    }
    
    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
    	PageInfo pageInfo = null;
    	try {
    	   PageHelper.startPage(page, size);
           List<SysRole> list = sysRoleService.findAll();
           pageInfo = new PageInfo(list);
       }catch(Exception e) {
    	   e.printStackTrace();
	   	   logger.error(e.getMessage());
	   	   return Result.error(e.getMessage());
       }
        return Result.success(pageInfo);
    }
    
    @RequestMapping("getByColumn")
    public Result getByColumn(@RequestParam String colomnName, @RequestParam String colomnVal) {
        try{
	        SysRole sysRole = sysRoleService.findBy(colomnName, colomnVal);
	        return Result.success(sysRole);
        }catch(Exception e){
        	return Result.error(e.getMessage());
        }
    }
    
    @RequestMapping("getByExample")
    public Result getByExample(@RequestBody Map map) {
    	try {
    		int page = map.get("page")==null?1:(Integer.parseInt(StringUtil.getString(map.get("page"))));
    		int size = map.get("size")==null?10:(Integer.parseInt(StringUtil.getString(map.get("size"))));
    		map.remove("page");
    		map.remove("size");
    		
    		Condition condition = new Condition(SysRole.class);
    		Criteria criteria  = condition.createCriteria();
    		Iterator it = map.entrySet().iterator();
    		while(it.hasNext()) {
    			Map.Entry<String,Object> entry = (Map.Entry)it.next();
    			String key = entry.getKey();
    			Object value = entry.getValue();
    			if(StringUtil.isNotEmpty(value)) {
    				criteria.andEqualTo(key, value);
    			}
    		}
    		
    		PageHelper.startPage(page, size);
    		List<SysRole> list = sysRoleService.findByCondition(condition);
            PageInfo pageInfo = new PageInfo(list);
    		return Result.success(pageInfo);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return Result.error(e.getMessage());
    	}
    }
}
