package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @Author llzyy
 * @Date 2022/7/13 16:09
 **/

@RestController
@RequestMapping("/echarts")
public class EchartsController {

	@Autowired
	private IUserService userService;

	@GetMapping("/example")
	public Result get(){
		Map<String,Object> map = new HashMap<>();
		map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
		map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));

		return Result.success(map);
	}

	@GetMapping("/members")
	public Result member(){
		List<User> list = userService.list();
		//一年四个季度
		int[] index = new int[4];
		for (User user : list) {
			Date createTime = user.getCreateTime();
			Quarter quarter = DateUtil.quarterEnum(createTime);
			switch (quarter){
				case Q1: index[0] += 1;break;
				case Q2: index[1] += 1;break;
				case Q3: index[2] += 1;break;
				case Q4: index[3] += 1;break;
				default: break;
			}
		}
		return Result.success(index);
	}
}
