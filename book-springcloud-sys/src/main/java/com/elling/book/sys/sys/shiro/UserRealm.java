package com.elling.book.sys.sys.shiro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.elling.book.sys.common.constant.Constants;
import com.elling.book.sys.common.utils.StringUtil;
import com.elling.book.sys.sys.model.SysRole;
import com.elling.book.sys.sys.model.SysUser;
import com.elling.book.sys.sys.service.SysRoleService;
import com.elling.book.sys.sys.service.SysUserService;
import com.mysql.cj.util.StringUtils;

public class UserRealm extends AuthorizingRealm{
	
	private Logger logger = Logger.getLogger(UserRealm.class);
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
		String username = ((SysUser)token.getPrimaryPrincipal()).getUsername(); 
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(); 
        //从数据库获取角色和权限，并设置到authorizationInfo返回
//        Set<String> roles = userService.selectRolesByUsername(username);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("username",username);
        List<SysRole> rs = sysRoleService.getSysRolebyCondition(map);
        Set<String> roles = new HashSet<String>();
        for(SysRole r:rs) {
        	roles.add(r.getRoleCode());
        }
        
        Set<String> permissions = null;//userService.selectPermissionsByUsername(username);
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;  
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) at;  
        String username = (String)token.getUsername();
        if(!StringUtils.isNullOrEmpty(username)){
            //根据用户名，从数据库获取
            SysUser user = sysUserService.findBy("username", username);
            if(user == null){
                throw new UnknownAccountException("账户不存在");
            }
            if(!Constants.STATE_OK.equals(StringUtil.getString(user.getStatus()))){
                throw new LockedAccountException("账户被锁定");
            }
            //返回，并判断密码
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }
        return null;
	}
	
}
