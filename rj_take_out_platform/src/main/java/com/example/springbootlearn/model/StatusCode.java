package com.example.springbootlearn.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @program fraud-data-report
 * @description:状态枚举
 * @author: wanghaihong
 * @create: 2021/06/07 17:31
 */

public enum StatusCode {
    // 成功状态
    SUCCESS(1, "success"),
    // 失败状态
    FAIL(-1, "fail"),
    VALID_ERROR(100, "数据校验失败"),
    SERVER_ERROR(500, "服务器异常"),
    PARAMS_ERROR(400, "参数错误"),
    JSON_PARSE_ERROR(10003, "Json解析错误"),
    ILLEAGAL_STRING(15001, "非法字符串");

    StatusCode(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    @Getter
    @Setter
    private Integer code;

    @Setter
    @Getter
    private String des;
}
