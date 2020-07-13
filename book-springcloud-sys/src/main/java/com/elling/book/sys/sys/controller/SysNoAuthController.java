package com.elling.book.sys.sys.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elling.book.sys.common.entity.Result;
import com.elling.book.sys.common.utils.DateUtil;
import com.elling.book.sys.sys.model.SysUser;
import com.elling.book.sys.sys.service.SysUserService;

@CrossOrigin
@RestController
@RequestMapping(value="/sysNoAuth")
public class SysNoAuthController {
	private Logger logger = Logger.getLogger(SysNoAuthController.class);
	
	@Autowired
    SysUserService sysUserService;
	
	
	/**
	 * .注册
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("registerUser")
    public Result add(@RequestBody SysUser sysUser) {
    	try {
    		SysUser user = sysUserService.findBy("username", sysUser.getUsername());
    		if(user!=null) {
    			return Result.error("该手机号已经已经存在，请使用其他手机号注册");
    		}else {
    			sysUser.setCreateTime(DateUtil.getNowTime());
    			sysUserService.save(sysUser);
    		}
	    }catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		return Result.error(e.getMessage());
    	}
	    return Result.success();
    }
	
	
}
