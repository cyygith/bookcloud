package com.elling.book.sys.common.utils;

import java.util.UUID;

import tk.mybatis.mapper.genid.GenId;

public class UuidGen implements GenId<String> {

	@Override
	public String genId(String table, String column) {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}

}
