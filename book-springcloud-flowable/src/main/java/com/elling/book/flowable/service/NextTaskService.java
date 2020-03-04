package com.elling.book.flowable.service;

import org.flowable.bpmn.model.UserTask;

public interface NextTaskService {
	/**
	 * flowable 方式下获取下一个节点的情况
	 * @param node
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public UserTask getNextTaskFlowable(String node,String taskId) throws Exception;
}
