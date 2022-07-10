package com.example.demo.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @Author llzyy
 * @Date 2022/7/10 22:22
 **/

@Data
@TableName("sys_file")
@ApiModel(value = "File对象",description = "")
public class File implements Serializable {

	@ApiModelProperty("id主键")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty("文件名")
	@Alias("文件名")
	private String name;

	@ApiModelProperty("文件类型")
	@Alias("文件类型")
	private String type;

	@ApiModelProperty("文件大小")
	@Alias("文件大小")
	private Long size;

	@ApiModelProperty("文件地址")
	@Alias("文件地址")
	private String url;

	@ApiModelProperty("是否删除")
	@Alias("是否删除")
	private Boolean isDelete;

	@ApiModelProperty("是否禁用")
	@Alias("是否禁用")
	private Boolean enable;
}
