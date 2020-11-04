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
	
	
	@Transactional(rollbackFor = Exception.class)
	public String startProcess(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String templateId = MyStringUtil.getString(parmMap.get("templateId"));
		String businessId = MyStringUtil.getString(parmMap.get("businessId"));
		
		Authentication.setAuthenticatedUserId(userId);//设置流程发起人
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(templateId, businessId);
		return processInstance.getId();
	}
	
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
	
	@Transactional(rollbackFor = Exception.class)
	public String claimAndSubmitToNextTask(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String taskId = MyStringUtil.getString(parmMap.get("taskId"));
		
		//去除掉必须的参数，剩余的参数为流程必须参数
		parmMap.remove("userId");
		parmMap.remove("taskId");
		
		//如果存在comment的情况下，添加comment
		String commentMsg = MyStringUtil.getString(parmMap.get("commentMsg"));
		String msgType = MyStringUtil.getString(parmMap.get("msgType"));
		if(!MyStringUtil.isNullOrEmpty(commentMsg)) {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String processId = task.getProcessInstanceId();
			if(MyStringUtil.isNullOrEmpty(msgType)) {
				taskService.addComment(taskId, processId, commentMsg);
			}else {
				taskService.addComment(taskId, processId, msgType,commentMsg);
			}
		}
		
		
		taskService.claim(taskId, userId);
		taskService.complete(taskId,parmMap);
		return taskId;
	}
	
	@Override
	public String submitToNextTaskToPerson(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String taskId = MyStringUtil.getString(parmMap.get("taskId"));
		String businessId = MyStringUtil.getString(parmMap.get("businessId"));
		String nextUserId = MyStringUtil.getString(parmMap.get("nextUserId"));
		
		//去除掉必须的参数，剩余的参数为流程必须参数
		parmMap.remove("businessId");
		parmMap.remove("nextUserId");
		parmMap.remove("userId");
		parmMap.remove("taskId");
		
		//如果存在comment的情况下，添加comment
		String commentMsg = MyStringUtil.getString(parmMap.get("commentMsg"));
		String msgType = MyStringUtil.getString(parmMap.get("msgType"));
		if(!MyStringUtil.isNullOrEmpty(commentMsg)) {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String processId = task.getProcessInstanceId();
			if(MyStringUtil.isNullOrEmpty(msgType)) {
				taskService.addComment(taskId, processId, commentMsg);
			}else {
				taskService.addComment(taskId, processId, msgType,commentMsg);
			}
		}
		
		
		taskService.setAssignee(taskId, userId);
		taskService.complete(taskId,parmMap);
		
		//获取下一步任务的taskId，并认领，否则下一个节点推送不到具体的人那里
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setBusinessId(businessId);
		List<Map<String,Object>> list = taskQueryDao.getCurrentTaskByBusinessId(taskQuery);
		String nextTaskId = "";
		if(null !=list&&list.size() == 1) {
			Map map = list.get(0);
			nextTaskId = MyStringUtil.getString(map.get("taskId"));
			System.out.println("下一个节点的taskId为"+nextTaskId);
			taskService.claim(nextTaskId, nextUserId);
		}
		
		
		return taskId;
	}
	@Transactional(rollbackFor = Exception.class)
	public String claimAndSubmitToNextTaskToPerson(Map<String, Object> parmMap) {
		String userId = MyStringUtil.getString(parmMap.get("userId"));
		String taskId = MyStringUtil.getString(parmMap.get("taskId"));
		String businessId = MyStringUtil.getString(parmMap.get("businessId"));
		String nextUserId = MyStringUtil.getString(parmMap.get("nextUserId"));
		
		//去除掉必须的参数，剩余的参数为流程必须参数
		parmMap.remove("businessId");
		parmMap.remove("nextUserId");
		parmMap.remove("userId");
		parmMap.remove("taskId");
		
		//如果存在comment的情况下，添加comment
		String commentMsg = MyStringUtil.getString(parmMap.get("commentMsg"));
		String msgType = MyStringUtil.getString(parmMap.get("msgType"));
		if(!MyStringUtil.isNullOrEmpty(commentMsg)) {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String processId = task.getProcessInstanceId();
			if(MyStringUtil.isNullOrEmpty(msgType)) {
				taskService.addComment(taskId, processId, commentMsg);
			}else {
				taskService.addComment(taskId, processId, msgType,commentMsg);
			}
		}
		
		
		taskService.claim(taskId, userId);
		taskService.complete(taskId,parmMap);
		
		//获取下一步任务的taskId，并认领，否则下一个节点推送不到具体的人那里
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setBusinessId(businessId);
		List<Map<String,Object>> list = taskQueryDao.getCurrentTaskByBusinessId(taskQuery);
		String nextTaskId = "";
		if(null !=list&&list.size() == 1) {
			Map map = list.get(0);
			nextTaskId = MyStringUtil.getString(map.get("taskId"));
			System.out.println("下一个节点的taskId为"+nextTaskId);
			taskService.claim(nextTaskId, nextUserId);
		}
		
		
		return nextTaskId;
	}
	
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
			String userId = taskQuery.getUserId();
			String taskId = MyStringUtil.getString(taskInstMap.get("taskId"));
			
			
			BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
			FlowNode flowNode = (FlowNode)bpmnModel.getFlowElement(currentTaskDefId);
			
			SequenceFlow sequenceFlow = flowNode.getIncomingFlows().get(0);
			String targetKey = sequenceFlow.getSourceRef();//获取上一个节点的activityId
			
			taskService.setAssignee(taskId, userId);//设置当前节点执行人
			runtimeService.createChangeActivityStateBuilder().processInstanceId(taskQuery.getProcessId()).moveActivityIdsToSingleActivityId(currTaskKeys, targetKey).changeState();
			
			//更新跳转后节点执行人（目前只适用于直线流程，存在子流程的情况下不适用）			
			taskQuery.setTaskId(null);
			taskInstList = taskQueryDao.getActHiTaskinstByCondition(taskQuery);
			if(null!=taskInstList&&taskInstList.size()>0) {
				taskInstMap = taskInstList.get(0);
				String currTaskId = MyStringUtil.getString(taskInstMap.get("taskId"));
				
				//更新跳转节点后执行人，如果不设置Assign则退回到上个角色，则不能具体到对应人（目前只适用于直线流程，存在子流程的情况下不适用）
				taskQuery.setTaskDefKey(targetKey);
				List<Map<String,Object>> tempTaskList = taskQueryDao.getActHiTaskinstByCondition(taskQuery);
				if(null!=tempTaskList&&tempTaskList.size()>0) {
					Map tMap = tempTaskList.get(1);
					String lastUserId = MyStringUtil.getString(tMap.get("assignee"));
					taskService.setAssignee(currTaskId, lastUserId);//设置当前节点执行人
				}
			}
			
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

	
	/**
	 *     返回首个节点
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  processId:xxx,			//流程实例ID（必须）
	 *  taskId:xxx,			//当前任务ID（必须）
	 *  currTaskKeys:xxx	//驳回发起的当前节点key 为 act_ru_task中的TASK_DEF_KEY_字段的值
	 *  targetKey:xxx		//目标节点的key  为act_hi_taskinst中的TASK_DEF_KEY_字段的值
	 * }
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String backToFirstTask(TaskQuery taskQuery) {
		List<Map<String,Object>> taskInstList = taskQueryDao.getActHiTaskinstByCondition(taskQuery);//获取当前的任务
		
		List<Map<String,Object>> allTaskInstList = taskQueryDao.getActHiTaskinstListByProcessId(taskQuery);//获取所有的已办任务列表
		
		Map taskInstMap = null;
		List currTaskKeys = new ArrayList();//当前任务节点ID列表
		String processDefId = "";//流程定义ID
		String currentTaskDefId = "";//当前任务定义ID
		if(null!=taskInstList&&taskInstList.size()>0) {
			taskInstMap = taskInstList.get(0);
			currTaskKeys.add(taskInstMap.get("taskDefKey"));
			processDefId = MyStringUtil.getString(taskInstMap.get("procDefId"));
			currentTaskDefId = MyStringUtil.getString(taskInstMap.get("taskDefKey"));
			
			Map tempMap = null;
			if(null!=allTaskInstList&&allTaskInstList.size()>0) {
				tempMap = allTaskInstList.get(0);//根据开始时间排序，取第一个则为首节点信息
			}
			
			String targetKey = MyStringUtil.getString(tempMap.get("taskDefKey"));
			runtimeService.createChangeActivityStateBuilder().processInstanceId(taskQuery.getProcessId()).moveActivityIdsToSingleActivityId(currTaskKeys, targetKey).changeState();
			
			return "success";
			
		}else {
			throw new RuntimeException("找不到该任务对应的流程信息，请联系管理员查询处理问题");
		}
	}
}



















