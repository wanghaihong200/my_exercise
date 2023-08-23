package com.example.springbootlearn.model.qo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-10-28 17:50
 */
@Data
public class PageQo {
    @ApiModelProperty(name = "page", value="页码", example = "1")
    private Integer page = 1;

    @ApiModelProperty(name = "pageSize", value="每页展示记录数", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(name = "name", value="员工姓名", example = "乔丹")
    private String name;
}
