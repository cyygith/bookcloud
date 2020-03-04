package com.elling.book.flowable.service;



import com.elling.book.flowable.common.entity.Page;
import com.elling.book.flowable.entity.TaskQuery;

public interface TaskQueryService {
	/**
	 * 【我发起的】流程实例查询
	 * @param page
	 * @param taskQuery
	 * @return
	 */
	public Page getProcessByMyStart(Page page,TaskQuery taskQuery);
	
	
	/**
	 * 根据用户ID和流程定义ID等条件查询已办列表
	 * @param page
	 * @param taskQuery
	 * @return
	 */
	public Page getDoneListByCondition(Page page,TaskQuery taskQuery);
	
	/**
	 * 根据用户ID获取已领取和未领取的待办任务列表
	 * 这里已领取的表示：具体的assignee已经有分配人了
	 * 未领取的表示：改节点是一个用户组，而当前用户ID属于该组
	 * @param page
	 * @param taskQuery
	 * @return
	 */
	public Page getTodoListByUserIdCondition(Page page,TaskQuery taskQuery);
	
	
	
	
}
