package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.entity.ResultData;
import com.qf.service.IGoodsService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    //图片上传路径
    private String uploadPath = "C:/worker/imgs";

    @Reference
    private IGoodsService goodsService;

    @RequestMapping("/list")
    public String list(Model model){
        //调用商品服务，查询所有商品
        List<Goods> goods = goodsService.list();
//        System.out.println("查询所有商品：" + goods);
        model.addAttribute("goodsList" , goods);
        return "goodslist";
    }

    @RequestMapping("/uploader")
    @ResponseBody
    public ResultData<String> uploader(MultipartFile file){

        System.out.println("触发了文件上传：" + file.getOriginalFilename());

        //准备文件名称
        String filename = UUID.randomUUID().toString();

        String path = uploadPath + "/" + filename;


        try(
                InputStream in = file.getInputStream();
                OutputStream out = new FileOutputStream(path);
        ) {
            IOUtils.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData(path);
    }

    /**
     * 图片回显
     */
    @RequestMapping("/showimg")
    public void showImage(String imgPath, HttpServletResponse response){

        try (
                InputStream in = new FileInputStream(imgPath);
                ServletOutputStream out = response.getOutputStream();
        ){
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 添加商品
     * @return
     */
    @RequestMapping("/insert")
    public String insert(Goods goods){
        //调用商品服务，保存图片
        goodsService.insert(goods);
        return "redirect:/goods/list";
    }


}
