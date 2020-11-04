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
 * Service 鍜�  ServiceImpl鐢熸垚
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
		
		//鍒濆鍖栨暟鎹�
		Map<String,Object> pMap = new HashMap<String,Object>();
		pMap.put("modelName", modelName);
		pMap.put("sign", sign);
		pMap.put("modelNameUpperCamel", modelNameUpperCamel);
		pMap.put("toolGenCode", toolGenCode);
		Map<String, Object> data = getInitData(pMap);
		
		try {
			String javaPath = toolGenCode.getJavaPath();
			
			// 鍒涘缓 Service 鎺ュ彛
			String servicePath = PROJECT_PATH + javaPath +CodeUtils.packageConvertPath(toolGenCode.getServicePackage());
			File serviceFile = new File(servicePath + customMapping + modelNameUpperCamel + "Service.java");
			// 鏌ョ湅鐖剁骇鐩綍鏄惁瀛樺湪, 涓嶅瓨鍦ㄥ垯鍒涘缓
			if (!serviceFile.getParentFile().exists()) {
				serviceFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("service.ftl").process(data, new FileWriter(serviceFile));
			logger.info(modelNameUpperCamel + "Service.java 鐢熸垚鎴愬姛!");
			returnMsg.append(modelNameUpperCamel + "Service.java 鐢熸垚鎴愬姛!");
			
			// 鍒涘缓 Service 鎺ュ彛鐨勫疄鐜扮被
			String serviceImplPath = PROJECT_PATH+javaPath+CodeUtils.packageConvertPath(toolGenCode.getServiceImplPackage());
			File serviceImplFile = new File(serviceImplPath + customMapping + modelNameUpperCamel + "ServiceImpl.java");
			// 鏌ョ湅鐖剁骇鐩綍鏄惁瀛樺湪, 涓嶅瓨鍦ㄥ垯鍒涘缓
			if (!serviceImplFile.getParentFile().exists()) {
				serviceImplFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("service-impl.ftl").process(data, new FileWriter(serviceImplFile));
			logger.info(modelNameUpperCamel + "ServiceImpl.java 鐢熸垚鎴愬姛!");
			returnMsg.append(" "+modelNameUpperCamel + "ServiceImpl.java 鐢熸垚鎴愬姛");
		} catch (Exception e) {
			returnMsg.append("Service 鐢熸垚澶辫触");
			logger.error("Service 鐢熸垚澶辫触");
			e.printStackTrace();
//			throw new RuntimeException("Service 鐢熸垚澶辫触!", e);
		}
		
		return returnMsg.toString();
	}

	@Override
	public Map<String, Object> getInitData(Map<String,Object> map) {
		Map<String, Object> data = new HashMap<String, Object>();
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
