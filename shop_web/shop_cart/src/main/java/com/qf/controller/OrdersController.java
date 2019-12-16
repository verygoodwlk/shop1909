package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.aop.UserHolder;
import com.qf.entity.Address;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.util.PriceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Reference
    private ICartService cartService;
    
    @Reference
    private IAddressService addressService;

    /**
     * 跳转到订单编辑页面
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/toOrdersEdit")
    public String toOrdersEdit(Integer[] gid, Model model){
        System.out.println("需要下单的商品id：" + Arrays.toString(gid));
        //获得当前登录的用户信息
        User user = UserHolder.getUser();

        //商品清单
        List<ShopCart> shopCarts = cartService.queryCartsByGid(gid, user);

        //收货地址
        List<Address> addresses = addressService.queryByUid(user.getId());

        //计算总价
        double allprice = PriceUtil.allPrice(shopCarts);

        model.addAttribute("carts", shopCarts);
        model.addAttribute("addresses", addresses);
        model.addAttribute("allprice", allprice);

        return "ordersedit";
    }
}
