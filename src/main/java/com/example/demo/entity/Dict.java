package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-07-14
 */
@Getter
@Setter
  @TableName("sys_dict")
@ApiModel(value = "Dict对象", description = "")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("名称")
      @TableField(value = "name")
      private String name;

      @ApiModelProperty("内容")
      @TableField(value = "value")
      private String value;

      @ApiModelProperty("类型")
      @TableField(value = "type")
      private String type;


}
