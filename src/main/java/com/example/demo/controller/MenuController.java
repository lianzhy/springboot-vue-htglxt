package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import com.example.demo.entity.Dict;
import com.example.demo.entity.Menu;
import com.example.demo.mapper.DictMapper;
import com.example.demo.service.IMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhyuanlian
 * @since 2022-07-14
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;

    @PostMapping
    public Result saveOrUpdate(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        return Result.success(menuService.findMenus(name));
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
		QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("id");
        queryWrapper.like("name",name);
        return Result.success(menuService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(dictQueryWrapper));
    }

}

