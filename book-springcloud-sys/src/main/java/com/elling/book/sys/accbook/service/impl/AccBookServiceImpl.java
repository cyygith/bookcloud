package com.elling.book.sys.accbook.service.impl;

import java.util.List;
import java.util.Map;

import com.elling.book.sys.accbook.dao.mapper.AccBookMapper;
import com.elling.book.sys.accbook.model.AccBook;
import com.elling.book.sys.accbook.service.AccBookService;
import com.elling.book.sys.common.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by CYY on 2020-01-03 10:44:03.
 */
@Service
public class AccBookServiceImpl extends AbstractService<AccBook> implements AccBookService {

    @Autowired
    private AccBookMapper accBookMapper;
    
	@Override
	public List<Map<String,Object>> getByCondition(AccBook accBook) {
		return accBookMapper.getByCondition(accBook);
	}
	
	@Override
	public List<Map<String,Object>> getSumByTypeAndTime(Map map){
		return accBookMapper.getSumByTypeAndTime(map);
	};
	
	@Override
	public List<Map<String,Object>> getDayOfMonth(Map map){
		return accBookMapper.getDayOfMonth(map);
	}

	@Override
	public Map<String, Object> getSumDayAndSumCount(Map map) {
		return accBookMapper.getSumDayAndSumCount(map);
	};
	
}
