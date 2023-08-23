package com.example.springbootlearn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 羊毛名单添加备注 枚举
 * @author: 王海虹
 * @create: 2022-10-12 19:34
 */
@AllArgsConstructor
public enum UnusualListCommentEnum {
    STRATEGY("策略命中"),
    MIS("mis维护"),
    RISK_USER("风控人工维护");

    @Getter
    @Setter
    private final String desc;
}
