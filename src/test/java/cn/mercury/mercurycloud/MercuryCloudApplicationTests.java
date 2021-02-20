package cn.mercury.mercurycloud;


import cn.mercury.mercurycloud.service.MailService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Stack;
import java.util.UUID;

@SpringBootTest
class MercuryCloudApplicationTests {

    @Autowired
    private mail m;
    @Autowired
    private MailService mailService;
    @Test
    void contextLoads() {
//        System.out.println(m.from);
   //     m.send("g2090007549@gmail.com","测试邮件", UUID.randomUUID().toString().toUpperCase());
//
//        Stack<Character> s = new Stack<Character>();
        mailService.sendCodeToMail("测试邮件2","org.thymeleaf.context.Context@61bcbcce","g2090007549@gmail.com");
    }
}
