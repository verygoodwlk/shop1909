package com.qf.shop_mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@SpringBootTest
class ShopMailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void contextLoads() throws MessagingException {

        //创建一封邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //Spring提供的一个便捷的邮件设置对象
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        //设置邮件的内容

        //标题
        mimeMessageHelper.setSubject("会员到期通知！");

        //发送方
        mimeMessageHelper.setFrom("verygoodwlk@sina.cn");

        //接收方
        mimeMessageHelper.setTo("1120673996@qq.com");//接收者
//        mimeMessageHelper.setCc("xiaohong@qq.com");//抄送
//        mimeMessageHelper.setBcc("xiaogang@qq.com");//密送

        //设置内容
        mimeMessageHelper.setText("会员已经到期，请及时续费！<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575973255147&di=0706e7c23e0954a59cc64921fb5574f1&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2F8cb1cb1349540923ebfc3dee9258d109b2de498c.jpg'/>",
                true);

        //发送附件
        mimeMessageHelper.addAttachment("我的附件.jpg", new File("C:\\Users\\Ken\\Pictures\\Saved Pictures\\联盟.jpg"));

        //设置当前时间
        mimeMessageHelper.setSentDate(new Date());

        //发送邮件
        javaMailSender.send(mimeMessage);
        System.out.println("邮件发送成功！");

    }

}
