package com.elling.book.sys.common.utils;


import java.io.File;

import com.google.common.base.CaseFormat;

public class CodeUtils {
	/**
	 * 下划线转成驼峰, 首字符为小写
	 * eg: sys_user_demo ==> sysUserDemo
	 * @param tableName 表名, eg: sys_user_demo
	 * @return
	 */
	public static String tableNameConvertLowerCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
	}
	
	/**
	 * 下划线转成驼峰, 首字符为大写
	 * eg: sys_user_demo ==> SysUserDemo
	 * @param tableName 表名, eg: sys_user_demo
	 * @return
	 */
	public static String tableNameConvertUpperCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
	}
	
	/**
	 * 表名转成映射路径
	 * eg: sys_user_demo ==> /sys/user/demo
	 * @param tableName 表名
	 * @return
	 */
	public static String tableNameConvertMappingPath(String tableName) {
		tableName = tableName.toLowerCase();
		return File.separator + (tableName.contains("_") ? tableName.replaceAll("_", File.separator) : tableName);
	}
	
	/**
	 * 获取表名切割后的数组
	 * @param tableName 表名
	 * @return
	 */
	public static String[] getTableNameSplit(String tableName) {
		String[] strs = tableName.split("_");
		if (!tableName.contains("_") || strs.length < 2) {
			throw new RuntimeException("表名格式不正确, 请按规定格式! 例如: sys_demo");
		}
		return strs;
	}
	
	/**
	 * 包转成路径
	 * eg: com.elling.book.sys.sys ==> com/elling/sys
	 * @param packageName
	 * @return
	 */
	public static String packageConvertPath(String packageName) {
		return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
	}
}
