package iee.yh.Mymall.Auth.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * @author yanghan
 * @date 2022/5/2
 */
@Component
@Scope("prototype")
public class EmailTask implements Serializable {

    @Resource
    private JavaMailSender javaMailSender;


    private final String mailbox = "yanghan1214@163.com";

    @Async
    public void sendAsync(SimpleMailMessage simpleMailMessage){
        simpleMailMessage.setFrom(mailbox);
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送邮件
     */
    public void sendMail(String addressEmail,String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(addressEmail);
        mailMessage.setSubject("账号注册验证码");
        mailMessage.setText("您好！您的验证码为【" + code + "】,10分钟内有效,谢谢!");
        this.sendAsync(mailMessage);
    }
}
