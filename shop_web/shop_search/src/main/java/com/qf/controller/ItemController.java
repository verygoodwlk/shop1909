package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Reference
    private IGoodsService service;

    @RequestMapping("/showById")
    public String showById(Integer id, Model model){
        Goods goods = service.queryById(id);
        model.addAttribute("goods", goods);
        return "goodsitem";
    }
}
