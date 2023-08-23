package com.example.springbootlearn.aop;

import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.model.StatusCode;
import com.example.springbootlearn.util.RcLoggerUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @description: 全局异常处理,
 * RestControllerAdvice  自带@ResponseBody注解与@Component注解，具备对应的功能
 * @author: 王海虹
 * @create: 2022-10-12 10:54
 */
@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice {
    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param
     * @return Result
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return Result.fail(StatusCode.PARAMS_ERROR, "ConstraintViolationException，输入数据异常" + message);
    }

    /**
     * 处理Post请求,参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     * 添加全局异常处理流程，根据需要设置需要处理的异常
     *
     * @param e
     * @return Result
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result MethodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        HashMap<String, String> errorMsg = new HashMap<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMsg.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return Result.fail(StatusCode.PARAMS_ERROR, "MethodArgumentNotValidException，输入数据异常" + errorMsg);
    }

    /**
     * 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     *
     * @param bindException
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException bindException) {
        HashMap<String, String> errorMsg = new HashMap<>();

        for (FieldError fieldError : bindException.getBindingResult().getFieldErrors()) {
            errorMsg.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return Result.fail(StatusCode.PARAMS_ERROR, "BindException，输入数据异常" + errorMsg);
    }

    @ExceptionHandler(RuntimeException.class)
    public Result exceptionHandler(HttpServletRequest request, RuntimeException e) {
        e.printStackTrace();
        RcLoggerUtils.recordSystemError(log, "ExceptionControllerAdvice", System.currentTimeMillis(), request, e.getMessage());
        return Result.fail(StatusCode.SERVER_ERROR, e.getMessage());
    }
}
