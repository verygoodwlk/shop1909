package com.qf.exception;

import com.qf.entity.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * 1、如何处理非代码的异常（404）
 * 2、如何分别处理同步请求和ajax异步请求
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, Exception e){
        System.out.println("项目出现异常！" + e.getMessage());

        String header = request.getHeader("X-Requested-With");
        if(header != null && header.equals("XMLHttpRequest")){
            //说明当前是ajax请求
            //如果是ajax请求，出现异常应该返回json串
            return new ResultData<>().setCode(ResultData.ResultCodeList.ERROR).setMsg("服务器繁忙，请稍后再试！");
        } else {
            //说明当前是一个同步请求
            //如果是同步请求（表单提交、href...），出现异常应该返回错误页面
            return new ModelAndView("myerror");
        }
    }
}
