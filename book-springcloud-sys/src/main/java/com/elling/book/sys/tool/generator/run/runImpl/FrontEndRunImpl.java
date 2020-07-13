package com.elling.book.sys.tool.generator.run.runImpl;

import com.elling.book.sys.tool.generator.run.IRunCode;
import com.elling.book.sys.tool.generator.service.impl.ListAndManGenerator;
import com.elling.book.sys.tool.model.ToolGenCode;

/**
 * .前端相关文件生成类
 * @author Administrator
 *
 */
public class FrontEndRunImpl implements IRunCode {
	
	/**
	 * .根据表名生成对应的前端文件xxxList.vue、xxxManager.vue两个文件
	 * @param args
	 */
	@Override
	public String runCode(ToolGenCode toolGenCode) throws Exception{
		String[] tableNames = toolGenCode.getRunTable().split(",");
		StringBuffer sb = new StringBuffer();
		for(String tableName:tableNames) {
			toolGenCode.setTableName(tableName);
			sb.append(new ListAndManGenerator().genCode(toolGenCode)).append("/r/n");//生成xxxList.vue和xxxManager.vue的文件
			System.out.println("表："+tableName+"的执行情况如下："+sb.toString());
		}
		
		return sb.toString();
	}

}
