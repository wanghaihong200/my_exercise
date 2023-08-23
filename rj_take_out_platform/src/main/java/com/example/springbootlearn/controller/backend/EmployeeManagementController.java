package com.example.springbootlearn.controller.backend;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootlearn.annotation.UserLoginToken;
import com.example.springbootlearn.db.domain.ice.Employee;
import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.model.qo.PageQo;
import com.example.springbootlearn.db.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @description: 员工管理页面相关接口
 * @author: 王海虹
 * @create: 2022-10-28 15:52
 */
@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags="员工管理页面相关接口")
public class EmployeeManagementController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ApiOperation("新增员工")
    public Result saveEmployee(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工，员工信息：{}", employee.toString());
        // 设置初始密码123456，并进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));

        employee.setCreateTime(DateUtil.date());
        employee.setUpdateTime(DateUtil.date());

        // 获取当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        employeeService.save(employee);

        return Result.success("新增员工成功！");
    }

    @GetMapping("/page")
    @ApiOperation("员工名单分页")
    //@UserLoginToken
    public Result pageEmployee(PageQo pageQo){
        // 分页模型
        Page<Employee> pageInfo = new Page<>(pageQo.getPage(), pageQo.getPageSize());

        // 构造查询条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 模糊查询
        queryWrapper.like(StrUtil.isNotEmpty(pageQo.getName()), Employee::getName, pageQo.getName());
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo,queryWrapper);

        return Result.success(pageInfo);
    }

    @PutMapping("/update")
    @ApiOperation("根据id修改员工信息")
    public Result update(HttpServletRequest request, @RequestBody Employee employee){
        Long empId = (Long)request.getSession().getAttribute("employee");
        employee.setUpdateUser(empId);
        //employee.setUpdateTime(new Date());
        employeeService.updateById(employee);

        return Result.success("员工信息修改成功！");
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result getEmployeeById(@PathVariable("id") Long id){
        log.info("根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        if (employee != null){
            return Result.success(employee);
        }

        return Result.fail("没有查询到对应员工信息");
    }
}
