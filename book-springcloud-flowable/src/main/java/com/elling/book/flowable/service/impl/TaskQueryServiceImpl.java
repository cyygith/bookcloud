package com.elling.book.flowable.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elling.book.flowable.common.entity.Page;
import com.elling.book.flowable.dao.TaskQueryDao;
import com.elling.book.flowable.entity.TaskQuery;
import com.elling.book.flowable.service.TaskQueryService;

@Service(value="taskQueryService")
public class TaskQueryServiceImpl implements TaskQueryService{
	
	@Autowired
	private TaskQueryDao taskQueryDao;
	
	public Page getProcessByMyStart(Page page, TaskQuery taskQuery) {
		List list = taskQueryDao.getProcessByMyStart(page, taskQuery);
		Integer count = taskQueryDao.getProcessByMyStartCount(taskQuery);
		page.setTotal(count);
		page.setData(list);
		return page;
	}

	public Page getDoneListByCondition(Page page, TaskQuery taskQuery) {
		List list = taskQueryDao.getDoneListByCondition(page, taskQuery);
		Integer count = taskQueryDao.getDoneListByConditionCount(taskQuery);
		page.setTotal(count);
		page.setData(list);
		return page;
	}

	public Page getTodoListByUserIdCondition(Page page, TaskQuery taskQuery) {
		List list = taskQueryDao.getTodoListByUserIdCondition(page, taskQuery);
		Integer count = taskQueryDao.getTodoListByUserIdConditionCount(taskQuery);
		page.setTotal(count);
		page.setData(list);
		return page;
	}

}
