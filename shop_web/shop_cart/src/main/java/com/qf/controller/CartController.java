package com.qf.controller;

import com.qf.aop.IsLogin;
import com.qf.aop.UserHolder;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {


    /**
     * 添加购物车
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/insert")
    public String insert(Integer gid, Integer gnumber){
        System.out.println("加入购物车：" + gid + "---" + gnumber);

        User user = UserHolder.getUser();
        System.out.println("当前的登录信息：" + user);

        //调用购物车服务添加购物车

        return "insertok";
    }
}
