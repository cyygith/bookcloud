package com.elling.book.sys.sys.controller;
import com.elling.book.sys.sys.model.SysDept;
import com.elling.book.sys.sys.model.SysMenu;
import com.elling.book.sys.sys.service.SysDeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.apache.log4j.Logger;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.common.entity.Result;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by cyy on 2019/08/14.
 */
@RestController
@RequestMapping("/sysDept/")
public class SysDeptController {
	private static Logger logger = Logger.getLogger(SysDeptController.class);
	
    @Autowired
    SysDeptService sysDeptService;

    @RequestMapping("add")
    public Result add(@RequestBody SysDept sysDept) {
    	try {
	        sysDeptService.save(sysDept);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("delete")
    public Result delete(Integer id) {
    	try {
		    sysDeptService.deleteById(id);
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
    		String ids = StringUtil.getString(map.get("ids"));
    		sysDeptService.deleteByIds(ids);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("update")
    public Result update(@RequestBody SysDept sysDept) {
    	try {
		    sysDeptService.update(sysDept);
		}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("detail")
    public Result detail(Long id) {
    	SysDept sysDept = null;
        try {
	        sysDept = sysDeptService.findById(id);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success(sysDept);
    }

    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageInfo pageInfo = null;
        try {
	        PageHelper.startPage(page, size);
	        List<SysDept> list = sysDeptService.findAll();
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
	        SysDept sysDept = sysDeptService.findBy(colomnName, colomnVal);
	        return Result.success(sysDept);
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
    		
    		Condition condition = new Condition(SysDept.class);
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
    		List<SysDept> list = sysDeptService.findByCondition(condition);
            PageInfo pageInfo = new PageInfo(list);
    		return Result.success(pageInfo);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return Result.error(e.getMessage());
    	}
    }
    
    @RequestMapping("getDeptLevel")
    public Result getDeptLevel(@RequestBody SysDept sysDept) {
    	List<SysDept> list = sysDeptService.getDeptLevel(sysDept);
    	return Result.success(list);
    }
    
    /**
     * 根据用户的ID获取所属部门id
     * @param map
     * @return
     */
    @RequestMapping("getDeptDataByUserID")
    public Result getDeptDataByUserID(@RequestBody Map map) {
    	List list = sysDeptService.getDeptDataByUserID(map);
    	System.out.println(list);
    	return Result.success(list);
    }
    
    /**
     * .获取所有的部门信息
     * @param sysMenu
     * @return
     */
    @RequestMapping("getAllDeptData")
    public Result getAllDeptData(@RequestBody SysMenu sysMenu) {
    	Map map = new HashMap();
    	List list = sysDeptService.getAllDeptData(map);
    	System.out.println(list);
    	return Result.success(list);
    }
}
