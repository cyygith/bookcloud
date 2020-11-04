package com.elling.book.sys.tool.generator.service.impl;


import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import com.elling.book.sys.common.utils.CodeUtils;
import com.elling.book.sys.common.utils.DateUtil;
import com.elling.book.sys.common.utils.StringUtils;
import com.elling.book.sys.tool.generator.service.CodeManager;
import com.elling.book.sys.tool.generator.service.ICode;
import com.elling.book.sys.tool.model.ToolGenCode;
import com.google.common.base.CaseFormat;

import freemarker.template.Configuration;

public class ControllerGenerator extends CodeManager implements ICode {

	@Override
	public String genCode(ToolGenCode toolGenCode) {
		StringBuffer returnMsg = new StringBuffer();
		String modelName = "";
		String tableName = toolGenCode.getTableName();
		String sign = CodeUtils.getTableNameSplit(tableName)[1];
		String javaPath = toolGenCode.getJavaPath();
		String contrPath = toolGenCode.getControllerPackage();
		String customMapping = "/";
		
		
		Configuration cfg = getFreemarkerConfiguration(toolGenCode);
		String modelNameUpperCamel = StringUtils.isNullOrEmpty(modelName) ? CodeUtils.tableNameConvertUpperCamel(tableName) : modelName;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableName", tableName);
		map.put("modelName", modelName);
		map.put("sign", sign);
		map.put("modelNameUpperCamel", modelNameUpperCamel);
		map.put("basePackage",toolGenCode.getBasePackage());
		map.put("author",toolGenCode.getAuthor());
		
		Map<String, Object> data = getInitData(map); 
		try {
			String controllerPath = PROJECT_PATH + javaPath +CodeUtils.packageConvertPath(contrPath);
			File controllerFile = new File(controllerPath + customMapping + modelNameUpperCamel + "Controller.java");
	        if (!controllerFile.getParentFile().exists()) {
	        	controllerFile.getParentFile().mkdirs();
	        }
	        cfg.getTemplate("controller.ftl").process(data, new FileWriter(controllerFile));
			logger.info(modelNameUpperCamel + "Controller.java 生成成功!");
			returnMsg.append(modelNameUpperCamel + "Controller.java 生成成功!");
		} catch (Exception e) {
//			throw new RuntimeException("Controller 生成失败!", e);
			returnMsg.append(modelNameUpperCamel+"Controller 生成失败!");
			e.printStackTrace();
		}
		
		return returnMsg.toString();
	}

	@Override
	public Map<String, Object> getInitData(Map<String, Object> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("date", DateUtil.getNowTime());
        data.put("author", map.get("author"));
        data.put("sign", map.get("sign"));
        data.put("baseRequestMapping", StringUtils.toLowerCaseFirstOne(map.get("modelNameUpperCamel")+""));
        data.put("modelNameUpperCamel", map.get("modelNameUpperCamel"));
        data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, map.get("modelNameUpperCamel")+""));
        data.put("basePackage", map.get("basePackage"));
		
		return data;
	}

}
