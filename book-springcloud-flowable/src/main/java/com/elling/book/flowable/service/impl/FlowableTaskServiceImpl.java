package com.elling.book.flowable.service.impl;

import java.util.Map;

import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elling.book.flowable.service.FlowableTaskService;
import com.elling.book.flowable.util.MyStringUtil;

@Service("flowableTaskService")
public class FlowableTaskServiceImpl implements FlowableTaskService{
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * 启动流程节点g
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
	@Transactional(rollbackFor = Exception.class)
	public String startProcess(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String templateId = MyStringUtil.getString(parmMap.get("templateId"));
		String businessId = MyStringUtil.getString(parmMap.get("businessId"));
		
		Authentication.setAuthenticatedUserId(userId);//设置流程发起人
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(templateId, businessId);
		return processInstance.getId();
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
	@Transactional(rollbackFor = Exception.class)
	public String startProcessAndSubmitFirstTask(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String templateId = MyStringUtil.getString(parmMap.get("templateId"));
		String businessId = MyStringUtil.getString(parmMap.get("businessId"));
		
		//去除掉必须的参数，剩余的参数为流程必须参数
		parmMap.remove("userId");
		parmMap.remove("templateId");
		parmMap.remove("businessId");
		
		Authentication.setAuthenticatedUserId(userId);//设置流程发起人
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(templateId, businessId);
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessId).singleResult();
		task.setAssignee(userId);
		taskService.complete(task.getId(),parmMap);//提交并完成任务
		return processInstance.getId();
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
	@Transactional(rollbackFor = Exception.class)
	public String claimAndSubmitToNextTask(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String taskId = MyStringUtil.getString(parmMap.get("taskId"));
		
		//去除掉必须的参数，剩余的参数为流程必须参数
		parmMap.remove("userId");
		parmMap.remove("taskId");
		taskService.claim(taskId, userId);
		taskService.complete(taskId,parmMap);
		return taskId;
	}
	
	/**
	 * 直接提交下一个节点
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
	@Transactional(rollbackFor = Exception.class)
	public String submitToNextTask(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String taskId = MyStringUtil.getString(parmMap.get("taskId"));
		
		//去除掉必须的参数，剩余的参数为流程必须参数
		parmMap.remove("userId");
		parmMap.remove("taskId");
		taskService.complete(taskId,parmMap);
		return taskId;
	}

}
