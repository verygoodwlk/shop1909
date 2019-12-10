package com.qf.listener;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class RabbitMQListener {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @RabbitListener(queuesToDeclare = @Queue(name = "mail_queue"))
    public void msgHandler(Email email) throws MessagingException {

        //创建一封邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //Spring提供的一个便捷的邮件设置对象
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        //设置邮件的内容

        //标题
        mimeMessageHelper.setSubject(email.getSubject());

        //发送方
        mimeMessageHelper.setFrom(from);

        //接收方
        mimeMessageHelper.setTo(email.getTo());//接收者
//        mimeMessageHelper.setCc("xiaohong@qq.com");//抄送
//        mimeMessageHelper.setBcc("xiaogang@qq.com");//密送

        //设置内容
        mimeMessageHelper.setText(email.getContext(),true);

        //发送附件
//        mimeMessageHelper.addAttachment("我的附件.jpg", new File("C:\\Users\\Ken\\Pictures\\Saved Pictures\\联盟.jpg"));

        //设置当前时间
        mimeMessageHelper.setSentDate(email.getSendTime());

        //发送邮件
        javaMailSender.send(mimeMessage);
        System.out.println("邮件发送成功！");

    }
}
