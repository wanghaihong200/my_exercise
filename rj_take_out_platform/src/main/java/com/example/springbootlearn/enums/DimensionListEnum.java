package com.example.springbootlearn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * @description: 表 rc_dimension_list.dimension 枚举类
 * @author: 王海虹
 * @create: 2022-10-12 16:11
 */
@AllArgsConstructor
public enum DimensionListEnum {
    unionid("unionid"),
    member("会员号"),
    phone("手机号"),
    dcBuyerAccount("支付账号"),
    openId("微信开放账号"),
    deviceId("设备号"),
    hotelId("mhotelId"),
    keyword("关键字"),
    ;

    @Getter
    @Setter
    private final String desc;

    public static DimensionListEnum findByDesc(String desc) {
        return Arrays.stream(DimensionListEnum.values())
                .filter(dimensionListEnum -> dimensionListEnum.getDesc().equals(desc))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        System.out.println(findByDesc("微信开放账号"));
    }
}
