package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

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

    /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, String returnUrl, HttpServletResponse response, Model model){

        //进行登录
        User user = userService.queryByUserName(username);
        if(user != null && user.getPassword().equals(password)){
            //登录成功
//            System.out.println("登录成功：" + user);

            //保存登录状态
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(token, user);
            redisTemplate.expire(token, 7, TimeUnit.DAYS);

            //将token写入浏览器的cookie中
            Cookie cookie = new Cookie("loginToken", token);
            cookie.setMaxAge(60 * 60 * 24 * 7);//单位是秒
            cookie.setPath("/");//路径
            cookie.setDomain("localhost");//域名  sb.com  注意：这个属性不能设置成顶级域名，不允许
//            cookie.setHttpOnly(true);//只有服务能够读取cookie，页面上的js脚本不允许读取修改该cookie
//            cookie.setSecure(true);//只有https协议下，服务器才会收到cookie

            response.addCookie(cookie);

            //默认跳转回首页
            if(returnUrl == null || returnUrl.equals("")){
                returnUrl = "http://localhost";
            }

            try {
                returnUrl = URLEncoder.encode(returnUrl, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //跳转到首页
            return "redirect:http://localhost:8083/cart/merge?returnUrl=" + returnUrl;
        }

        model.addAttribute("error", "账号或者密码错误！");

        //登录失败
        return "redirect:/sso/tologin";
    }

    /**
     * 判定是否登录
     * JSONP 解决跨域的方案
     * @return
     */
    @ResponseBody
    @RequestMapping("/islogin")
    public String isLogin(@CookieValue(name = "loginToken", required = false) String loginToken, String callback){
        System.out.println("获得客户端的loginToken：" + loginToken);

        ResultData<User> resultData = new ResultData<User>().setCode(ResultData.ResultCodeList.ERROR);
        if(loginToken != null){
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if(user != null){
                resultData.setCode(ResultData.ResultCodeList.OK);
                resultData.setData(user);
            }
        }

//        System.out.println("返回的结果：" + resultData);

        return callback != null ? callback + "(" + JSON.toJSONString(resultData) + ")" : JSON.toJSONString(resultData);
    }

    /**
     * springmvc注解解决跨域
     * @param loginToken
     * @return
     */
//    @CrossOrigin(allowCredentials = "true")
//    @ResponseBody
//    @RequestMapping("/islogin")
//    public ResultData<User> isLogin(@CookieValue(name = "loginToken", required = false) String loginToken){
//        System.out.println("获得客户端的loginToken：" + loginToken);
//
//        ResultData<User> resultData = new ResultData<User>().setCode(ResultData.ResultCodeList.ERROR);
//        if(loginToken != null){
//            User user = (User) redisTemplate.opsForValue().get(loginToken);
//            if(user != null){
//                resultData.setCode(ResultData.ResultCodeList.OK);
//                resultData.setData(user);
//            }
//        }
//        return resultData;
//    }

    /**
     * 注销请求
     * @return
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(name = "loginToken", required = false) String loginToken, HttpServletResponse response){
        System.out.println("注销请求：" + loginToken);
        redisTemplate.delete(loginToken);

        Cookie cookie = new Cookie("loginToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);

        return "redirect:/sso/tologin";
    }
}
