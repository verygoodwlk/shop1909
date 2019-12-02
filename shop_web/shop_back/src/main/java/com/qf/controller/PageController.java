package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topage")
public class PageController {

    @RequestMapping("/{page}")
    public String topage(@PathVariable String page){
        return page;
    }
}
