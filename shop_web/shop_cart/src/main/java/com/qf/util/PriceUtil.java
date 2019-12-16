package com.qf.util;

import com.qf.entity.ShopCart;

import java.math.BigDecimal;
import java.util.List;

public class PriceUtil {

    /**
     * 根据购物车清单计算总价格
     * @param carts
     * @return
     */
    public static double allPrice(List<ShopCart> carts){

        BigDecimal allPirce = BigDecimal.valueOf(0);
        if(carts != null){
            for (ShopCart cart : carts) {
                allPirce = allPirce.add(cart.getCartPrice());
            }
        }

        return allPirce.doubleValue();
    }
}
