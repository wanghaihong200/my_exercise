package com.example.springbootlearn.controller.backend;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springbootlearn.db.domain.ice.Employee;
import com.example.springbootlearn.db.service.TokenService;
import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.model.StatusCode;
import com.example.springbootlearn.db.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-10-25 14:34
 */
@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags = "后台相关接口")
public class PlatformBackendController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @ApiOperation("后台登录接口")
    public Result login(HttpServletRequest request, @RequestBody Employee employee) {
        // 对密码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        // 根据 接口入参 查询表
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername())
                .eq(Employee::getPassword, password)
                .eq(Employee::getStatus, 1);

        Employee emp = employeeService.getOne(queryWrapper);

        if (emp == null) {
            // 若未查到 员工数据，则登录失败
            return Result.fail(StatusCode.PARAMS_ERROR, "用户名或密码错误！");
        }

        // 获取session对象， 设置session的最大不活动时间
        HttpSession session = request.getSession();

        // 若登录成功，将员工ID存入Session
        session.setAttribute("employee", emp.getId());

        return Result.success(1, emp);
    }

    /**
     * 后台登出接口
     *
     * @param: request
     * @return:
     * @date: 2022/10/27
     */
    @PostMapping("/logout")
    @ApiOperation("后台登出接口")
    public Result logout(HttpServletRequest request) {

        // 清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return Result.success(1, "退出成功！");
    }

    /**
     * 登录验证
     * @param user
     * @param response
     * @return
     */
    @PostMapping("/login2")
    @ApiOperation("登录2:成功返回token")
    public Result login(@RequestBody Employee user, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();

        // 对密码进行md5加密
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        // 根据 接口入参 查询表, 获取 用户信息
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, user.getUsername())
                .eq(Employee::getPassword, password)
                .eq(Employee::getStatus, 1);
        Employee userForBase = employeeService.getOne(queryWrapper);

        //判断账号或密码是否正确
        if (userForBase == null) {
            return Result.fail("用户名或密码错误");
        } else {
            String token = tokenService.getToken(userForBase);
            jsonObject.put("token", token);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return Result.success(jsonObject);
        }
    }
}
