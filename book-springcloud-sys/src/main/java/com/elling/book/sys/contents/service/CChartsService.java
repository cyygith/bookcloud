package com.elling.book.sys.contents.service;

import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.base.Service;
import com.elling.book.sys.contents.model.CArticle;

public interface CChartsService extends Service<CArticle>{
	
	public List getCLineCharts(Map map);
}
