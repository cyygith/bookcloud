package com.elling.book.flowable.controller;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elling.book.flowable.common.entity.Page;
import com.elling.book.flowable.common.entity.Result;
import com.elling.book.flowable.entity.TaskQuery;
import com.elling.book.flowable.service.TaskQueryService;
import com.elling.book.flowable.util.MyStringUtil;

@RestController
@RequestMapping(value="flowable/history")
public class FlowableHistoryController {
	private static Logger logger = LoggerFactory.getLogger(FlowableHistoryController.class);
	
	@Autowired
	private TaskQueryService taskQueryService;
	
	
	/**
	 * 【我发起的】流程实例查询
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  userId:xxx,			//提交用户ID（必须）
	 *  processDefId:xxx	//流程定义ID
	 *  xxx:xxx				//其它流程中需要的参数（选择性）
	 *  ....
	 * }
	 * @return processId 	//流程实例ID
	 */
	@RequestMapping(value="listByMyStartProcess")
	public Result listByMyStartProcess(Page page,TaskQuery taskQuery) {
		try {
    		if(MyStringUtil.isNullOrEmpty(taskQuery.getUserId())) {
    			return Result.error("【userId不能为空】");
    		}
    		
    		taskQueryService.getProcessByMyStart(page,taskQuery);
    		return Result.success(page.getData(),page.getTotal());
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error("查询失败，请联系管理员处理："+e.getMessage());
    		return Result.error("查询失败，请联系管理员处理："+e.getMessage());
    	}
	}
	/**
	 * 根据条件获取已办列表
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  userId:xxx,			//提交用户ID（必须）
	 *  processDefId:xxx	//流程定义ID
	 *  ....
	 * }
	 */
	@RequestMapping(value="getDoneListByCondition")
	public Result getDoneListByCondition(Page page,TaskQuery taskQuery) {
		try {
    		if(MyStringUtil.isNullOrEmpty(taskQuery.getUserId())) {
    			return Result.error("【userId不能为空】");
    		}
    		
    		taskQueryService.getDoneListByCondition(page,taskQuery);
    		return Result.success(page.getData(),page.getTotal());
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error("查询失败，请联系管理员处理："+e.getMessage());
    		return Result.error("查询失败，请联系管理员处理："+e.getMessage());
    	}
	}
	/**
	 * 根据条件获取流程的历史执行信息
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  processId:xxx		//流程实例ID（必须）
	 * }
	 */
	@RequestMapping(value="getHistoryByProcessId")
	public Result getHistoryByProcessId(Page page,TaskQuery taskQuery) {
		try {
    		if(MyStringUtil.isNullOrEmpty(taskQuery.getProcessId())) {
    			return Result.error("【processId不能为空】");
    		}
    		
    		taskQueryService.getDoneListByCondition(page,taskQuery);
    		return Result.success(page.getData(),page.getTotal());
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error("查询失败，请联系管理员处理："+e.getMessage());
    		return Result.error("查询失败，请联系管理员处理："+e.getMessage());
    	}
	}
}
