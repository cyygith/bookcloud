package com.elling.book.sys.sys.service.impl;

import com.elling.book.sys.sys.dao.mapper.SysUserRoleMapper;
import com.elling.book.sys.sys.model.SysUserRole;
import com.elling.book.sys.sys.service.SysUserRoleService;
import com.elling.book.sys.common.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by cyy on 2019/08/14.
 */
@Service
public class SysUserRoleServiceImpl extends AbstractService<SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

}
