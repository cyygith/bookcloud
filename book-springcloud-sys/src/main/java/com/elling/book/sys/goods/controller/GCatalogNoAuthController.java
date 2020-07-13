package com.elling.book.sys.goods.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elling.book.sys.common.constant.Config;
import com.elling.book.sys.common.entity.Result;
import com.elling.book.sys.common.entity.TreeNode;
import com.elling.book.sys.common.utils.DateUtil;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.common.utils.TreeBuilder;
import com.elling.book.sys.goods.model.GCatalog;
import com.elling.book.sys.goods.service.GCatalogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by cyy on 2019/09/08.
 * 无需验证的controller
 */
@RestController
@RequestMapping("/gCatalogNoAuth/")
public class GCatalogNoAuthController {
	private static Logger logger = Logger.getLogger(GCatalogNoAuthController.class);
	private static String UPLOAD_URL = "";
	private static String separator = File.separator;
    @Autowired
    GCatalogService gCatalogService;
    
    @RequestMapping("getCatalogLeftMenu")
    public Result getCatalogLeftMenu(GCatalog gCatalog) {
    	try {
    		List<TreeNode> catalogTree = gCatalogService.getCatalogTree(gCatalog);
    		List resultList = TreeBuilder.buildTree(catalogTree);
    		return Result.success(resultList);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
    
    @RequestMapping("getCatalogTree")
    public Result getCatalogTree(GCatalog gCatalog) {
    	try {
    		List<TreeNode> catalogTree = gCatalogService.getCatalogTree(gCatalog);
    		return Result.success(catalogTree);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
    @RequestMapping("getCatalogById")
    public Result getCatalogById(GCatalog gCatalog) {
    	try {
    		GCatalog gc = gCatalogService.getCatalogById(gCatalog);
    		return Result.success(gc);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
    
    
    @RequestMapping("add")
    public Result add(@RequestBody GCatalog gCatalog) {
    	try {
    		gCatalog.setCreateTime(DateUtil.getNowTime());
	        gCatalogService.save(gCatalog);
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
		    gCatalogService.deleteById(id);
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
    		String[] idsarr = ids.split(",");
    		List<String> idsArr = Arrays.asList(idsarr);
    		gCatalogService.deleteByUuids(idsArr);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("update")
    public Result update(GCatalog gCatalog) {
    	try {
    		gCatalog.setUpdateTime(DateUtil.getNowTime());
		    gCatalogService.update(gCatalog);
		}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("detail")
    public Result detail(@RequestParam String id) {
    	GCatalog gCatalog = null;
        try {
	        gCatalog = gCatalogService.findBy("id", id);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success(gCatalog);
    }

    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageInfo pageInfo = null;
        try {
	        PageHelper.startPage(page, size);
	        List<GCatalog> list = gCatalogService.findAll();
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
	        GCatalog gCatalog = gCatalogService.findBy(colomnName, colomnVal);
	        return Result.success(gCatalog);
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
    		
    		Condition condition = new Condition(GCatalog.class);
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
    		condition.orderBy("createTime").desc();
    		
    		PageHelper.startPage(page, size);
    		List<GCatalog> list = gCatalogService.findByCondition(condition);
            PageInfo pageInfo = new PageInfo(list);
    		return Result.success(pageInfo);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return Result.error(e.getMessage());
    	}
    }
}
