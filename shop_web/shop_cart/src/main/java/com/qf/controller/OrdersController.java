package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.aop.UserHolder;
import com.qf.entity.*;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import com.qf.util.PriceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Reference
    private ICartService cartService;
    
    @Reference
    private IAddressService addressService;

    @Reference
    private IOrderService orderService;

    /**
     * 跳转到订单编辑页面
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/toOrdersEdit")
    public String toOrdersEdit(Integer[] gid, Model model){
//        System.out.println("需要下单的商品id：" + Arrays.toString(gid));
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

    /**
     * 添加订单 - 下单
     * @return
     */
    @IsLogin(mustLogin = true)
    @PostMapping("/insert")
    @ResponseBody
    public ResultData<Orders> insertOrders(Integer aid, Integer[] cids){

        System.out.println("收货地址的id：" + aid);
        System.out.println("购物清单的id：" + Arrays.toString(cids));

        //下单
        Orders orders = orderService.insertOrder(cids, aid, UserHolder.getUser());

        //去支付

        return new ResultData<Orders>().setCode(ResultData.ResultCodeList.OK).setMsg("下单成功！").setData(orders);
    }

    /**
     * 查询订单列表
     * @return
     */
    @RequestMapping("/list")
    @IsLogin(mustLogin = true)
    public String list(Model model){

        User user = UserHolder.getUser();
        List<Orders> ordersList = orderService.queryByUid(user.getId());
        model.addAttribute("ordersList", ordersList);
        return "orderslist";
    }
}
