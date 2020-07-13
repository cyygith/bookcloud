package com.elling.book.sys.sys.service.impl;

import com.elling.book.sys.sys.dao.mapper.SysDeptMapper;
import com.elling.book.sys.sys.model.SysDept;
import com.elling.book.sys.sys.service.SysDeptService;
import com.elling.book.sys.common.base.AbstractService;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.common.utils.TreeBuilder;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by cyy on 2019/08/14.
 */
@Service
public class SysDeptServiceImpl extends AbstractService<SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    
    @Override
	public List<SysDept> getDeptLevel(SysDept sysDept) {
		return sysDeptMapper.getDeptLevel(sysDept);
	}
    
    @Override
	public List getDeptByUserID(Map map) {
		List list = sysDeptMapper.getDeptDataByUserId(map);
		return list;
	}
    
    /**
     * 这里获取的是组装成树节点的数据
     */
	@Override
	public List getDeptDataByUserID(Map map) {
		List list = sysDeptMapper.getDeptDataByUserId(map);
		List resulList = TreeBuilder.buildTree(list);
		return resulList;
	}

	@Override
	public List getAllDeptData(Map map) {
		List list = sysDeptMapper.getAllDeptData(map);
		List resulList = TreeBuilder.buildTree(list);
		return resulList;
	}

	

}
