package com.elling.book.sys.contents.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elling.book.sys.common.base.AbstractService;
import com.elling.book.sys.contents.dao.mapper.CChartsMapper;
import com.elling.book.sys.contents.model.CArticle;
import com.elling.book.sys.contents.service.CChartsService;

/**
 *
 * Created by cyy on 2019-10-25 15:01:45.
 */
@Service
public class CChartsServiceImpl extends AbstractService<CArticle> implements CChartsService {

    @Autowired
    private CChartsMapper cChartsMapper;

	@Override
	public List getCLineCharts(Map map) {
		return cChartsMapper.getCLineCharts(map);
	}

}
