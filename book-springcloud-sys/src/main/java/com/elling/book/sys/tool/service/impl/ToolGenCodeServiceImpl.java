package com.elling.book.sys.tool.service.impl;

import java.util.List;
import java.util.Map;

import com.elling.book.sys.tool.dao.mapper.ToolGenCodeMapper;
import com.elling.book.sys.tool.model.ToolGenCode;
import com.elling.book.sys.tool.service.ToolGenCodeService;
import com.elling.book.sys.common.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by cyy on 2019-12-20 09:50:31.
 */
@Service
public class ToolGenCodeServiceImpl extends AbstractService<ToolGenCode> implements ToolGenCodeService {

    @Autowired
    private ToolGenCodeMapper toolGenCodeMapper;
    
	@Override
	public List<Map<String,Object>> getByCondition(ToolGenCode toolGenCode) {
		return toolGenCodeMapper.getByCondition(toolGenCode);
	}
}
