package com.elling.book.sys.sys.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.elling.book.sys.sys.model.ResultData;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	
	@Override
	protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception{
		
//		//判断请求是否是一个Ajax请求 
		Subject subject = getSubject(request,response);
		if(subject.getPrincipal()==null) {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//			if (isAjax(request)) {
	            httpServletResponse.setCharacterEncoding("UTF-8");
	            httpServletResponse.setContentType("application/json");
	            httpServletResponse.setStatus(403);
	            ResultData resultData = new ResultData();
	            resultData.setResult("1");
	            resultData.setCode("403");
	            resultData.setMessage("登录认证失效，请重新登录!");
	            httpServletResponse.getWriter().write(JSON.toJSONString(resultData));
//	        }
			return false;
		}else {
			return true;
		}
		
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response,Object mappedValue){
		boolean allowed = super.isAccessAllowed(request, response, mappedValue);
		if(!allowed) {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			String method = WebUtils.toHttp(request).getMethod();
			if(StringUtils.equalsIgnoreCase("OPTIONS", method)) {
				setHeader(httpRequest,httpResponse);
				return true;
			}
		}
		
		return allowed;
	}
	
	/**
	 * 为respones设置header，实现跨域
	 * @param request
	 * @param response
	 */
	private void setHeader(HttpServletRequest request,HttpServletResponse response) {
		
		response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-control-Allow-Methods", request.getMethod());
		response.setHeader("Access-control-Allow-Credentials", "true");
//		response.setHeader("Access-control-Allow-Headers", request.getHeader("Access-control-Allow-Headers"));
		response.setHeader("Access-control-Allow-Headers", "Content-Type,Access-Token");
//		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.setStatus(HttpStatus.OK.value());
		
		
	}
	
	 private boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
