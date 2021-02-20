package cn.mercury.mercurycloud.service;

public interface MailService {
    String sendCodeToMail(String title,String text,String acceptEmail);
}
