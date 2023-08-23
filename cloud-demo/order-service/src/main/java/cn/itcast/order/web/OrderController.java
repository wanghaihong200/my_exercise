package cn.itcast.order.web;

import cn.itcast.nacos.client.UserClient;
import cn.itcast.order.pojo.TbOrder;
import cn.itcast.order.service.TbOrderService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private TbOrderService orderService;

    @Autowired
    private UserClient userClient;

    @GetMapping("{orderId}")
    public TbOrder queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        TbOrder order = orderService.queryOrder(orderId);
        String user = JSON.toJSONString(userClient.findById(order.getUserId()));
        System.out.println(user);
        return order;
    }
}
