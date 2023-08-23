package com.example.springbootlearn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * @description: 名单类型枚举
 * @author: 王海虹
 * @create: 2022-10-12 17:32
 */
@AllArgsConstructor
public enum ListTypeEnum {
    BL(1, "BL", "黑名单"),
    GL(2, "GL", "灰名单"),
    WL(3, "GL", "白名单"),
    DG(4, "GL", "降级名单"),
    ;

    // 场景
    @Getter
    @Setter
    int listType;

    // 描述
    @Getter
    @Setter
    String desc;

    // 中文描述
    @Getter
    @Setter
    String chineseDesc;

    public static ListTypeEnum getListTypeEnum(int listType) {
        return Arrays.stream(ListTypeEnum.values())
                .filter(ListTypeEnum -> ListTypeEnum.getListType() == listType)
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        System.out.println(getListTypeEnum(2).getChineseDesc());
    }
}
