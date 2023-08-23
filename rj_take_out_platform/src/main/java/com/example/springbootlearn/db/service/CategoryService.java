package com.example.springbootlearn.db.service;

import com.example.springbootlearn.db.domain.ice.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootlearn.model.Result;

/**
* @author TCLDUSER
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-11-04 15:54:19
*/
public interface CategoryService extends IService<Category> {
    /**
     * 根据id删除分类
     * @param id
     */
    public Result remove(Long id);


}
