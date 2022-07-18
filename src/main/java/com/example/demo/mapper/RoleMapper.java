package com.example.demo.mapper;

import com.example.demo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhyuanlian
 * @since 2022-07-14
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

	@Select("select id from sys_role where flag = #{flag}")
	Integer selectByFlag(@Param("flag") String flag);
}
