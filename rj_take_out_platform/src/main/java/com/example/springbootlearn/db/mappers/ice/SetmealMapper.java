package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.Setmeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

/**
* @author TCLDUSER
* @description 针对表【setmeal(套餐)】的数据库操作Mapper
* @createDate 2022-11-07 11:16:16
* @Entity com.example.springbootlearn.db.domain.ice.Setmeal
*/
@Mapper
@DS("ice")
public interface SetmealMapper extends BaseMapper<Setmeal> {

    Setmeal queryById(Long id);
}




