package com.elling.book.flowable.service.impl;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.ExclusiveGateway;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.de.odysseus.el.ExpressionFactoryImpl;
import org.flowable.common.engine.impl.de.odysseus.el.util.SimpleContext;
import org.flowable.common.engine.impl.javax.el.ExpressionFactory;
import org.flowable.common.engine.impl.javax.el.ValueExpression;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.runtime.Execution;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elling.book.flowable.service.NextTaskService;

@Service("nextTaskService")
public class NextTaskServiceImpl implements NextTaskService{
	@Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
	
    /**
     * flowable 方式下获取下一个节点的情况
     * @param processInstanceId
     * @throws Exception
     */
    public UserTask getNextTaskFlowable(String node,String taskId) throws Exception{
    	
    	UserTask userTask = null;
    	
    	Task task = taskService.createTaskQuery().taskId(taskId).singleResult();//获取当前作业
        
    	ExecutionEntity ee = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        
        String crruentActivityId = ee.getActivityId();// 当前审批节点
        
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        
        FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(crruentActivityId);
        
        
        // 输出连线
        List<SequenceFlow> outFlows = flowNode.getOutgoingFlows();
        for (SequenceFlow sequenceFlow : outFlows) {
            //当前审批节点
            if ("now".equals(node)) {
                FlowElement sourceFlowElement = sequenceFlow.getSourceFlowElement();
                System.out.println("当前节点: id=" + sourceFlowElement.getId() + ",name=" + sourceFlowElement.getName());
            } else if ("next".equals(node)) {
                // 下一个审批节点
                FlowElement targetFlow = sequenceFlow.getTargetFlowElement();
                if (targetFlow instanceof UserTask) {
                    System.out.println("下一节点: id=" + targetFlow.getId() + ",name=" + targetFlow.getName());
                    userTask = (UserTask)targetFlow;
                }
                // 如果下个审批节点为结束节点
                if (targetFlow instanceof EndEvent) {
                    System.out.println("下一节点为结束节点：id=" + targetFlow.getId() + ",name=" + targetFlow.getName());
                }else if (targetFlow instanceof ExclusiveGateway) {
                	String elString = "";
                	userTask = getNextTask(targetFlow,task.getProcessInstanceId(),task.getId(),elString);
                }
            }


        }
        return userTask;
    }
    
    /**
     * .获取下一个节点
     * @return
     */
    public UserTask getNextTask(FlowElement targetFlow,String processInstanceId,String taskId,String elString) {
    	String obj = "";
    	UserTask task = null;
    	if (targetFlow instanceof UserTask) {
            System.out.println("下一节点: id=" + targetFlow.getId() + ",name=" + targetFlow.getName());
            task = (UserTask)targetFlow;
        }else if(targetFlow instanceof ExclusiveGateway) {
        	List<SequenceFlow> targetFlows = ((ExclusiveGateway) targetFlow).getOutgoingFlows();
        	elString = getGatewayCondition(targetFlow.getId(), processInstanceId);
            //如果排他网关只有一条
            if(targetFlows.size()==1) {
            	task = getNextTask(targetFlows.get(0).getTargetFlowElement(),processInstanceId,taskId,elString);
            }else{
            	for(SequenceFlow sflow: targetFlows) {
            		obj = sflow.getConditionExpression();
            		// 判断el表达式是否成立
                    if (isCondition(targetFlow.getId(), obj.trim(), elString)) {
                    	task = getNextTask(sflow.getTargetFlowElement(),processInstanceId,taskId,elString);
                    }
            	}
            }
        }else if(targetFlow instanceof ServiceTask) {
        	List<SequenceFlow> targetFlows = ((ServiceTask) targetFlow).getOutgoingFlows();
        	return getNextTask(targetFlows.get(0).getTargetFlowElement(),processInstanceId,taskId,elString);
        }else if(targetFlow instanceof EndEvent) {
        	//如果是结束节点
        }else {
        	//其它的情况都返回为空,暂时不做讨论
        }
    	
    	return task;
    }
    
    /** 
     * .查询流程启动时设置排他网关判断条件信息  
     * @param String gatewayId          排他网关Id信息, 流程启动时设置网关路线判断条件key为网关Id信息  
     * @param String processInstanceId  流程实例Id信息  
     * @return 
     */  
    public String getGatewayCondition(String gatewayId, String processInstanceId) {  
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();
        Object object= runtimeService.getVariable(execution.getId(), gatewayId);
        return object==null? "":object.toString();  
    }  

    /** 
     * .根据key和value判断el表达式是否通过信息  
     * @param String key    el表达式key信息  
     * @param String el     el表达式信息  
     * @param String value  el表达式传入值信息  
     * @return 
     */  
    public boolean isCondition(String key, String el, String value) {  
        ExpressionFactory factory = new ExpressionFactoryImpl();    
        SimpleContext context = new SimpleContext();    
        context.setVariable(key, factory.createValueExpression(value, String.class));    
        ValueExpression e = factory.createValueExpression(context, el, boolean.class);    
        return (Boolean) e.getValue(context);  
    }  
}