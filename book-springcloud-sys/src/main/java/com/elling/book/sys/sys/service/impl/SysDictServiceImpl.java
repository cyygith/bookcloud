package com.elling.book.sys.sys.service.impl;

import com.elling.book.sys.sys.dao.mapper.SysDictMapper;
import com.elling.book.sys.sys.model.SysDict;
import com.elling.book.sys.sys.service.SysDictService;
import com.elling.book.sys.common.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by cyy on 2019/08/14.
 */
@Service
public class SysDictServiceImpl extends AbstractService<SysDict> implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

}
