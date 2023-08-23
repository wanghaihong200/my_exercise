package com.example.springbootlearn.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springbootlearn.db.domain.ice.AddressBook;
import com.example.springbootlearn.db.mappers.ice.AddressBookMapper;
import com.example.springbootlearn.db.service.AddressBookService;
import com.example.springbootlearn.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 用户地址簿
 * @author: 王海虹
 * @create: 2022-11-30 16:47
 */
@RequestMapping("/addressBook")
@RestController
@Api(tags = "用户地址簿")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    @ApiOperation("查询所有的收货地址")
    public Result listAddressBook() {
        Long userId = 1417012167126876162L;

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getIsDeleted, 0)
                .orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> addressBookList = addressBookService.list(queryWrapper);

        return Result.success(addressBookList);
    }

    @PutMapping("/default")
    public Result changeDefaultAddress(@RequestBody AddressBook addressBook) {
        return addressBookService.changeDefaultAddress(addressBook);
    }

    @GetMapping(path = "/{addressBookId}")
    @ApiOperation("收货地址查询")
    public Result queryAddressBook(@PathVariable("addressBookId") Long id) {
        AddressBook addressBook = addressBookService.getById(id);

        if (addressBook != null) {
            return Result.success(addressBook);
        }
        return Result.fail("地址查询失败！");
    }

    @PutMapping
    @ApiOperation("更新收货地址")
    public Result updateAddressBook(@RequestBody AddressBook addressBook) {
        Boolean updateAddress = addressBookService.updateById(addressBook);
        return updateAddress ? Result.success("收货地址更新成功！") : Result.fail("收货地址更新失败！");
    }

    @DeleteMapping
    @ApiOperation("删除收货地址")
    public Result deleteAddressBook(@RequestParam("ids") List<Long> idList) {
        LambdaQueryWrapper<AddressBook> addressBookLambdaQueryWrapper = new LambdaQueryWrapper<>();
        addressBookLambdaQueryWrapper.in(AddressBook::getId, idList);

        List<AddressBook> addressBookList = addressBookService.list(addressBookLambdaQueryWrapper);
        //List<AddressBook> addressBookList = addressBookService.listByIds(idList);
        addressBookList = addressBookList.stream().map(item->{
            item.setIsDeleted(1);
            return item;
        }).collect(Collectors.toList());
        Boolean deleteAddresses = addressBookService.updateBatchById(addressBookList);

        return deleteAddresses ? Result.success("删除收货地址成功！") : Result.fail("删除地址失败！");
    }

    @PostMapping
    @ApiOperation("新增收货地址")
    public Result addAddress(@RequestBody AddressBook addressBook){
        addressBook.setUserId(1417012167126876162L);
        Boolean saveAddress = addressBookService.save(addressBook);

        return saveAddress ? Result.success("新增收货地址成功！") : Result.fail("新增地址失败！");
    }
}
