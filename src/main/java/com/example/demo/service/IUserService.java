package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhyuanlian
 * @since 2022-07-04
 */
public interface IUserService extends IService<User> {

	UserDto login(UserDto userDto);

	User register(UserDto userDto);
}
