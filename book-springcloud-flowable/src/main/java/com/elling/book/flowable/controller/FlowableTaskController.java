package com.elling.book.flowable.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
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
@RequestMapping(value="flowable/task")
public class FlowableTaskController {
	private static Logger logger = LoggerFactory.getLogger(FlowableTaskController.class);
	
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private TaskQueryService taskQueryService;	
	
	
	/**
	 * 根据用户ID和其它条件获取代办列表
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  userId:xxx,				//提交用户ID（必须）
	 *  processDefId:xxx,		//流程定义ID
	 *  xxx:xxx				//其它流程中需要的参数（选择性）
	 *  ....
	 * }
	 */
	@RequestMapping(value="getTodoListByUserIdCondition")
	public Result getTodoListByUserIdCondition(Page page,TaskQuery taskQuery) {
		try {
    		if(MyStringUtil.isNullOrEmpty(taskQuery.getUserId())) {
    			return Result.error("【userId不能为空】");
    		}
//    		if(MyStringUtil.isNullOrEmpty(taskQuery.getProcessDefId())) {
//    			return Result.error("【processDefId不能为空】");
//    		}
    		
    		taskQueryService.getTodoListByUserIdCondition(page,taskQuery);
    		return Result.success(page.getData(),page.getTotal());
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error("查询失败，请联系管理员处理："+e.getMessage());
    		return Result.error("查询失败，请联系管理员处理："+e.getMessage());
    	}
	}
	
	/**
	 * 根据processId查询流程是否结束
	 * @param parmMap
	 * 参数包含如下：
	 * {
	 *  processId:xxx,		//流程定义ID
	 * }
	 */
	@RequestMapping(value="getIsProcessEnd")
	public Result getIsProcessEnd(String processId) {
		boolean endFlag = false;
		try {
    		if(MyStringUtil.isNullOrEmpty(processId)) {
    			return Result.error("【processId不能为空】");
    		}
    		
    		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
    		if(pi == null) {
    			endFlag = true;
    		}
    		
    		return Result.success(endFlag);
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error("查询失败，请联系管理员处理："+e.getMessage());
    		return Result.error("查询失败，请联系管理员处理："+e.getMessage());
    	}
	}
	
	/**
	 * 根据processId生成流程图
	 * 参数包含如下：
	 * {
	 *  processId:xxx,		//流程定义ID
	 * }
	 * @param response
	 * @param processId
	 */
	@RequestMapping(value = "processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
 
        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();
 
        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<String>();
        List<String> flows = new ArrayList<String>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
 
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, false);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    } 
}
