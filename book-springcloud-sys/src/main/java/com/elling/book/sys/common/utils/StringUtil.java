package com.elling.book.sys.common.utils;

public class StringUtil {
	
	/**
	 * .判断对象不为空
	 * @param object
	 * @return
	 */
	public static boolean isNotEmpty(Object object) {
		if(null == object) {
			return false;
		}
		if(object.toString().trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * .获取内容
	 * @return
	 */
	public static String getString(Object obj) {
		if(null == obj || obj.toString().trim().isEmpty()) {
			return "";
		}
		return obj.toString();
	}
	
	
}
