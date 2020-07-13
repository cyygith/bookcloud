package com.elling.book.sys.sys.service.impl;

import com.elling.book.sys.sys.dao.mapper.SysRoleMenuMapper;
import com.elling.book.sys.sys.model.SysRoleMenu;
import com.elling.book.sys.sys.service.SysRoleMenuService;
import com.elling.book.sys.common.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by cyy on 2019/08/14.
 */
@Service
public class SysRoleMenuServiceImpl extends AbstractService<SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

}
