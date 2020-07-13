package com.elling.book.sys.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static String format = "YYYY-MM-dd HH:mm:ss";
	private static String formatDate = "YYYY-MM-dd";
	private static String formatYearAndMonth = "YYYY-MM";
	private static String formatDate1 = "YYYYMMdd";
	
	
	public static String getNowTime() {
		String date = new SimpleDateFormat(format).format(new Date());
		return date;
	}
	
	public static String getNowDate() {
		String date = new SimpleDateFormat(formatDate).format(new Date());
		return date;
	}
	
	public static String getNowDate1() {
		String date = new SimpleDateFormat(formatDate1).format(new Date());
		return date;
	}
	
	public static String getNowYearAndMonth() {
		String date = new SimpleDateFormat(formatYearAndMonth).format(new Date());
		return date;
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.getNowDate());
		System.out.println(DateUtil.getNowTime());
		System.out.println(DateUtil.getNowYearAndMonth());
	}
	
	
}
