package cn.mercury.mercurycloud.service.impl;

import cn.mercury.mercurycloud.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 用来发送模版邮件
     */
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public String sendCodeToMail(String title,String text,String acceptEmail) {
        int code = (int) ((Math.random() * 9 + 1) * 100000);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        //   MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper = new MimeMessageHelper(message,true);
            helper.setSubject(title);
            helper.setText("Mercury-Cloud:注册验证码为 : "+code);
//            helper.setText(text);
            helper.setFrom(from,"Mercury-Cloud");
            helper.setTo(acceptEmail);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
//            new RuntimeException("发送邮件失败");
        }

        return String.valueOf(code);
    }
}
