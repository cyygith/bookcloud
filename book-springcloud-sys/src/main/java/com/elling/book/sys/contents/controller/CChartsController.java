package com.elling.book.sys.contents.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elling.book.sys.common.entity.Result;
import com.elling.book.sys.contents.service.CChartsService;

/**
*
* Created by cyy on 2019-11-22 15:01:45.
*/
@RestController
@RequestMapping("/cCharts/")
public class CChartsController {
	private static Logger logger = Logger.getLogger(CChartsController.class);
	
	@Autowired
	CChartsService cChartsService;
	
	/**
	 * .获取商品内容列表数据
	 * @return
	 */
	@RequestMapping("getCLineCharts")
	public Result getCLineCharts(@RequestBody Map map) {
		List list = null;
		try {
			list = cChartsService.getCLineCharts(map);
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success(list);
		
	}
	
	
	
}
