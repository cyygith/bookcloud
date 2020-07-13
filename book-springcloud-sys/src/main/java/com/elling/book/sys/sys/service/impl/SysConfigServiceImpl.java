package com.elling.book.sys.sys.service.impl;

import com.elling.book.sys.sys.dao.mapper.SysConfigMapper;
import com.elling.book.sys.sys.model.SysConfig;
import com.elling.book.sys.sys.service.SysConfigService;
import com.elling.book.sys.common.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by cyy on 2019/08/14.
 */
@Service
public class SysConfigServiceImpl extends AbstractService<SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

}
