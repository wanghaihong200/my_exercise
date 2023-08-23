package com.example.springbootlearn.db.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootlearn.db.domain.ice.AddressBook;
import com.example.springbootlearn.db.service.AddressBookService;
import com.example.springbootlearn.db.mappers.ice.AddressBookMapper;
import com.example.springbootlearn.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author TCLDUSER
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-11-07 11:15:49
*/
@Service
@DS("ice")
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

    @Override
    @Transactional
    public Result changeDefaultAddress(AddressBook addressBook){
        AddressBook addressBook2 = this.getById(addressBook.getId());
        // 将地址数据 都置为 非默认
        LambdaQueryWrapper<AddressBook> addressBookLambdaQueryWrapper = new LambdaQueryWrapper<>();
        addressBookLambdaQueryWrapper.eq(AddressBook::getIsDeleted, 0);

        List<AddressBook> addressBookList = this.list(addressBookLambdaQueryWrapper);
        addressBookList = addressBookList.stream().map(item -> {
            item.setIsDefault(0);
            return item;
        }).collect(Collectors.toList());
        Boolean setAllNotDefault = this.updateBatchById(addressBookList);

        // 将 id 对应的地址 置为 默认地址
        addressBook.setIsDefault(1);
        Boolean setAddressDefault = this.updateById(addressBook);

        if (setAllNotDefault && setAddressDefault){
            return Result.success("设置默认地址成功！");
        }

        return Result.fail("设置默认地址失败！");
    }
}




