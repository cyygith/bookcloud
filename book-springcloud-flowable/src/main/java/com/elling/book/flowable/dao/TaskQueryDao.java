package com.elling.book.flowable.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.elling.book.flowable.common.entity.Page;
import com.elling.book.flowable.entity.TaskQuery;

@Mapper
public interface TaskQueryDao {
	/**
	 * 【我发起的】流程实例查询
	 * @param page
	 * @param TaskQuery
	 * @return
	 */
	public List getProcessByMyStart(@Param("page") Page page,@Param("query") TaskQuery taskQuery);
	public Integer getProcessByMyStartCount(@Param("query") TaskQuery taskQuery);
	
	/**
	 * 根据用户ID和流程定义ID等条件查询已办列表
	 * @param page
	 * @param TaskQuery
	 * @return
	 */
	public List getDoneListByCondition(@Param("page") Page page,@Param("query") TaskQuery taskQuery);
	public Integer getDoneListByConditionCount(@Param("query") TaskQuery taskQuery);
	
	/**
	 * 根据用户ID获取已领取和未领取的待办任务列表
	 * 这里已领取的表示：具体的assignee已经有分配人了
	 * 未领取的表示：改节点是一个用户组，而当前用户ID属于该组
	 * @param page
	 * @param TaskQuery
	 * @return
	 */
	public List getTodoListByUserIdCondition(@Param("page") Page page,@Param("query") TaskQuery taskQuery);
	public Integer getTodoListByUserIdConditionCount(@Param("query") TaskQuery taskQuery);
	/**
	 * 获取ACT_HI_TASKIINST中的内容
	 * @param taskQuery
	 * @return
	 */
	public List<Map<String,Object>> getActHiTaskinstByCondition(TaskQuery taskQuery);
}
