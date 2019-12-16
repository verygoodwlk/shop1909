package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.aop.UserHolder;
import com.qf.entity.Address;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/address")
public class AddressController {

    @Reference
    private IAddressService addressService;

    /**
     * 新增收货地址
     * @return
     */
    @RequestMapping("/insert")
    @IsLogin
    @ResponseBody
    public ResultData<String> insert(Address address){
        User user = UserHolder.getUser();
        addressService.insertAddress(address,user);
        return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setMsg("添加成功！");
    }
}
