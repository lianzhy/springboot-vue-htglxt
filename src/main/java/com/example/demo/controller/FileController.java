package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 文件操作
 * </p>
 *
 * @Author llzyy
 * @Date 2022/7/10 21:26
 **/

@RestController
@RequestMapping("/file")
public class FileController {

	@Value("${files.upload.path}")
	private String fileUploadPath;

	/**
	 * 文件上传
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/upload")
	public String upload(@RequestParam MultipartFile file) throws IOException {
		String originalFilename = file.getOriginalFilename();
		String type = FileUtil.extName(originalFilename);
		long size = file.getSize();
		//存储到磁盘
		File uploadParentFile = new File(fileUploadPath);
		if(!uploadParentFile.exists()){
			uploadParentFile.mkdirs();
		}
		//定义一个文件唯一的标识码
		String uuid = IdUtil.fastSimpleUUID();
		File uploadFile = new File(fileUploadPath + uuid + StrUtil.DOT + type);
		//把获取到的文件存到磁盘上去
		file.transferTo(uploadFile);
		//存储数据库
		return "";

	}

}
