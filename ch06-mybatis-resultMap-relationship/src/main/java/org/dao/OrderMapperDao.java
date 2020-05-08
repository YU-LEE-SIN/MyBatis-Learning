package org.dao;

import org.pojo.Orders;
import org.pojo.User;

import java.util.List;

/**
 * @author yu
 * @date 2020/4/21
 */
public interface OrderMapperDao {
    /**查询某个用户的所有订单及商品明细（一个用户多个订单） */
    public List<Orders> findUserOrders(Integer userId);
}
