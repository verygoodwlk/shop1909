package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sso")
public class SsoController {

    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/toregister")
    public String toRegister(){
        return "register";
    }

    @Reference
    private IUserService userService;

    /**
     * 注册
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, Model model){
        int result = userService.register(user);

        if(result > 0){
            //注册成功
            return "login";
        } else if(result == -1){
            //用户名已经存在
            model.addAttribute("error", "用户名已经存在！");
        } else {
            model.addAttribute("error", "注册失败！");
        }

        return "register";
    }
}
