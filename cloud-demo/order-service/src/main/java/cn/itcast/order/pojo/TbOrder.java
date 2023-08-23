package cn.itcast.order.pojo;

import cn.itcast.nacos.pojo.User;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_order
 */
@TableName(value ="tb_order")
@Data
public class TbOrder implements Serializable {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private Long price;

    /**
     * 商品数量
     */
    private Integer num;

    //private User user;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}