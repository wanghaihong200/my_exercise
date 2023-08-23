package com.example.springbootlearn.db.domain.ice;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 用户信息表 实体类
 * </p>
 *
 * @author www.javacoder.top
 * @since 2022-10-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {

    private static final long serialVersionUID = 2809936603229476091L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @NotBlank(message = "用户名不能为空")
    private String name;

    /**
     * 电话
     */
    private String phone;

    // 性别
    private String sex;

    // 身份证号
    private String idNumber;

    // 头像
    private String avatar;

    // 状态：0：有效， 1：无效
    private int status;

    /**
     * 创建时间
     */
    //@TableField(fill= FieldFill.INSERT)
    //@ApiModelProperty(name = "createTime", value = "创建时间", example = "123")
    //@JSONField(format="yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //private Date createTime;

    /**
     * 更新时间
     */
    //@TableField(fill=FieldFill.INSERT_UPDATE)
    //@ApiModelProperty(name = "updateTime", value = "更新时间", example = "123")
    //@JSONField(format="yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //private Date updateTime;
}
