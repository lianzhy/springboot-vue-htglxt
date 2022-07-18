package com.example.demo.dto;

import com.example.demo.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * <p>用户登录实体类</p>
 * @Author:lianzhyu
 * @Date:2022.10.28
 */
@Data
public class UserDto {

	private String username;

	private String password;

	private String nickname;

	private String avatarUrl;

	private String token;

	private List<Menu> menus;

	private String role;
}
