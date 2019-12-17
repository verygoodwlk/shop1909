package com.qf.service;

import com.qf.entity.Orders;
import com.qf.entity.User;

import java.util.List;

public interface IOrderService {

    Orders insertOrder(Integer[] cids, Integer aid, User user);

    List<Orders> queryByUid(Integer uid);

    Orders queryById(Integer id);

    Orders QueryByOid(String oid);

    int updateOrderStatus(String orderid, Integer status);

}
