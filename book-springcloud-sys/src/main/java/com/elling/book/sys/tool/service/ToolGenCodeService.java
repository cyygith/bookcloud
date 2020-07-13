package com.elling.book.sys.tool.service;
import java.util.List;
import java.util.Map;

import com.elling.book.sys.tool.model.ToolGenCode;
import com.elling.book.sys.common.base.Service;

/**
 *
 * Created by cyy on 2019-12-20 09:50:31.
 */
public interface ToolGenCodeService extends Service<ToolGenCode> {
	
	/**
	 *	根据自定义条件查询
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getByCondition(ToolGenCode toolGenCode);
}
