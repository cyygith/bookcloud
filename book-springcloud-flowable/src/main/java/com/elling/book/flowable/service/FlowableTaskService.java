package com.elling.book.flowable.service;

import java.util.Map;

public interface FlowableTaskService {
	
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
	public String startProcess(Map<String,Object> parmMap);
	
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
	public String startProcessAndSubmitFirstTask(Map<String,Object> parmMap);
	
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
	public String claimAndSubmitToNextTask(Map<String,Object> parmMap);
	
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
	public String submitToNextTask(Map<String,Object> parmMap);
	
	
	
}
