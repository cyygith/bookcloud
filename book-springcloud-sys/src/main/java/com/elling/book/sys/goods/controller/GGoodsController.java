package com.elling.book.sys.goods.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elling.book.sys.common.constant.Config;
import com.elling.book.sys.common.entity.Result;
import com.elling.book.sys.common.utils.DateUtil;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.goods.model.GCatalog;
import com.elling.book.sys.goods.model.GGoods;
import com.elling.book.sys.goods.service.GGoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by cyy on 2019/08/23.
 */
@RestController
@RequestMapping("/gGoods/")
public class GGoodsController {
	private static Logger logger = Logger.getLogger(GGoodsController.class);
	private static String UPLOAD_URL = "";
	private static String separator = File.separator;
    @Autowired
    GGoodsService gGoodsService;
    
    @SuppressWarnings("rawtypes")
	@RequestMapping("saveIncludeImage")
    public Result saveIncludeImage(@RequestParam("images") MultipartFile[] file,GGoods gGoods,String opType) {
    	try {
    		//save image if have a file
    		if(file.length>0) {
    			UPLOAD_URL = Config.getConf("upload.url");
    			String baseDir = separator + "UPLOAD" + separator + DateUtil.getNowDate1() + separator;
    			String filePath = UPLOAD_URL+ baseDir ;
    			File dir = new File(filePath);
    			if(!dir.exists()) {
    				boolean b = dir.mkdirs();
    				System.out.println("是否创建成功："+b);
    			}
    			List<String> filePaths = new ArrayList<String>();
    			String filename = "";
    			String suffix = "";
    			String uuid = "";
    			String code = "";
    			for(int i=0;i<file.length;i++) {
    				MultipartFile filetemp = file[i];
    				filename = filetemp.getOriginalFilename();
    				suffix = filename.substring(filename.lastIndexOf("."));
    				uuid = UUID.randomUUID().toString().replaceAll("-", "");
    				code = uuid+suffix;
    				filetemp.transferTo(new File(filePath+"/"+code));
    				filePaths.add(code);
    			}
    			
    			gGoods.setImgUrl(StringUtils.join(filePaths.iterator(), ","));
    			gGoods.setImgDir(baseDir);
    		}
    		
    		if(opType.equals("add")) {
    			//save ggoods
    			gGoods.setCreateTime(DateUtil.getNowTime());
    			gGoodsService.save(gGoods);
    		}else {
    			//update ggoods
    			gGoods.setUpdateTime(DateUtil.getNowTime());
    			gGoodsService.update(gGoods);
    		}
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }
    
    
    @RequestMapping("add")
    public Result add(@RequestBody GGoods gGoods) {
    	try {
	        gGoodsService.save(gGoods);
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
		    gGoodsService.deleteById(id);
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
//    		gGoodsService.deleteByIds(ids);
    		List<String> idsArr = Arrays.asList(idsarr);
    		gGoodsService.deleteByUuids(idsArr);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("update")
    public Result update(GGoods gGoods) {
    	try {
    		gGoods.setUpdateTime(DateUtil.getNowTime());
		    gGoodsService.update(gGoods);
		}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("detail")
    public Result detail(@RequestParam String id) {
    	GGoods gGoods = null;
        try {
	        gGoods = gGoodsService.findBy("id", id);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success(gGoods);
    }
    @RequestMapping("getGoodsById")
    public Result getGoodsById(GGoods gGoods) {
    	try {
    		GGoods gg = gGoodsService.getGoodsById(gGoods);
    		return Result.success(gg);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }

    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageInfo pageInfo = null;
        try {
	        PageHelper.startPage(page, size);
	        List<GGoods> list = gGoodsService.findAll();
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
	        GGoods gGoods = gGoodsService.findBy(colomnName, colomnVal);
	        return Result.success(gGoods);
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
    		
    		Condition condition = new Condition(GGoods.class);
    		Criteria criteria  = condition.createCriteria();
    		Iterator it = map.entrySet().iterator();
    		while(it.hasNext()) {
    			Map.Entry<String,Object> entry = (Map.Entry)it.next();
    			String key = entry.getKey();
    			Object value = entry.getValue();
    			if(StringUtil.isNotEmpty(value)) {
//    				criteria.andEqualTo(key, value);
    				criteria.andLike(key, "%"+StringUtil.getString(value)+"%");
    			}
    		}
    		condition.orderBy("createTime").desc();
    		
    		PageHelper.startPage(page, size);
    		List<GGoods> list = gGoodsService.findByCondition(condition);
            PageInfo pageInfo = new PageInfo(list);
    		return Result.success(pageInfo);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return Result.error(e.getMessage());
    	}
    }
}
