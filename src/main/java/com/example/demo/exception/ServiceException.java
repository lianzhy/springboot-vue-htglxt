package com.example.demo.exception;

import lombok.Getter;

/**
 * <p>
 *  自定义异常类
 * </p>
 *
 * @Author llzyy
 * @Date 2022/7/8 23:04
 **/

@Getter
public class ServiceException extends RuntimeException {

	private String code;

	public ServiceException(String code,String msg){
		super(msg);
		this.code = code;
	}
}
