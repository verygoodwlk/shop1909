package com.qf.service;

import com.qf.entity.ShopCart;
import com.qf.entity.User;

import java.util.List;

public interface ICartService {

    String insertCart(ShopCart cart, User user, String cartToken);

    List<ShopCart> listCarts(String cartToken, User user);
}
