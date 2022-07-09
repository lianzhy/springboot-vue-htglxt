package com.example.demo.exception;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  全局异常处理器
 * </p>
 *
 * @Author llzyy
 * @Date 2022/7/8 23:01
 **/

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * <p>如果抛出的是ServiceException，则调用该方法</p>
	 * @param se
	 * @return Result
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public Result handle(ServiceException se){
		return Result.error(se.getCode(),se.getMessage());
	}
}
