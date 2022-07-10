package com.example.demo.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.common.Constants;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.IUserService;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
 * </p>
 *
 * @Author llzyy
 * @Date 2022/7/10 11:14
 **/

public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private IUserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("token");
		//如果不是映射到方法直接通过
		if(!(handler instanceof HandlerMethod)){
			return true;
		}

		//执行认证
		if(StrUtil.isBlank(token)){
			throw new ServiceException(Constants.CODE_401,"无token，请重新登录!");
		}
		//获取 token 中的 user id
		String userID;
		try{
			userID = JWT.decode(token).getAudience().get(0);
		}catch (JWTDecodeException e){
			throw new ServiceException(Constants.CODE_401,"token验证失败!");
		}
		//根据token中的userID查询数据库，验证用户信息
		User user = userService.getById(Integer.valueOf(userID));
		if(user == null){
			throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录!");
		}
		//用户密码加code加签验证 token
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword()+TokenUtils.code)).build();
		try{
			jwtVerifier.verify(token);
		}catch (JWTVerificationException e){
			throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录!");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
