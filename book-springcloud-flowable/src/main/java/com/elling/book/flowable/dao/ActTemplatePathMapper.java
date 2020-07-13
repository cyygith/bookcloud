package com.elling.book.flowable.dao;

import java.util.List;
import java.util.Map;

import com.elling.book.flowable.common.base.MyMapper;
import com.elling.book.flowable.entity.ActTemplatePath;

/**
 *
 * Created by cyy on 2020-05-26 10:52:04.
 */
public interface ActTemplatePathMapper extends MyMapper<ActTemplatePath> {
	/**
	 *	根据自定义条件查询
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getByCondition(ActTemplatePath actTemplatePath);
}