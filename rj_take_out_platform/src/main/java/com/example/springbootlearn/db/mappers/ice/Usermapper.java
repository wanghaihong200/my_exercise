package com.example.springbootlearn.db.mappers.ice;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author www.javacoder.top
 * @since 2022-10-13
 */
@DS("ice")
@Mapper
public interface Usermapper extends BaseMapper<User> {


    List<User> selectAllByIdOrderByAgeDesc(@Param("id") Long id);

}
