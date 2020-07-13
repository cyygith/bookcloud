package com.elling.book.sys.tool.generator.service;


import java.util.Map;

import com.elling.book.sys.tool.model.ToolGenCode;

/**
 * 
 * @author cyy
 * @date 20191017
 */
public interface ICode {
	
	/**
	 * 代码生成主要逻辑
	 * @param tableName 表名
	 * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
	 * @param sign 区分字段, 规定如表 sys_dict_manager, 则 dict 即为区分字段
	 */
	String genCode(ToolGenCode toolGenCode);
	/**
	 * 初始化数据
	 */
	Map<String, Object> getInitData(Map<String,Object> map);
}
