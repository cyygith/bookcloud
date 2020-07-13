package com.elling.book.sys.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.elling.book.sys.common.entity.Result;
import com.elling.book.sys.sys.model.SysRole;
import com.elling.book.sys.sys.model.SysUser;
import com.elling.book.sys.sys.service.SysRoleService;
import com.elling.book.sys.sys.service.SysUserService;

@CrossOrigin
@RestController
@RequestMapping(value="/syslogin")
public class LoginController {
	
	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("/login")
    public Result login(@RequestBody SysUser sysUser) {
		Map resultMap = new HashMap();
		String sid = "";
		UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(),sysUser.getPassword());
//		token.setRememberMe(true);//是否设置为记住我
		try {
			Subject currUser = SecurityUtils.getSubject();
			currUser.login(token);
			boolean isAuthenticated = currUser.isAuthenticated();
			String roleIds = "";
			if(isAuthenticated) {
				Session session = currUser.getSession();
				SysUser sysuser = (SysUser)currUser.getPrincipal();
				sid = (String)session.getId();
				
				Map pMap = new HashMap();
				pMap.put("userId", sysuser.getUserId());
				List<SysRole> roles = sysRoleService.getSysRolebyCondition(pMap);
				SysUser user = sysUserService.findById(sysuser.getUserId());
				resultMap.put("roles", roles);
				resultMap.put("user", user);
			}
			
			resultMap.put("token", sid);
			
		}catch(UnknownAccountException e) {
			logger.error("Account is exist");
			return Result.error("账户不存在");
		}catch(IncorrectCredentialsException e) {
			e.printStackTrace();
			logger.error("username and password is not matched");
			return Result.error("用户名和密码不正确");
		}catch(LockedAccountException e) {
			e.printStackTrace();
			logger.error("Account is been lock");
			return Result.error("账户被锁定");
		}catch(Exception e) {
			e.addSuppressed(e);
			logger.error("Other reason Error" + e.getMessage());
			return Result.error("登录失败，请联系管理员");
		}
		System.out.println("文件问题："+JSON.toJSONString(Result.success(resultMap)));
        return Result.success(resultMap);
    }
	
	

}
