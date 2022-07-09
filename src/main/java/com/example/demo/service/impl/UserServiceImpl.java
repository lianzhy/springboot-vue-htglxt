package com.example.demo.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Constants;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhyuanlian
 * @since 2022-07-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	private static final Log log = Log.get();

	@Override
	public UserDto login(UserDto userDto) {
		User one = getUserInfo(userDto);
		if(one != null) {
			BeanUtils.copyProperties(one,userDto);
			//设置token
			String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
			userDto.setToken(token);
			return userDto;
		}else{
			throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
		}
	}

	@Override
	public User register(UserDto userDto) {
		QueryWrapper<User> queryUser= new QueryWrapper<>();
		queryUser.eq("username",userDto.getUsername());
		User one;
		try{
			one = getOne(queryUser);        //从数据库查询用户信息
		}catch (Exception e){
			log.error(e);
			throw new ServiceException(Constants.CODE_500,"系统错误");
		}
		if(one == null){
			one = new User();
			BeanUtils.copyProperties(userDto,one);
			save(one);
		}else{
			throw new ServiceException(Constants.CODE_600,"用户已存在！");
		}
		return one;
	}

	private User getUserInfo(UserDto userDto){
		QueryWrapper<User> queryUser= new QueryWrapper<>();
		queryUser.eq("username",userDto.getUsername());
		queryUser.eq("password",userDto.getPassword());
		User one;
		try{
			one = getOne(queryUser);        //从数据库查询用户信息
		}catch (Exception e){
			log.error(e);
			throw new ServiceException(Constants.CODE_500,"系统错误");
		}
		return one;
	}
}
