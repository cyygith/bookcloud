package com.elling.book.flowable.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elling.book.flowable.dao.ActTemplatePathMapper;
import com.elling.book.flowable.dao.TaskQueryDao;
import com.elling.book.flowable.entity.ActTemplatePath;
import com.elling.book.flowable.entity.TaskQuery;
import com.elling.book.flowable.service.FlowableTaskService;
import com.elling.book.flowable.util.MyStringUtil;


@Service("flowableTaskService")
public class FlowableTaskServiceImpl implements FlowableTaskService{
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskQueryDao taskQueryDao;
	
	@Autowired
	private ActTemplatePathMapper actTemplatePathMapper;
	
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
	@Transactional(rollbackFor = Exception.class)
	public String startProcessWithMultiTenant(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String templateId = MyStringUtil.getString(parmMap.get("templateId"));
		String businessId = MyStringUtil.getString(parmMap.get("businessId"));
		String tenantId = MyStringUtil.getString(parmMap.get("tenantId"));//必输项（如果是有启用到多租户的情况）
		
		Authentication.setAuthenticatedUserId(userId);//设置流程发起人
		
		//从模板路径对照表中获取templateId和templatePath对照表
		ActTemplatePath actTemplatePath = new ActTemplatePath();
		actTemplatePath.setTemplateKey(templateId);
		actTemplatePath.setStatus("1");
		List<ActTemplatePath> templateList = actTemplatePathMapper.select(actTemplatePath);
		actTemplatePath = templateList.size()>0?templateList.get(0):null;
		
		String processId = "";
		if(actTemplatePath!=null) {
			//部署流程、启动流程并提交
			repositoryService.createDeployment().addClasspathResource(actTemplatePath.getTemplatePath()).tenantId(tenantId).deploy();
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(templateId, businessId, tenantId);
			processId = processInstance.getId();
		}else {
			throw new RuntimeException("该模板ID"+templateId+"在ACT_TEMPLATE_PATH中找不到对应配置路径，请联系管理员查询问题!");
		}
		return processId;
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
	public String startProcessAndSubmitFirstTaskWithMultiTenant(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String templateId = MyStringUtil.getString(parmMap.get("templateId"));
		String businessId = MyStringUtil.getString(parmMap.get("businessId"));
		String tenantId = MyStringUtil.getString(parmMap.get("tenantId"));//必输项（如果是有启用到多租户的情况）
		
		//去除掉必须的参数，剩余的参数为流程必须参数
		parmMap.remove("userId");
		parmMap.remove("templateId");
		parmMap.remove("businessId");
		parmMap.remove("tenantId");
		
		Authentication.setAuthenticatedUserId(userId);//设置流程发起人
		
		//从模板路径对照表中获取templateId和templatePath对照表
		ActTemplatePath actTemplatePath = new ActTemplatePath();
		actTemplatePath.setTemplateKey(templateId);
		actTemplatePath.setStatus("1");
		List<ActTemplatePath> templateList = actTemplatePathMapper.select(actTemplatePath);
		actTemplatePath = templateList.size()>0?templateList.get(0):null;
		
		String processId = "";
		if(actTemplatePath!=null) {
			//部署流程、启动流程并提交
			repositoryService.createDeployment().addClasspathResource(actTemplatePath.getTemplatePath()).tenantId(tenantId).deploy();
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(templateId, businessId, tenantId);
			Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessId).singleResult();
			task.setAssignee(userId);
			taskService.complete(task.getId(),parmMap);//提交并完成任务
			processId = processInstance.getId();
		}else {
			throw new RuntimeException("该模板ID"+templateId+"在ACT_TEMPLATE_PATH中找不到对应配置路径，请联系管理员查询问题!");
		}
		
		return processId;
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
	
	/**
	 * 退回上个节点
	 *  processId:xxx,			//流程实例ID（必须）
	 *  taskId:xxx,				//任务ID（必须）
	 *  currTaskKeys:xxx,		//驳回发起的当前节点key 为  act_ru_task 中TASK_DEF_KEY_ 字段的值
	 *  targetKey:xxx,  		//目标节点的key  为act_hi_taskinst 中 TASK_DEF_KEY_
	 */
	@Transactional(rollbackFor = Exception.class)
	public String backToLastTask(TaskQuery taskQuery) {

		List<Map<String,Object>> taskInstList = taskQueryDao.getActHiTaskinstByCondition(taskQuery);
		Map taskInstMap = null;
		List currTaskKeys = new ArrayList();//当前任务节点ID列表
		String processDefId = "";//流程定义ID
		String currentTaskDefId = "";//当前流程定义ID
		if(null!=taskInstList&&taskInstList.size()>0) {
			taskInstMap = taskInstList.get(0);
			currTaskKeys.add(taskInstMap.get("taskDefKey"));
			processDefId = MyStringUtil.getString(taskInstMap.get("procDefId"));
			currentTaskDefId = MyStringUtil.getString(taskInstMap.get("taskDefKey"));
			
			BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
			FlowNode flowNode = (FlowNode)bpmnModel.getFlowElement(currentTaskDefId);
			
			SequenceFlow sequenceFlow = flowNode.getIncomingFlows().get(0);
			String targetKey = sequenceFlow.getSourceRef();//获取上一个节点的activityId
			
			runtimeService.createChangeActivityStateBuilder().processInstanceId(taskQuery.getProcessId()).moveActivityIdsToSingleActivityId(currTaskKeys, targetKey).changeState();
			return "success";
		}else {
			throw new RuntimeException("找不到该任务对应的流程信息，请联系管理员处理！");
		}
		
	}
	
	/**
	 * 转办
	 * 
	 */
	public String turnTaskToPerson(Map<String, Object> parmMap) {
		String taskId = MyStringUtil.getString(parmMap.get("taskId"));
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		
		//去除掉必须的参数，剩余的参数为流程必须参数
		parmMap.remove("taskId");
		parmMap.remove("userId");
		
		taskService.setAssignee(taskId, userId);
		taskService.setOwner(taskId, userId);
		
		return "success";
	}
	
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
	@Transactional(rollbackFor = Exception.class)
	public String cancelProcess(TaskQuery taskQuery) {
		String processId = taskQuery.getProcessId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
		if(processInstance!=null) {
			//1、根据process实例查询结束节点出口
			BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
			org.flowable.bpmn.model.Process process = bpmnModel.getProcesses().get(0);
			Collection<EndEvent> endEvents = process.findFlowElementsOfType(EndEvent.class);
			String endId = "";
			for(EndEvent endEvent:endEvents) {
				endId = endEvent.getId();
				break;
			}
			
			//2、执行终止
			List<Execution> executions = runtimeService.createExecutionQuery().parentId(processId).list();
			List<String> executionIds = new ArrayList<String>();
			executions.forEach(execution -> executionIds.add(execution.getId()));
			runtimeService.createChangeActivityStateBuilder().processInstanceId(processId).moveExecutionsToSingleActivityId(executionIds, endId).changeState();
		}else {
			throw new RuntimeException("不存在运行的流程实例，请确认。");
		}
		
		return "success";
	}
}
