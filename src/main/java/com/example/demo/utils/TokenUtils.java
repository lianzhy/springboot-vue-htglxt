package com.example.demo.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

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


public class TokenUtils {

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
					.sign(Algorithm.HMAC256(sign));		//以password作为token的密钥
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
