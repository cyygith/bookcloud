package com.elling.book.sys.tool.generator.run.runImpl;

import com.elling.book.sys.tool.generator.run.IRunCode;
import com.elling.book.sys.tool.generator.service.impl.ControllerGenerator;
import com.elling.book.sys.tool.generator.service.impl.MapperGenerator;
import com.elling.book.sys.tool.generator.service.impl.MapperXmlGenerator;
import com.elling.book.sys.tool.generator.service.impl.ModelAndMapperGenerator;
import com.elling.book.sys.tool.generator.service.impl.ServiceGenerator;
import com.elling.book.sys.tool.model.ToolGenCode;

/**
 * .后端相关文件生成类
 * @author Administrator
 *
 */
public class BackEndRunImpl implements IRunCode {
	/**
	 * .根据表名生成对应的后端文件Service、ServiceImpl、Controller、Mapper、entity、xml等六个文件
	 * @param args
	 */
	@Override
	public String runCode(ToolGenCode toolGenCode) {
		String[] tableNames = toolGenCode.getRunTable().split(",");
		StringBuffer sb = new StringBuffer();
		for(String tableName:tableNames) {
			toolGenCode.setTableName(tableName);
			sb.append(new ServiceGenerator().genCode(toolGenCode)).append("<br/>");//生成service、serviceImpl页面相关
			sb.append(new ControllerGenerator().genCode(toolGenCode)).append("<br/>");//生成controller页面相关
			sb.append(new MapperGenerator().genCode(toolGenCode)).append("<br/>");//生成Mapper文件
			sb.append(new MapperXmlGenerator().genCode(toolGenCode)).append("<br/>");//生成Mapper.xml文件
			sb.append(new ModelAndMapperGenerator().genCode(toolGenCode)).append("<br/>");//生成model页面相关
			
			System.out.println("表："+tableName+"的执行情况如下："+sb.toString());
		}
		return sb.toString();
	}

}
