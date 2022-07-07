package com.example.demo.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
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

    @PostMapping
    public Boolean saveOrUpdate(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
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
        return userService.page(new Page<>(pageNum,pageSize),queryWrapper);
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
    public Boolean imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //List<User> list = reader.readAll(User.class);
        List<User> list = reader.read(0, 1, User.class);
        userService.saveBatch(list);
        inputStream.close();
        return true;
    }
}

