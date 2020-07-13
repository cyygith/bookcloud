package com.elling.book.sys.tool.model;

public class TableColEntity {
	private String colunm;
	private String comment;
	private String colunmKey;//列主键
	private String dataType;//数据类型
	private String dealType;//处理类型
	private String colunmUp;//字段Upper类型，如sys_id->sysId
	
	
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColunmKey() {
		return colunmKey;
	}
	public void setColunmKey(String colunmKey) {
		this.colunmKey = colunmKey;
	}
	public String getColunm() {
		return colunm;
	}
	public void setColunm(String colunm) {
		this.colunm = colunm;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getColunmUp() {
		return colunmUp;
	}
	public void setColunmUp(String colunmUp) {
		this.colunmUp = colunmUp;
	}
	
	
}
