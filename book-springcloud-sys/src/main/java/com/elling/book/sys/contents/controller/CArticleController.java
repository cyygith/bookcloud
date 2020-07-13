package com.elling.book.sys.contents.controller;
import com.elling.book.sys.contents.model.CArticle;
import com.elling.book.sys.contents.service.CArticleService;
import com.elling.book.sys.goods.model.GGoods;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

import org.apache.log4j.Logger;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.elling.book.sys.common.utils.DateUtil;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.common.constant.Config;
import com.elling.book.sys.common.entity.Result;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by cyy on 2019-10-25 15:01:45.
 */
@RestController
@RequestMapping("/cArticle/")
public class CArticleController {
	private static Logger logger = Logger.getLogger(CArticleController.class);
	
    @Autowired
    CArticleService cArticleService;
    
    @RequestMapping("addHtml")
    public Result addHtml(@RequestBody CArticle cArticle) {
    	try {
    		cArticle.setCreateTime(DateUtil.getNowTime());
	        cArticleService.save(cArticle);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }
    
    @RequestMapping("add")
    public Result add(@RequestBody CArticle cArticle) {
    	try {
    		cArticle.setCreateTime(DateUtil.getNowTime());
	        cArticleService.save(cArticle);
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
		    cArticleService.deleteById(id);
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
    		cArticleService.deleteByIds(ids);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("update")
    public Result update(@RequestBody CArticle cArticle) {
    	try {
    		cArticle.setUpdateTime(DateUtil.getNowTime());
		    cArticleService.update(cArticle);
		}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("detail")
    public Result detail(@RequestParam Long id) {
    	CArticle cArticle = null;
        try {
	        cArticle = cArticleService.findById(id);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success(cArticle);
    }

    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageInfo pageInfo = null;
        try {
	        PageHelper.startPage(page, size);
	        List<CArticle> list = cArticleService.findAll();
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
	        CArticle cArticle = cArticleService.findBy(colomnName, colomnVal);
	        return Result.success(cArticle);
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
    		
    		Condition condition = new Condition(CArticle.class);
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
    		List<CArticle> list = cArticleService.findByCondition(condition);
            PageInfo pageInfo = new PageInfo(list);
    		return Result.success(pageInfo);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return Result.error(e.getMessage());
    	}
    }
    
    /**
     * 保存图片并且返回图片的相对地址，
     * 比如图片放在d:/upload/20191101/test.jpg中，则这里的imgUrl则为20191101/test.jpg
     * 然后再tomcat（window）或者nginx（linux）中配置图片服务器的地址，
     * 则访问该图片为：http://localhost:8080/upload/20191101/test.jpg
     * @param file
     * @return
     */
    @RequestMapping("uploadImage")
    public Result uploadImge(@RequestParam("images") MultipartFile[] file) {
    	StringBuffer imgUrl = new StringBuffer();//种类的imgUrl是相对地址，比如图片放在d:/upload/20191101/test.jpg中，则这里的imgUrl则为20191101/test.jpg
    	try {
    		String UPLOAD_URL = Config.getConf("upload.url");
    		String separator = File.separator;
    		String dateSuffix = DateUtil.getNowDate1();
    		String baseDir = separator + "ARTICLE" + separator + dateSuffix + separator;//地址：/ARTICLE/20191101/
    		String filePath = UPLOAD_URL+ baseDir ;//完整地址：D:/UPLOAD/ARTICLE/20191101/
    		File dir = new File(filePath);
			if(!dir.exists()) {
				boolean b = dir.mkdirs();
				logger.info("目录是否创建成功"+b+",路径："+filePath);
				System.out.println("是否创建成功："+b);
			}
			
			String filename = "";
			imgUrl.append("ARTICLE/").append(dateSuffix).append("/");
			for(int i=0;i<file.length;i++) {
				MultipartFile filetemp = file[i];
				filename = filetemp.getOriginalFilename();
				filetemp.transferTo(new File(filePath+filename));
				imgUrl.append(filename);
			}
			System.out.println("保存成功"+imgUrl);
			
    	}catch(Exception e) {
    		logger.error(e.getMessage());
    		return Result.error();
    	}
    	return Result.success(imgUrl);
    }
}
