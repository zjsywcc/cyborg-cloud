package com.ese.cloud.client.util;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.File;


/**
 * 邮件发送服务
 * Created by rencong on 16/5/29.
 */
@Component
public class EmailServer {

    Logger logger = Logger.getLogger(EmailServer.class);

//    @Autowired
//    private JavaMailSender javaMailSender;

    /***
     * 邮件发送服务
     * @param to 收件人
     * @param from  发件人
     * @param subject 主题
     * @param text 邮件内容
     */
    public void send(String to,String from,String subject,String text) {
//        MimeMessage mail = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(mail, true,"utf-8");
//            helper.setTo(to);
//            //helper.setReplyTo(replyTo);
//            helper.setFrom(from);
//            helper.setSubject(subject);
//            helper.setText(text,true);
//
//        } catch (MessagingException e) {
//            logger.error(e);
//        } catch (javax.mail.MessagingException e) {
//            logger.error(e);
//        } finally {
//            javaMailSender.send(mail);
//        }

        //return helper;
    }


    /***
     * 邮件发送服务
     * @param to 收件人
     * @param from  发件人
     * @param subject 主题
     * @param text 邮件内容
     */
    public void send(String to,String from,String subject,String text,String fileurl) {


//        MimeMessage mail = javaMailSender.createMimeMessage();
//
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(mail, true,"utf-8");
//            helper.setTo(to);
//            helper.setFrom(from);
//            helper.setSubject(subject);
//            helper.setText(text,true);
//            File f = new File(fileurl);
//            File in = null;
//            try {
//                in = new File(fileurl);
//            } catch (Exception e) {
//                logger.error("io error",e);
//            }
//            helper.addAttachment("unusualMob.xls",in);
//
//        } catch (MessagingException e) {
//            logger.error(e);
//        } catch (javax.mail.MessagingException e) {
//            logger.error(e);
//        } finally {
//            javaMailSender.send(mail);
//        }

        //return helper;
    }


}
