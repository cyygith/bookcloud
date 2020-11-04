package com.elling.book.sys.tool.generator.service.impl;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.utils.CodeUtils;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.common.utils.StringUtils;
import com.elling.book.sys.tool.database.QueryMysqlTable;
import com.elling.book.sys.tool.generator.service.CodeManager;
import com.elling.book.sys.tool.generator.service.ICode;
import com.elling.book.sys.tool.model.TableColEntity;
import com.elling.book.sys.tool.model.ToolGenCode;
import com.google.common.base.CaseFormat;

import freemarker.template.Configuration;

public class ListAndManGenerator extends CodeManager implements ICode{

	@Override
	public String genCode(ToolGenCode toolGenCode) {
		StringBuffer returnMsg = new StringBuffer();
		String modelName = "";
		String tableName = toolGenCode.getTableName();
		String sign = CodeUtils.getTableNameSplit(tableName)[1];
		
		Configuration cfg = getFreemarkerConfiguration(toolGenCode);
		String customMapping = "/" + sign + "/";
		String modelNameLowerCamel = StringUtils.isNullOrEmpty(modelName) ? CodeUtils.tableNameConvertLowerCamel(tableName) : modelName;
		String PAGE_PATH = toolGenCode.getPagePath();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableName", tableName);
		map.put("sign", sign);
		map.put("modelNameLowerCamel", modelNameLowerCamel);
		map.put("toolGenCode", toolGenCode);
		Map<String, Object> data = getInitData(map);
		try {
			// 鍒涘缓 list 椤甸潰
			File listFile = new File(PROJECT_PATH + PAGE_PATH + customMapping + modelNameLowerCamel + "List.vue");
			// 鏌ョ湅鐖剁骇鐩綍鏄惁瀛樺湪, 涓嶅瓨鍦ㄥ垯鍒涘缓
			if (!listFile.getParentFile().exists()) {
				listFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("List.ftl").process(data, new FileWriter(listFile));
			logger.info (modelNameLowerCamel+ "List.vue 鐢熸垚鎴愬姛!");
			
			// 鍒涘缓 Manager 椤甸潰
			File ManagerFile = new File(PROJECT_PATH + PAGE_PATH + customMapping + modelNameLowerCamel + "Manager.vue");
			// 鏌ョ湅鐖剁骇鐩綍鏄惁瀛樺湪, 涓嶅瓨鍦ㄥ垯鍒涘缓
			if (!ManagerFile.getParentFile().exists()) {
				ManagerFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("manager.ftl").process(data, new FileWriter(ManagerFile));
			logger.info (modelNameLowerCamel+ "manager.vue 鐢熸垚鎴愬姛!");
			returnMsg.append(modelNameLowerCamel+ "manager.vue 鐢熸垚鎴愬姛!");
		} catch (Exception e) {
//			throw new RuntimeException("vue 鐢熸垚澶辫触!", e);
			returnMsg.append(modelNameLowerCamel+"manager.vue 鐢熸垚澶辫触!");
			e.printStackTrace();
		}
		
		return returnMsg.toString();
	}

	@Override
	public Map<String, Object> getInitData(Map<String, Object> pmap) {
		Map<String, Object> data = new HashMap<String, Object>();
		ToolGenCode toolGenCode = (ToolGenCode)pmap.get("toolGenCode");
		String tableName = pmap.get("tableName")+"";
		String schema = toolGenCode.getJdbcSchema();;
		data.put("sign", pmap.get("sign"));
		data.put("modelNameLowerCamel", pmap.get("modelNameLowerCamel"));
		data.put("modelNameMidCamel",CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, tableName.toLowerCase()));
		data.put("base_model",toolGenCode.getBaseModel());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableName", tableName);
		map.put("url", toolGenCode.getJdbcUrl());
		map.put("username", toolGenCode.getJdbcUsername());
		map.put("passwd", toolGenCode.getJdbcPassword());
		map.put("schema", schema);
		
		QueryMysqlTable query = new QueryMysqlTable();
		String sql = "select t.COLUMN_NAME,t.COLUMN_COMMENT,t.COLUMN_KEY,T.DATA_TYPE from information_schema.`COLUMNS` t where t.TABLE_NAME = '"+tableName+"' AND t.TABLE_SCHEMA='"+schema+"'";    //瑕佹墽琛岀殑SQL
		String[] arr = new String[] {null};
		List<Map<String,Object>> list = query.getBySql(sql, null);
		List<TableColEntity> colList = new ArrayList<TableColEntity>();
		TableColEntity col = null;
		for(int i=0,len=list.size();i<len;i++) {
			col = new TableColEntity();
			Map tMap = list.get(i);
			col.setColunm(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tMap.get("COLUMN_NAME")+""));
			col.setComment(tMap.get("COLUMN_COMMENT")+"");
			col.setColunmKey(tMap.get("COLUMN_KEY")+"");
			col.setDataType(tMap.get("DATA_TYPE")+"");
			if(StringUtil.getString(tMap.get("COLUMN_KEY")).equals("PRI")) {
				data.put("primaryKey", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tMap.get("COLUMN_NAME")+""));
			}
			/**
        	 * .璁剧疆澶勭悊绫诲瀷,濡傛灉鍖呭惈time鐨勮瘽鍒欐樉绀簍ime锛�
        	 * .濡傛灉鏄痵tatus,鍒欐樉绀簊elect绫诲瀷锛屼互鍚庡彲浠ヨ鑼冧竴涓嬶紝涓嬫媺妗嗗浣曡缃紝杩欓噷鎵嬪姩鍐�
        	 * .濡傛灉鏄�...鍙互璁剧疆涓簉adio绫诲瀷
        	 * .鍏朵粬鐨勯粯璁や负text绫诲瀷
        	 */
			String colunm = tMap.get("COLUMN_NAME")+"";
        	if(colunm.toLowerCase().contains("time")) {
        		col.setDealType("time");
        	}else if(colunm.toLowerCase().contains("status")) {
        		col.setDealType("select");
        	}else {
        		col.setDealType("text");
        	}
			
			colList.add(col);
		}
		
		List<TableColEntity> list1 = new ArrayList<TableColEntity>();
		colList.forEach((item->{
			if(!item.getColunmKey().equals("PRI")) {
				list1.add(item);
			}
		}));
		
		data.put("colsEntityNoKey",list1);
		data.put("colsEntity",colList);
		
		return data;
	}

}
