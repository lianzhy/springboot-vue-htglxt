package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleMenu;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhyuanlian
 * @since 2022-07-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	@Resource
	private RoleMenuMapper roleMenuMapper;

	@Transactional
	@Override
	public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
		//先删除当前角色所有绑定关系
		QueryWrapper<RoleMenu> qw = new QueryWrapper<>();
		qw.eq("role_id",roleId);
		roleMenuMapper.delete(qw);

		//然后再把前端传过来的菜单id数组绑定上去
		for (Integer menuId : menuIds) {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenuMapper.insert(roleMenu);
		}
	}

	@Override
	public List<Integer> getRoleMenu(Integer roleId) {
		return roleMenuMapper.selectByRoleId(roleId);
	}

}
