package com.example.demo.mapper;

import com.example.demo.entity.Files;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhyuanlian
 * @since 2022-07-11
 */
@Mapper
public interface FileMapper extends BaseMapper<Files> {

}
