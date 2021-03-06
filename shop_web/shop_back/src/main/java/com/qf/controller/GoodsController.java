package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.entity.ResultData;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    //图片上传路径
    private String uploadPath = "C:/worker/imgs";

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Reference
    private IGoodsService goodsService;

    @RequestMapping("/list")
    public String list(Model model){
        //调用商品服务，查询所有商品
        List<Goods> goods = goodsService.list();

        //分页查询
//        Page<Goods> page = new Page<>(1, 2);
//        IPage<Goods> ipage = goodsService.listPage(page);
//
//        System.out.println("当前页：" + ipage.getCurrent());
//        System.out.println("当前页：" + ipage.getPages());
//        System.out.println("当前页：" + ipage.getSize());
//        System.out.println("当前页：" + ipage.getTotal());
//        System.out.println("-->" + ipage.getRecords());

        model.addAttribute("goodsList" , goods);
//        model.addAttribute("page" , ipage);
        return "goodslist";
    }

    @RequestMapping("/uploader")
    @ResponseBody
    public ResultData<String> uploader(MultipartFile file){

        //上传到fastdfs
        String path = null;
        try {
            StorePath resultPath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "JPG",
                    null
            );

            path = resultPath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData("http://www.img.com:8080/" + path);
    }

    /**
     * 图片回显

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

    }*/

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
