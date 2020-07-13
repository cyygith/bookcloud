package com.elling.book.sys.sys.service.impl;

import com.elling.book.sys.sys.dao.mapper.SysUserDeptMapper;
import com.elling.book.sys.sys.model.SysUserDept;
import com.elling.book.sys.sys.service.SysUserDeptService;
import com.elling.book.sys.common.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by cyy on 2019/08/14.
 */
@Service
public class SysUserDeptServiceImpl extends AbstractService<SysUserDept> implements SysUserDeptService {

    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

}
