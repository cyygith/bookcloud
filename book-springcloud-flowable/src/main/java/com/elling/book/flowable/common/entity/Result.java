package com.elling.book.flowable.common.entity;


import java.io.Serializable;

import com.elling.book.flowable.common.constant.ConstantCode;

public class Result<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * .标示
	 */
	private int code;
	/**
	 * .返回前端的消息提醒
	 */
	private String msg;
	
	/**
	 * .返回内容
	 */
	private T data;
	
	private T data1;
	
	private T data2;
	
	private int count;
	
	private Result() {
		super();
	}
	
	public static <T> Result<T> success(){
		return success(null);
	}
	public static <T> Result<T> success(T data){
		return success(data,"操作成功");
	}
	public static <T> Result<T> success(T data,T data1){
		return success(data,data1,null,ConstantCode.SUCCESS,"操作成功");
	}
	public static <T> Result<T> success(T data,int count){
		return success(count,data,ConstantCode.SUCCESS,"操作成功");
	}
	
	public static <T> Result<T> success(T data,String msg){
		return success(data,ConstantCode.SUCCESS,msg);
	}
	
	public static <T> Result<T> success(Integer code,String msg){
		return success(null,code,msg);
	}
	
	public static <T> Result<T> success(T data,Integer code,String msg){
		Result<T> resultData = new Result<T>();
		resultData.setCode(code);
		resultData.setMsg(msg);;
		resultData.setData(data);;
		return resultData;
	}
	
	public static <T> Result<T> success(T data,T data1,T data2,Integer code,String msg){
		Result<T> resultData = new Result<T>();
		resultData.setCode(code);
		resultData.setMsg(msg);;
		resultData.setData(data);
		resultData.setData1(data1);
		resultData.setData2(data2);
		return resultData;
	}
	
	public static <T> Result<T> success(int count,T data,Integer code,String msg){
		Result<T> resultData = new Result<T>();
		resultData.setCode(code);
		resultData.setMsg(msg);;
		resultData.setData(data);
		resultData.setCount(count);
		return resultData;
	}
	
	public static <T> Result<T> error(){
		Result<T> resultData = new Result<T>();
		resultData.setCode(ConstantCode.ERROR);
		resultData.setMsg("操作失败");;
		return resultData;
	}
	public static <T> Result<T> error(String msg){
		Result<T> resultData = new Result<T>();
		resultData.setCode(ConstantCode.ERROR);
		resultData.setMsg(msg);;
		return resultData;
	}
	
	
	public Result(int code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData1() {
		return data1;
	}

	public void setData1(T data1) {
		this.data1 = data1;
	}

	public T getData2() {
		return data2;
	}

	public void setData2(T data2) {
		this.data2 = data2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
