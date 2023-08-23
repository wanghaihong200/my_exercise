package com.example.springbootlearn.db.service;

import com.example.springbootlearn.db.domain.ice.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootlearn.model.Result;

/**
* @author TCLDUSER
* @description 针对表【address_book(地址管理)】的数据库操作Service
* @createDate 2022-11-07 11:15:49
*/
public interface AddressBookService extends IService<AddressBook> {

    public Result changeDefaultAddress(AddressBook addressBook);
}
