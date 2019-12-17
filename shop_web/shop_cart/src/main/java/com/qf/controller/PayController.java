package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import com.qf.util.AlipayUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Reference
    private IOrderService orderService;

    @RequestMapping("/alipay")
    @ResponseBody
    public void alipay(String orderid, HttpServletResponse response) throws IOException {

        Orders orders = orderService.QueryByOid(orderid);

        //创建一个支付宝的客户端对象（支付、退款、查询、关闭交易....）
        AlipayClient alipayClient = AlipayUtil.getAlipayClient();

        //创建一个支付页面申请对象
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request

        //设置支付完成后的同步请求
        alipayRequest.setReturnUrl("http://localhost:8083/orders/list");
        //设置支付完成后的异步请求 - 决定支付是否成功
        alipayRequest.setNotifyUrl("http://verygoodwlk.xicp.net/pay/payCallBack");//在公共参数中设置回跳和通知地址

        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orders.getOrderid() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + orders.getAllprice().doubleValue() + "," +
                "    \"subject\":\"" + orders.getOrderDetils().get(0).getSubject() + "..\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数


        //发送请求给支付宝，支付宝返回一个支付页面
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //直接将支付页面返回给用户浏览器
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }


    /**
     * 支付宝的回调接口
     * @return
     */
    @RequestMapping("/payCallBack")
    @ResponseBody
    public String payCallBack(String out_trade_no, String trade_status){

        if(trade_status.equals("TRADE_SUCCESS")){
            //支付成功，修改订单状态
            orderService.updateOrderStatus(out_trade_no, 1);
        }

        return null;
    }
}
