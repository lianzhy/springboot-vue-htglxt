package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhyuanlian
 * @since 2022-07-11
 */
@Getter
@Setter
  @TableName("sys_file")
@ApiModel(value = "File对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("文件名称")
      private String name;

      @ApiModelProperty("文件类型")
      private String type;

      @ApiModelProperty("文件大小")
      private Long size;

      @ApiModelProperty("文件md5")
      private String md5;

      @ApiModelProperty("文件地址")
      private String url;

      @ApiModelProperty("是否删除")
      private Boolean isDelete;

      @ApiModelProperty("是否禁用链接")
      private Boolean enable;


}
