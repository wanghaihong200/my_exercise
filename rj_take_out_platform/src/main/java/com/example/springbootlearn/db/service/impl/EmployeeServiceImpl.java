package com.example.springbootlearn.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.Employee;
import com.example.springbootlearn.db.service.EmployeeService;
import com.example.springbootlearn.db.mappers.ice.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
 * @author TCLDUSER
 * @description 针对表【employee(后台员工信息)】的数据库操作Service实现
 * @createDate 2022-10-25 13:53:15
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {

}




