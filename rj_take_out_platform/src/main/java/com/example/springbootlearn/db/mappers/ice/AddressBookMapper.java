package com.example.springbootlearn.db.mappers.ice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.springbootlearn.db.domain.ice.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TCLDUSER
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2022-11-07 11:15:49
* @Entity com.example.springbootlearn.db.domain.ice.AddressBook
*/
@Mapper
@DS("ice")
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




