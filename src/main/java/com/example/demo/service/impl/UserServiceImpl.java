package com.example.demo.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Constants;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Menu;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IMenuService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Autowired
	private IMenuService iMenuService;

	@Override
	public UserDto login(UserDto userDto) {
		User one = getUserInfo(userDto);
		if(one != null) {
			BeanUtils.copyProperties(one,userDto);
			//设置token
			String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
			userDto.setToken(token);

			String role = one.getRole();  //ROLE_ADMIN
			//设置用户菜单列表
			List<Menu> roleMenus = getRoleMenus(role);
			userDto.setMenus(roleMenus);
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

	/**
	 * 获取当前角色的菜单列表
	 * @param roleFlag
	 * @return
	 */
	private List<Menu> getRoleMenus(String roleFlag){
		Integer roleId = roleMapper.selectByFlag(roleFlag);
		//当前角色的所有菜单id集合
		List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

		//查出系统所有的菜单
		List<Menu> menus = iMenuService.findMenus("");
		//new一个最后赛选完成的菜单
		ArrayList<Menu> roleMenus = new ArrayList<>();
		//筛选当前用户角色的菜单
		for (Menu menu : menus) {
			Boolean isSuccess = false;
			if(menuIds.contains(menu.getId())){
				roleMenus.add(menu);
			}
			List<Menu> children = menu.getChildren();
			//需要移除的child
			List<Menu> tempChild = new ArrayList<>();
			//移除 children 里不在 menuIds 里的 id
			for (Menu child : children) {
				if(!menuIds.contains(child.getId())){
					//children.remove(child);
					tempChild.add(child);
					isSuccess = true;
				}
			}
			//如果临时child不为空则删除其中的child
			if(tempChild.size()!=0){
				children.removeAll(tempChild);
			}
			if(children.size() != 0 && isSuccess){
				roleMenus.add(menu);
			}
		}
		return roleMenus;
	}
}
