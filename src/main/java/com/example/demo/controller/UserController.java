package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import com.example.demo.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhyuanlian
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){

        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }

        UserDto dto = userService.login(userDto);
        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userDto));
    }

    @PostMapping
    public Result saveOrUpdate(@RequestBody User user) {
        return Result.success(userService.saveOrUpdate(user));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeBatchByIds(ids));
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/username/{username}")
    public Result findOneByUsername(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address ) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.like("username", username).and(i -> i.like("email", email).like("address", address));
        if(!username.equals("")){
            queryWrapper.like("username", username);
        }
        if(!email.equals("")){
            queryWrapper.like("email", email);
        }
        if(!address.equals("")){
            queryWrapper.like("address", address);
        }
        queryWrapper.orderByDesc("id");

        User currentUser = TokenUtils.getCurrentUser();
        System.out.println(currentUser);

        return Result.success(userService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    /**
     * 导出用户数据
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        //从数据库查出所有数据
        List<User> list = userService.list();
        //在内存操作，写出到浏览器
        ExcelWriter write = ExcelUtil.getWriter(true);
//        //自定义标题名
//        write.addHeaderAlias("name","用户名");
//        write.addHeaderAlias("password","密码");
//        write.addHeaderAlias("nickname","昵称");
//        write.addHeaderAlias("email","邮箱");
//        write.addHeaderAlias("phone","电话");
//        write.addHeaderAlias("address","地址");
//        write.addHeaderAlias("createTime","创建时间");
//        write.addHeaderAlias("avatarUrl","头像");

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        write.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        write.flush(out,true);
        out.close();
        write.close();
    }

    /**
     * 导入用户数据 .xlsx
     * @param file
     * @throws IOException
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //List<User> list = reader.readAll(User.class);
        List<User> list = reader.read(0, 1, User.class);
        userService.saveBatch(list);
        inputStream.close();
        return Result.success(true);
    }


}

