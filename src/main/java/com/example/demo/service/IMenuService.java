package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhyuanlian
 * @since 2022-07-14
 */
public interface IMenuService extends IService<Menu> {

	List<Menu> findMenus(String name);
}
