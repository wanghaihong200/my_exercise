package com.example.springbootlearn.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springbootlearn.db.domain.ice.ShoppingCart;
import com.example.springbootlearn.db.service.ShoppingCartService;
import com.example.springbootlearn.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-12-01 17:46
 */
@RequestMapping("/shoppingCart")
@RestController
@Api(tags="购物车")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    @ApiOperation("查询购物车列表")
    public Result queryShoppingCartList(){
        Long userId = 1417012167126876162L;
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);

        List<ShoppingCart> shoppingCartList =shoppingCartService.list(shoppingCartLambdaQueryWrapper);

        return Result.success(shoppingCartList);
    }

    //@PostMapping("")
}
