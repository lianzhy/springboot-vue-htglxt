package com.example.demo.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * mp 代码生成器
 * @author ：zhyuanlian
 * @description：TODO
 * @date ：2022/7/4 8:57
 */

public class Generator {

	private static String url = "jdbc:mysql://localhost:3308/testsv?serverTimezone=GMT%2b8&characterEncoding=UTF8";

	private static String username = "root";

	private static String password = "111111";

	public static void main(String[] args) {
		generator();
	}

	private static void generator(){
		FastAutoGenerator.create(url, username, password)
				.globalConfig(builder -> {
					builder.author("zhyuanlian") // 设置作者
							.enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.outputDir("D:\\springboot-vue-htgl\\springboot\\src\\main\\java\\"); // 指定输出目录
				})
				.packageConfig(builder -> {
					builder.parent("com.example.demo") // 设置父包名
							.moduleName("") // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\springboot-vue-htgl\\springboot\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
				})
				.strategyConfig(builder -> {
					builder.entityBuilder().enableLombok();  //启用lombok
					builder.mapperBuilder().enableMapperAnnotation().build();  //mapper注解
					builder.controllerBuilder().enableHyphenStyle()  //开启驼峰转连字符
									.enableRestStyle();             //开启@RestController 控制器
					builder.addInclude("sys_file") // 设置需要生成的表名
							.addTablePrefix("sys_"); // 设置过滤表前缀
				})
//				.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}
}
