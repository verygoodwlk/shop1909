package com.qf.service;

import com.qf.entity.ShopCart;
import com.qf.entity.User;

import java.util.List;

public interface ICartService {

    String insertCart(ShopCart cart, User user, String cartToken);

    List<ShopCart> listCarts(String cartToken, User user);

    List<ShopCart> queryCartsByGid(Integer[] gid, User user);

    List<ShopCart> queryCartsByCid(Integer[] cids);

    int deleteByCids(Integer[] cids);
}
