package com.example.springbootlearn.db.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Entity 基类
 * @author: 王海虹
 * @create: 2022-10-19 17:20
 */
@Getter
@Setter
@ApiModel
public class
BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    @TableField(exist = false)
    @ApiModelProperty(name = "searchValue", value = "搜索值", example = "123")
    private String searchValue;

    /**
     * 创建者
     */
    @TableField(exist = false)
    @ApiModelProperty(name = "createBy", value = "创建者", example = "123")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(exist = false)
    @ApiModelProperty(name = "createTime", value = "创建时间", example = "123")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(exist = false)
    @ApiModelProperty(name = "updateBy", value = "更新者", example = "123")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(exist = false)
    @ApiModelProperty(name = "updateTime", value = "更新时间", example = "123")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(exist = false)
    @ApiModelProperty(name = "remark", value = "备注", example = "123")
    private String remark;

    /**
     * 请求参数
     */
    @TableField(exist = false)
    @ApiModelProperty(name = "params", value = "请求参数", example = "123")
    private Map<String, Object> params;

    /**
     * 页码
     */
    @TableField(exist = false)
    @ApiModelProperty(name = "pageNo", value = "页码", example = "1")
    private Integer pageNo = 1;

    /**
     * 条数
     */
    @TableField(exist = false)
    @ApiModelProperty(name = "pageSize", value = "条数", example = "10")
    private Integer pageSize = 10;

    /**
     * limit offset
     */
    @TableField(exist = false)
    private Integer offset;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }
}
