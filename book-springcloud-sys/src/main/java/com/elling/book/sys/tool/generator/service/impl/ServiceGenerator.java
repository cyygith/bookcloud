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

import freemarker.template.Configuration;

/**
 * Service 和  ServiceImpl生成
 * @author cyy
 *
 */
public class ServiceGenerator extends CodeManager implements ICode{

	@Override
	public String genCode(ToolGenCode toolGenCode) {
		StringBuffer returnMsg = new StringBuffer();
		
		String modelName = "";
		String tableName = toolGenCode.getTableName();
		String sign = CodeUtils.getTableNameSplit(tableName)[1];
		
		
		Configuration cfg = getFreemarkerConfiguration(toolGenCode);
		String customMapping = "/";
		String modelNameUpperCamel = StringUtils.isNullOrEmpty(modelName) ? CodeUtils.tableNameConvertUpperCamel(tableName) : modelName;
		
		//初始化数据
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("modelName", modelName);
		pMap.put("sign", sign);
		pMap.put("modelNameUpperCamel", modelNameUpperCamel);
		pMap.put("toolGenCode", toolGenCode);
		Map<String, Object> data = getInitData(pMap);
		
		try {
			String javaPath = toolGenCode.getJavaPath();
			
			// 创建 Service 接口
			String servicePath = PROJECT_PATH + javaPath +CodeUtils.packageConvertPath(toolGenCode.getServicePackage());
			File serviceFile = new File(servicePath + customMapping + modelNameUpperCamel + "Service.java");
			// 查看父级目录是否存在, 不存在则创建
			if (!serviceFile.getParentFile().exists()) {
				serviceFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("service.ftl").process(data, new FileWriter(serviceFile));
			logger.info(modelNameUpperCamel + "Service.java 生成成功!");
			returnMsg.append(modelNameUpperCamel + "Service.java 生成成功!");
			
			// 创建 Service 接口的实现类
			String serviceImplPath = PROJECT_PATH+javaPath+CodeUtils.packageConvertPath(toolGenCode.getServiceImplPackage());
			File serviceImplFile = new File(serviceImplPath + customMapping + modelNameUpperCamel + "ServiceImpl.java");
			// 查看父级目录是否存在, 不存在则创建
			if (!serviceImplFile.getParentFile().exists()) {
				serviceImplFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("service-impl.ftl").process(data, new FileWriter(serviceImplFile));
			logger.info(modelNameUpperCamel + "ServiceImpl.java 生成成功!");
			returnMsg.append(" "+modelNameUpperCamel + "ServiceImpl.java 生成成功");
		} catch (Exception e) {
			returnMsg.append("Service 生成失败");
			logger.error("Service 生成失败");
			e.printStackTrace();
//			throw new RuntimeException("Service 生成失败!", e);
		}
		
		return returnMsg.toString();
	}

	@Override
	public Map<String, Object> getInitData(Map<String,Object> map) {
		Map<String, Object> data = new HashMap<>();
		ToolGenCode toolGenCode = (ToolGenCode)map.get("toolGenCode");
		data.put("date", DateUtil.getNowTime());
		data.put("author", toolGenCode.getAuthor());
		data.put("sign", map.get("sign"));
		data.put("modelNameUpperCamel", map.get("modelNameUpperCamel"));
		data.put("modelNameLowerCamel", StringUtils.toLowerCaseFirstOne(map.get("modelNameUpperCamel")+""));
		data.put("basePackage", toolGenCode.getBasePackage());
		
		return data;
	}

}
