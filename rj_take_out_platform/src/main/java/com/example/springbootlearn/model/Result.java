package com.example.springbootlearn.model;

import lombok.Builder;
import lombok.Data;

/**
 * @program fraud-data-report
 * @description:统一封装返回数据
 * @author: wanghaihong
 * @create: 2021/06/07 17:29
 */
@Data
@Builder
public class Result<T> {
    private Integer code;

    private String msg;

    private Object data;

    public static Result success() {
        return Result.builder()
                .code(StatusCode.SUCCESS.getCode())
                .msg(StatusCode.SUCCESS.getDes())
                .build();
    }

    public static Result success(String msg) {
        return Result.builder()
                .code(StatusCode.SUCCESS.getCode())
                .msg(msg)
                .build();
    }
    public static Result success(String msg, Object data) {
        return Result.builder()
                .code(StatusCode.SUCCESS.getCode())
                .msg(msg)
                .data(data)
                .build();
    }

    public static Result success(Object data) {
        return Result.builder()
                .code(StatusCode.SUCCESS.getCode())
                .msg(StatusCode.SUCCESS.getDes())
                .data(data)
                .build();
    }

    public static Result success(Integer code, Object data) {
        return Result.builder()
                .code(code)
                .msg(StatusCode.SUCCESS.getDes())
                .data(data)
                .build();
    }

    public static Result success(Integer code, String msg) {
        return Result.builder()
                .code(code)
                .msg(msg)
                .build();
    }

    public static Result fail(String msg) {
        return Result.builder()
                .code(StatusCode.FAIL.getCode())
                .msg(msg)
                .build();
    }

    public static Result fail(StatusCode statusCode) {
        return Result.builder()
                .code(statusCode.getCode())
                .msg(statusCode.getDes())
                .build();
    }

    public static Result fail(StatusCode statusCode, String msg) {
        return Result.builder()
                .code(statusCode.getCode())
                .msg(msg)
                .build();
    }

    public static Result fail(StatusCode statusCode, Object data) {
        return Result.builder()
                .code(statusCode.getCode())
                .msg(statusCode.getDes())
                .data(data)
                .build();
    }

    public static Result fail(Integer code, String msg) {
        return Result.builder()
                .code(code)
                .msg(msg)
                .build();
    }
}
