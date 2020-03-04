package com.elling.book.flowable.controller;

import java.util.Map;

import org.flowable.bpmn.model.UserTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elling.book.flowable.common.entity.Result;
import com.elling.book.flowable.service.FlowableTaskService;
import com.elling.book.flowable.service.NextTaskService;
import com.elling.book.flowable.util.MyStringUtil;

@RestController
@RequestMapping(value="flowable/process")
public class FlowableProcessController {
	private static Logger logger = LoggerFactory.getLogger(FlowableProcessController.class);
	
	@Autowired
	private FlowableTaskService flowableTaskService;
	
	@Autowired
	private NextTaskService nextTaskService;
	
	
	/**
	 * 启动流程节点
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 * 	templateKey:xxx,	//流程模板ID（必须）
	 *  userId:xxx,			//提交用户ID（必须）
	 *  businessId:xxx,		//业务编号ID（必须）
	 *  xxx:xxx				//其它流程中需要的参数（选择性）
	 *  ....
	 * }
	 * @return processId 	//流程实例ID
	 */
	@RequestMapping(value="startProcess")
	public Result startProcess(@RequestBody Map<String,Object> parmMap) {
		try {
    		String templateId = MyStringUtil.getString(parmMap.get("templateId"));
    		if(MyStringUtil.isNullOrEmpty(templateId)) {
    			return Result.error("【templateId不能为空】");
    		}
    		String userId = MyStringUtil.getString(parmMap.get("userId"));
    		if(MyStringUtil.isNullOrEmpty(userId)) {
    			return Result.error("【userId不能为空】");
    		}
    		String businessId = MyStringUtil.getString(parmMap.get("businessId"));
    		if(MyStringUtil.isNullOrEmpty(businessId)) {
    			return Result.error("【businessId不能为空】");
    		}
    		
    		flowableTaskService.startProcess(parmMap);
    		return Result.success();
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	}
	
	/**
	 * 启动流程并提交首节点
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 * 	templateKey:xxx,	//流程模板ID（必须）
	 *  userId:xxx,			//提交用户ID（必须）
	 *  businessId:xxx,		//业务编号ID（必须）
	 *  xxx:xxx				//其它流程中需要的参数（选择性）
	 *  ....
	 * }
	 * @return   taskId   //任务ID
	 */
    @RequestMapping(value = "startProcessAndSubmit")
    public Result startProcessAndSubmit(@RequestBody Map<String,Object> parmMap) {
    	
    	try {
    		String templateId = MyStringUtil.getString(parmMap.get("templateId"));
    		if(MyStringUtil.isNullOrEmpty(templateId)) {
    			return Result.error("【templateId不能为空】");
    		}
    		String userId = MyStringUtil.getString(parmMap.get("userId"));
    		if(MyStringUtil.isNullOrEmpty(userId)) {
    			return Result.error("【userId不能为空】");
    		}
    		String businessId = MyStringUtil.getString(parmMap.get("businessId"));
    		if(MyStringUtil.isNullOrEmpty(businessId)) {
    			return Result.error("【businessId不能为空】");
    		}
    		
    		String processId = flowableTaskService.startProcessAndSubmitFirstTask(parmMap);
    		return Result.success(processId);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
    
    /**
	 * 认领并提交下一个节点
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  userId:xxx,			//提交用户ID（必须）
	 *  taskId:xxx,			//当前任务ID（必须）
	 *  xxx:xxx				//其它流程中需要的参数（选择性）
	 *  ....
	 * }
	 * @return
	 */
    @RequestMapping(value = "claimAndSubmitToNextTask")
    public Result claimAndSubmitToNextTask(@RequestBody Map<String,Object> parmMap) {
    	try {
    		String taskId = MyStringUtil.getString(parmMap.get("taskId"));
    		if(MyStringUtil.isNullOrEmpty(taskId)) {
    			return Result.error("【taskId不能为空】");
    		}
    		String userId = MyStringUtil.getString(parmMap.get("userId"));
    		if(MyStringUtil.isNullOrEmpty(userId)) {
    			return Result.error("【userId不能为空】");
    		}
    		flowableTaskService.claimAndSubmitToNextTask(parmMap);
    		return Result.success();
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
    /**
	 * 认领并提交下一个节点
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  userId:xxx,			//提交用户ID（必须）
	 *  taskId:xxx,			//当前任务ID（必须）
	 *  xxx:xxx				//其它流程中需要的参数（选择性）
	 *  ....
	 * }
	 * @return
	 */
    @RequestMapping(value = "submitToNextTask")
    public Result submitToNextTask(@RequestBody Map<String,Object> parmMap) {
    	try {
    		String taskId = MyStringUtil.getString(parmMap.get("taskId"));
    		if(MyStringUtil.isNullOrEmpty(taskId)) {
    			return Result.error("【taskId不能为空】");
    		}
    		String userId = MyStringUtil.getString(parmMap.get("userId"));
    		if(MyStringUtil.isNullOrEmpty(userId)) {
    			return Result.error("【userId不能为空】");
    		}
    		flowableTaskService.submitToNextTask(parmMap);
    		return Result.success();
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }
    
    /**
	 * 获取下一个节点信息
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  taskId:xxx,			//当前任务ID（必须）
	 * }
	 * @return
	 */
    @RequestMapping(value = "getNextTaskFlowable")
    public Result getNextTaskFlowable(@RequestBody Map<String,Object> parmMap) {
    	try {
    		String taskId = MyStringUtil.getString(parmMap.get("taskId"));
    		if(MyStringUtil.isNullOrEmpty(taskId)) {
    			return Result.error("【taskId不能为空】");
    		}
    		UserTask task = nextTaskService.getNextTaskFlowable("next",taskId);
    		return Result.success(task);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
    }

}
