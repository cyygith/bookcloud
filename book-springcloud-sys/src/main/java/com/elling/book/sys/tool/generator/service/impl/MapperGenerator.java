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

public class MapperGenerator extends CodeManager implements ICode{

	@Override
	public String genCode(ToolGenCode toolGenCode) {
		StringBuffer returnMsg = new StringBuffer();
		String templatePath = toolGenCode.getTemplateFilePath();
		String modelName = "";
		String tableName = toolGenCode.getTableName();
		String sign = CodeUtils.getTableNameSplit(tableName)[1];
		
		
		Configuration cfg = getFreemarkerConfiguration(toolGenCode);//模板地址
		String customMapping = "/";
		String modelNameLowerCamel = StringUtils.isNullOrEmpty(modelName) ? CodeUtils.tableNameConvertLowerCamel(tableName) : modelName;
		String modelNameUpperCamel = StringUtils.isNullOrEmpty(modelName) ? CodeUtils.tableNameConvertUpperCamel(tableName) : modelName;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableName", tableName);
		map.put("sign", sign);
		map.put("modelNameLowerCamel", modelNameLowerCamel);
		map.put("modelNameUpperCamel", modelNameUpperCamel);
		map.put("toolGenCode", toolGenCode);
		Map<String, Object> data = getInitData(map);
		try {
			
			// 创建 Mapper 接口
			String javaPath = toolGenCode.getJavaPath();
			String mapperPackage = toolGenCode.getMapperPackage();
			String servicePath = PROJECT_PATH + javaPath +CodeUtils.packageConvertPath(mapperPackage);
			File serviceFile = new File(servicePath + customMapping + modelNameUpperCamel + "Mapper.java");
			// 查看父级目录是否存在, 不存在则创建
			if (!serviceFile.getParentFile().exists()) {
				serviceFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("mapper.ftl").process(data, new FileWriter(serviceFile));
			logger.info(modelNameUpperCamel + "Mapper.java 生成成功!");
			returnMsg.append(modelNameUpperCamel+"Mapper.java 生成成功!");
		} catch (Exception e) {
//			throw new RuntimeException("Mapper.java 生成失败!", e);
			returnMsg.append(modelNameUpperCamel+"Mapper.java 生成失败!");
			e.printStackTrace();
		}
		
		return returnMsg.toString();
	}

	@Override
	public Map<String, Object> getInitData(Map<String, Object> pmap) {
		Map<String, Object> data = new HashMap<>();
		ToolGenCode toolGenCode = (ToolGenCode)pmap.get("toolGenCode");
		String tableName = pmap.get("tableName")+"";
		data.put("date", DateUtil.getNowTime());
		data.put("author", toolGenCode.getAuthor());
		data.put("sign", pmap.get("sign"));
		data.put("modelNameLowerCamel", pmap.get("modelNameLowerCamel"));
		data.put("modelNameUpperCamel", pmap.get("modelNameUpperCamel"));
		data.put("modelNameMidCamel",CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, tableName.toLowerCase()));
		data.put("basePackage", toolGenCode.getBasePackage());
		return data;
	}

}
