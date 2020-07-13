package com.elling.book.sys.accbook.controller;
import com.elling.book.sys.accbook.model.AccBook;
import com.elling.book.sys.accbook.service.AccBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.apache.log4j.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.utils.DateUtil;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.common.entity.Result;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * Created by CYY on 2020-01-03 10:44:03.
 */
@RestController
@RequestMapping("/accBook/")
public class AccBookController {
	private static Logger logger = Logger.getLogger(AccBookController.class);
	
    @Autowired
    AccBookService accBookService;

    @RequestMapping("add")
    public Result add(@RequestBody AccBook accBook) {
    	try {
    		accBook.setCreateTime(DateUtil.getNowTime());
	        accBookService.save(accBook);
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
		    accBookService.deleteById(id);
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
    		accBookService.deleteByIds(ids);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("update")
    public Result update(@RequestBody AccBook accBook) {
    	try {
    		accBook.setUpdateTime(DateUtil.getNowTime());
		    accBookService.update(accBook);
		}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }
    
    @RequestMapping("saveOrUpdate")
    public Result saveOrupdate(@RequestBody AccBook accBook) {
    	try {
    		if(accBook.getId()!=null) {
    			accBook.setUpdateTime(DateUtil.getNowTime());
    			accBookService.update(accBook);
    		}else {
    			accBook.setCreateTime(DateUtil.getNowTime());
    	        accBookService.save(accBook);
    		}
		}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }

    @RequestMapping("detail")
    public Result detail(@RequestParam Long id) {
    	AccBook accBook = null;
        try {
	        accBook = accBookService.findById(id);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success(accBook);
    }
    
    @RequestMapping("getByCondition")
    public Result getByCondition(AccBook accBook) {
    	Map rMap = null;
    	try {
    		List<Map<String,Object>> list = accBookService.getByCondition(accBook);
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
	        List<AccBook> list = accBookService.findAll();
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
	        AccBook accBook = accBookService.findBy(colomnName, colomnVal);
	        return Result.success(accBook);
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
    		
    		Condition condition = new Condition(AccBook.class);
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
    		List<AccBook> list = accBookService.findByCondition(condition);
            PageInfo pageInfo = new PageInfo(list);
    		return Result.success(pageInfo);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
    
    @RequestMapping("getListByTime")
    public Result getListByTime(@RequestBody Map map) {
    	try {
    		String month = map.get("searchTime")+"";
    		String userId = map.get("userId")+"";
    		if(month==null||month.equals("")) month = DateUtil.getNowYearAndMonth();
    		
    		
    		Condition condition = new Condition(AccBook.class);
    		condition.setOrderByClause("time,create_time desc");
    		Criteria criteria  = condition.createCriteria();
    		criteria.andEqualTo("userId", userId);
    		criteria.andCondition("DATE_FORMAT(time,'%Y-%m')='"+month+"'");
    		
    		List<AccBook> list = accBookService.findByCondition(condition);
    		Map dMap = new HashMap();
    		dMap.put("day", month); 
    		dMap.put("userId", userId);
    		List<Map<String,Object>> dayList = accBookService.getSumByTypeAndTime(dMap);
    		
    		Map mMap = new HashMap();
    		mMap.put("month", month);
    		mMap.put("userId", userId);
    		List<Map<String,Object>> monthList = accBookService.getSumByTypeAndTime(mMap);
    		
    		//总共的时间天数问题
    		Map dmMap = new HashMap();
    		dmMap.put("month", month);
    		dmMap.put("userId", userId);
    		List<Map<String,Object>> dayOfmonthList = accBookService.getDayOfMonth(dmMap);
    		
    		//每天详细数据
    		Map<String,ArrayList> timeMap = new HashMap<String,ArrayList>();
    		list.forEach(item->{
    			String itime = item.getTime();
    			if(timeMap.get(itime)==null) {
    				ArrayList<AccBook> ll = new ArrayList<AccBook>();
    				ll.add(item);
    				timeMap.put(itime, ll);
    			}else {
    				timeMap.get(itime).add(item);
    			}
    		});
    		
    		//当天总计
    		Map<String,Map> sumMap = new HashMap<String,Map>();
    		dayList.forEach(item->{
    			String itime = item.get("TIME").toString()+"_day_sum";
    			if(sumMap.get(itime)==null) {
    				Map tmp = new HashMap();
    				tmp.put(item.get("TYPE"), item.get("SUM"));
    				sumMap.put(itime, tmp);
    			}else {
    				sumMap.get(itime).put(item.get("TYPE"), item.get("SUM"));
    			}
    		});
    		//当月总计
    		Map<String,String> monthSumMap = new HashMap<String,String>();
    		monthList.forEach(item->{
    			String type = item.get("TYPE")+"";
    			String money = item.get("SUM")+"";
    			if(type.equals("0")) monthSumMap.put("out", money);
    			if(type.equals("1")) monthSumMap.put("in", money);
    		});
    		
    		Map resultMap = new HashMap();
    		resultMap.put("dayOfmonthList", dayOfmonthList);
    		resultMap.put("timeMap", timeMap);
    		resultMap.put("sumMap", sumMap);
    		resultMap.put("monthSumMap", monthSumMap);
    		
    		return Result.success(resultMap);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
    
    @RequestMapping("getAllSummary")
    public Result getAllSummary(@RequestBody Map map) {
    	try {
    		Map resultMap = accBookService.getSumDayAndSumCount(map);
    		return Result.success(resultMap);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
    
}
