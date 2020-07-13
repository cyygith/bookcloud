package com.elling.book.sys.tool.controller;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elling.book.sys.common.entity.Result;
import com.elling.book.sys.common.utils.DateUtil;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.tool.generator.run.IRunCode;
import com.elling.book.sys.tool.model.ToolGenCode;
import com.elling.book.sys.tool.service.ToolGenCodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by cyy on 2019-12-20 09:50:31.
 */
@RestController
@RequestMapping("/toolGenCode/")
public class ToolGenCodeController {
	private static Logger logger = Logger.getLogger(ToolGenCodeController.class);
	
    @Autowired
    ToolGenCodeService toolGenCodeService;
    
    @RequestMapping("runTool")
    public Result runTool(@RequestBody Map map) {
    	StringBuffer sb = new StringBuffer();
    	try {
    		String ids = StringUtil.getString(map.get("ids"));
    		if(ids.trim().equals(""))
    			return Result.error("请输入ids");
    		
    		List<ToolGenCode> list = toolGenCodeService.findByIds(ids);
    		if(null!=list&&list.size()>0) {
    			for(ToolGenCode gen : list) {
    				String className = gen.getRunClass();
        			if(className.trim().equals("")) {
        				return Result.error("需要执行的类为空，无法执行，请重新编辑执行参数");
        			}else {
        				Class newClass = null;
        				try {
        					newClass = Class.forName(className);
        				}catch(Exception e) {
        					logger.error("无法根据描述符【"+className+"】加载对应的数据类");
        					System.out.println("无法根据描述符【"+className+"】加载对应的数据类");
        					return Result.error("无法根据描述符【"+className+"】加载对应的数据类");
        				}
        				
        				Object newObject = null;
        				try {
        					newObject = newClass.newInstance();
        				}catch(Exception e) {
        					logger.error("对应的类【"+className+"】无法实例化数据对象,reason:"+e.getMessage());
        					System.out.println("对应的类【"+className+"】无法实例化数据对象,reason:"+e.getMessage());
        					return Result.error("对应的类【"+className+"】无法实例化数据对象,reason:"+e.getMessage());
        				}
        				
        				IRunCode runCode = (IRunCode)newObject;
        				String rtnStr = runCode.runCode(gen);
        				sb.append(rtnStr);
        				
        				gen.setRunTime(DateUtil.getNowTime());
        				gen.setRunCount(gen.getRunCount()+1);
        				toolGenCodeService.update(gen);//执行成功之后自动更新执行状态
        			}
    			}
    		}else {
    			return Result.error("数据库中不存在传入的数据，请重新检查");
    		}
    		
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    	System.out.println("生成情况如下：");
    	System.out.println(sb.toString());
	    return Result.success(sb);
    }
    
    @RequestMapping("add")
    public Result add(@RequestBody ToolGenCode toolGenCode) {
    	try {
	        toolGenCodeService.save(toolGenCode);
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
		    toolGenCodeService.deleteById(id);
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
    		toolGenCodeService.deleteByIds(ids);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("update")
    public Result update(@RequestBody ToolGenCode toolGenCode) {
    	try {
		    toolGenCodeService.update(toolGenCode);
		}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("detail")
    public Result detail(@RequestParam Long id) {
    	ToolGenCode toolGenCode = null;
        try {
	        toolGenCode = toolGenCodeService.findById(id);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success(toolGenCode);
    }
    
    @RequestMapping("getByCondition")
    public Result getByCondition(ToolGenCode toolGenCode) {
    	Map rMap = null;
    	try {
    		List<Map<String,Object>> list = toolGenCodeService.getByCondition(toolGenCode);
    		if(list!=null && list.size()>0) {
    			rMap = list.get(0);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
        return Result.success(rMap);
    }

    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageInfo pageInfo = null;
        try {
	        PageHelper.startPage(page, size);
	        List<ToolGenCode> list = toolGenCodeService.findAll();
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
	        ToolGenCode toolGenCode = toolGenCodeService.findBy(colomnName, colomnVal);
	        return Result.success(toolGenCode);
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error(e.getMessage());
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
    		
    		Condition condition = new Condition(ToolGenCode.class);
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
    		condition.setOrderByClause("ID DESC");
    		
    		PageHelper.startPage(page, size);
    		List<ToolGenCode> list = toolGenCodeService.findByCondition(condition);
            PageInfo pageInfo = new PageInfo(list);
    		return Result.success(pageInfo);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
}
