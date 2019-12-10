package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Email;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/forget")
public class ForgetController {

    @Reference
    private IUserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 跳转到忘记密码的页面
     * @return
     */
    @RequestMapping("/toForgetPassword")
    public String toForgetPassword(){
        return "forgetpassword";
    }

    /**
     * 发送找回密码的邮件
     * @return
     */
    @RequestMapping("/sendmail")
    @ResponseBody
    public ResultData<Map<String, String>> sendPasswordMail(String username){

        //用户名找到用户信息
        User user = userService.queryByUserName(username);

        if(user == null){
            //用户名不存在
            return new ResultData<Map<String, String>>().setCode(ResultData.ResultCodeList.ERROR).setMsg("用户名不存在！");
        }

        //生成uuid
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(uuid, username);
        redisTemplate.expire(uuid, 5, TimeUnit.MINUTES);

        //找回密码的链接 - 1、一次性  2、时效性 3、只能改自己的密码
        String url = "http://localhost:8082/forget/toUpdatePassword?token=" + uuid;

        //发送邮件
        Email email = new Email()
                .setTo(user.getEmail())
                .setSubject("找回密码，非本人操作请忽略！")
                .setContext("点击<a href='" + url + "'>这里</a>找回密码")
                .setSendTime(new Date());

        rabbitTemplate.convertAndSend("mail_exchange", "", email);

        String emailStr = user.getEmail();
        String mStr = emailStr.substring(3, emailStr.lastIndexOf("@"));
        String showmail = emailStr.replace(mStr, "******");

        String tomail = "mail." + emailStr.substring(emailStr.lastIndexOf("@") + 1);//1111111@qq.com

        Map<String, String> map = new HashMap<>();
        map.put("showmail", showmail);
        map.put("tomail", tomail);

        return new ResultData<Map<String, String>>()
                .setCode(ResultData.ResultCodeList.OK)
                .setMsg("邮件发送成功！")
                .setData(map);
    }

    /**
     * 跳转到修改密码的页面
     * @return
     */
    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(){
        return "updatepassword";
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping("/updatepassword")
    public String updatepassword(String newpassword, String token){

        //检验token的有效性
        String username = redisTemplate.opsForValue().get(token);
        if(username == null){
            //token过期或者无效
            return "updateerror";
        }

        //
        userService.updatePassword(username, newpassword);

        //删除token
        redisTemplate.delete(token);

        return "login";
    }
}
