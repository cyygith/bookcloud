package com.elling.book.sys.common.constant;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class GenConfig {
	private static Logger logger = Logger.getLogger(GenConfig.class);
	private static String path = "template/config.properties";
	private static Properties prop;
	
	static {
		logger.info("if these info output multiple time,that means this file reload more than one times...");
		init();
	}
	/**
	 * 初始化配置信息
	 */
	private static void init() {
		prop = loadProperties();
	}
	/**
	 * 加载配置文件
	 * @return
	 */
	private static Properties loadProperties() {
		Properties prop = null;
		try {
			prop = new Properties();
			InputStream in = GenConfig.class.getClassLoader().getResourceAsStream(path);
			prop.load(in);
		} catch (Exception e) {
			throw new RuntimeException("加载配置文件异常!", e);
		}
		return prop;
	}
	/**
	 * 根据名称获取配置值
	 * @param propname
	 * @return
	 */
	public static String getConf(String propname) {
		String value = "";
		try {
			if(prop == null || prop.isEmpty()) {
				logger.error("config.properties cache lost,now reload");
				prop = loadProperties();
			}
			value = prop.getProperty(propname);
		}catch(Exception e) {
			logger.error("An error accur when get the prop name from config.properties:" + propname);
		}
		return value;
	}
}
