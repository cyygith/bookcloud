package com.elling.book.flowable.service;

import java.util.Map;

import com.elling.book.flowable.entity.TaskQuery;

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
	 * 启动流程节点(多租户方式启动)
	 * 1.这里与上面springboot启动不同之处在于，springboot自启动方式在微服务启动之时就将对应的流程xml部署到ACT_RE_PROCEST中了，启动时检查文件是否更新。
	 * 2.这里的多租户启动方式为：根据传入的tenantId进行xml部署，每次启动一个新流程的时候就部署一次，使用该部署的流程相关表都是继承该TENANT_ID，
	 *  .缺点是:每次启动都得部署一次,会牺牲掉性能
	 *  .该种部署方式需要改掉对应xxx-application.xml的配置文件中对应部署为false：deploy-resources:false
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
	public String startProcessWithMultiTenant(Map<String,Object> parmMap);
	
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
	 * 启动流程并提交首节点(多租户方式启动)
	 * 1.这里与上面springboot启动不同之处在于，springboot自启动方式在微服务启动之时就将对应的流程xml部署到ACT_RE_PROCEST中了，启动时检查文件是否更新。
	 * 2.这里的多租户启动方式为：根据传入的tenantId进行xml部署，每次启动一个新流程的时候就部署一次，使用该部署的流程相关表都是继承该TENANT_ID，
	 *  .缺点是:每次启动都得部署一次,会牺牲掉性能
	 *  .该种部署方式需要改掉对应xxx-application.xml的配置文件中对应部署为false：deploy-resources:false
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
	public String startProcessAndSubmitFirstTaskWithMultiTenant(Map<String,Object> parmMap);
	
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
	
	/**
	 *  退回上个节点（无并行任务的流程情况下）
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  processId:xxx,			//流程实例ID（必须）
	 *  taskId:xxx,				//任务ID（必须）
	 *  currTaskKeys:xxx,		//驳回发起的当前节点key 为  act_ru_task 中TASK_DEF_KEY_ 字段的值
	 *  targetKey:xxx,  		//目标节点的key  为act_hi_taskinst 中 TASK_DEF_KEY_
	 * }
	 * @return
	 */
	public String backToLastTask(TaskQuery taskQuery);
	
	/**
	 *  转办，这里的转办只是改变当前任务的办理人而已，不会产生流转记录
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  userId:xxx,			//提交用户ID（必须）
	 *  taskId:xxx,			//当前任务ID（必须）
	 * }
	 * @return
	 */
	public String turnTaskToPerson(Map<String,Object> parmMap);
	/**
	 *  撤销流程（终止流程）
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  processId:xxx,			//流程实例ID（必须）
	 *  taskId:xxx,			//当前任务ID（必须）
	 * }
	 * @return
	 */
	public String cancelProcess(TaskQuery taskQuery);
	
	
}
