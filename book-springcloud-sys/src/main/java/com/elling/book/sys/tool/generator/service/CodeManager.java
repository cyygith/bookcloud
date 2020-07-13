package com.elling.book.sys.tool.generator.service;


import java.io.File;
import java.io.IOException;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elling.book.sys.common.constant.GenConfig;
import com.elling.book.sys.tool.model.ToolGenCode;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * 生成代码基类
 * @author cyy
 * @date 20191017
 */
public class CodeManager {
	
protected static final Logger logger = LoggerFactory.getLogger(CodeManager.class);
	
	private static Configuration configuration = null;
	// 项目在硬盘上的基础路径
	//protected static final String PROJECT_PATH = System.getProperty("user.dir");
	protected static String  PROJECT_PATH;//System.getProperty("user.dir");
	protected static String TEMPLATE_PATH;//模板地址，判断传入的模板文件夹是否与缓存的模板文件夹是否相同，不同则重新初始化
	/**
	 * 获取 Freemarker 模板环境配置
	 * path : 模板的地址，配置在config.properties中也可以，也可以自己输入
	 * @return
	 */
	public Configuration getFreemarkerConfiguration(ToolGenCode toolGenCode) {
		PROJECT_PATH = toolGenCode.getProjectPath();
		if (configuration == null||(!TEMPLATE_PATH.equals(toolGenCode.getTemplateFilePath()))) {
			configuration = initFreemarkerConfiguration(toolGenCode);
		}
		return configuration;
	}
	
	/**
	 * Freemarker 模板环境配置
	 * @return
	 * @throws IOException
	 */
	private Configuration initFreemarkerConfiguration(ToolGenCode toolGenCode) {
		Configuration cfg = null;
		try {
			String path = toolGenCode.getTemplateFilePath();
			TEMPLATE_PATH = path;
			cfg = new Configuration(Configuration.VERSION_2_3_23);
//			cfg.setDirectoryForTemplateLoading(new File(PROJECT_PATH+path));
			cfg.setDirectoryForTemplateLoading(new File(path));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		} catch (IOException e) {
			throw new RuntimeException("Freemarker 模板环境初始化异常!", e);
		}
		return cfg;
	}
	/**
	 * Mybatis 代码自动生成基本配置
	 * @return
	 */
	public Context initMybatisGeneratorContext(ToolGenCode toolGenCode) {
		Context context = new Context(ModelType.FLAT);
		context.setId("apple");
		context.setTargetRuntime("MyBatis3Simple");
		context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS, "true");
        
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(GenConfig.getConf("jdbc.url"));
        jdbcConnectionConfiguration.setUserId(GenConfig.getConf("jdbc.username)"));
        jdbcConnectionConfiguration.setPassword(GenConfig.getConf("jdbc.password"));
        jdbcConnectionConfiguration.setDriverClass(GenConfig.getConf("jdbc.driver.class.name"));
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        
//        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
//        sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + toolGenCode.getResourcesPath());
//        sqlMapGeneratorConfiguration.setTargetPackage("mapper." + toolGenCode.getBaseModel());
//        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        
        // 增加 mapper 插件
//        addMapperPlugin(context);
        
		return context;
	}
	/**
	 * 增加 Mapper 插件
	 * @param context
	 */
	private void addMapperPlugin(Context context) {
		PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", GenConfig.getConf("mapper.interface.reference"));
        context.addPluginConfiguration(pluginConfiguration);
	}
	
}
