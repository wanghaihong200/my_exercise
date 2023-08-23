package com.example.springbootlearn.controller.backend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootlearn.db.domain.ice.Category;
import com.example.springbootlearn.db.service.CategoryService;
import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.model.qo.PageQo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-11-04 15:49
 */
@Slf4j
@RestController
@RequestMapping("/category")
@Api(tags="分类管理页面")
public class CategoryManagementController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("新增菜品分类")
    public Result save(@RequestBody Category category){
        log.info("category: {}", category);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName, category.getName());
        if (categoryService.getOne(queryWrapper) == null){
            categoryService.save(category);
        }else {
            return Result.fail(category.getName()+" 已存在");
        }

        return Result.success("新增菜品分类成功");
    }

    @GetMapping("/page")
    @ApiOperation("菜品分类信息 分页查询")
    public Result pageCategories(int page, int pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }

    @GetMapping("/delete")
    @ApiOperation("删除菜品分类信息")
    public Result deleteCategory(Long id){
        if (categoryService.getById(id) == null){
            return Result.fail("id:"+id+" 不存在");

        }
        return categoryService.remove(id);
    }

    @PostMapping("/update")
    @ApiOperation("根据id修改菜品分类信息")
    public Result update(@RequestBody Category category){
        log.info("category: {}", category);

        if (categoryService.getById(category.getId()) != null){
            categoryService.updateById(category);
        }else {
            return Result.fail(category.getId()+" 不存在");
        }

        return Result.success("修改菜品分类信息成功");
    }


    @GetMapping("/list")
    @ApiOperation("查询菜品分类列表")
    public Result list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null, Category::getType, category.getType())
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getUpdateTime);

        List<Category> categoryList = categoryService.list(queryWrapper);
        return Result.success(categoryList);
    }
}
