package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author TCLDUSER
 * @description 针对表【employee(后台员工信息)】的数据库操作Mapper
 * @createDate 2022-10-25 13:53:15
 * @Entity com.example.springbootlearn.db.domain.ice.Employee
 */
@Mapper
@DS("ice")
public interface EmployeeMapper extends BaseMapper<Employee> {

}




