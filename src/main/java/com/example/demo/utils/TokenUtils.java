package com.example.demo.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * <p>
 *  Token工具类
 * </p>
 *
 * @Author llzyy
 * @Date 2022/7/9 23:05
 **/

@Component
public class TokenUtils {

	private static IUserService staticUserService;

	@Resource
	private IUserService userService;

	public static final String code = "#^*&^$(&&^!~";

	@PostConstruct
	public void setUserService(){
		staticUserService = userService;
	}

	/**
	 * 生成 token
	 * @param userID
	 * @param sign
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String genToken(String userID,String sign) {
		try {
			return JWT.create().withAudience(userID)   //将userID保存到token里，作为载荷
					.withExpiresAt(DateUtil.offsetHour(new Date(),2))  //2小时过期
					.sign(Algorithm.HMAC256(sign+code));		//以password作为token的密钥
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *获取当前登录的信息
	 * @return User
	 */
	public static User getCurrentUser(){
		try {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			String token = request.getHeader("token");
			if(StrUtil.isNotBlank(token)) {
				String userId = JWT.decode(token).getAudience().get(0);
				return staticUserService.getById(Integer.valueOf(userId));
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}
