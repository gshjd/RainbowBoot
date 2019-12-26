package com.example.rainboot.common.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 邮件发送工具
 *
 * @version 1.0
 * @Author 小熊
 * @Created 13:14 2018/4/3
 * @Remark
 */
public class SendEmailUtil {
    /**
     * 收件人电子邮箱
     */
    private String addresseeEmail;

    /**
     * 发件人电子邮箱
     */
    private String senderEmail;

    /**
     * 获取默认session对象
     */
    private Session session;

    /**
     * 邮件类型
     * 纯文本
     */
    public static final String TEXT = "text";
    /**
     * HTML
     */
    public static final String HTML = "html";
    /**
     * QQ邮箱服务器
     */
    public static final String STMP = "smtp.qq.com";

    /**
     * 初始化发送邮件对象
     *
     * @param host           邮箱服务器
     * @param senderEmail    发件人邮箱
     * @param senderPassword 发件人密码
     * @throws GeneralSecurityException 异常
     */
    public SendEmailUtil(String host, final String senderEmail, final String senderPassword) throws Exception {
        this.senderEmail = senderEmail;
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        // 获取默认session对象
        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // SSL授权
        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
    }

    /**
     * 发送邮件(需要先指定收件人)
     *
     * @param title   邮件标题
     * @param content 邮件内容
     * @param type    邮件类型
     * @throws Exception 异常
     */
    public void sendEmail(String title, String content, String type) throws Exception {
        // 创建默认的 MimeMessage 对象
        MimeMessage message = new MimeMessage(session);

        // 添加发送人
        message.setFrom(new InternetAddress(senderEmail));

        // 添加接收人
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(addresseeEmail));

        // 邮件标题
        message.setSubject(MimeUtility.encodeText(title, MimeUtility.javaCharset("utf-8"), null));

        switch (type) {
            case "text":
                // 设置纯文本消息内容
                message.setText(content, "utf-8");
                break;
            case "html":
                // 设置HTML消息内容
                message.setContent(content, "text/html;charset=utf-8");
            default:
        }

        // 发送消息
        Transport.send(message);
    }

    public void setAddresseeEmail(String addresseeEmail) {
        this.addresseeEmail = addresseeEmail;
    }

    /* public static void main(String[] args) throws Exception {
        SendEmailUtil sendEmailUtil = new SendEmailUtil("smtp.qq.com","745133927@qq.com","zgjaepkdwnvibahf");
        sendEmailUtil.setAddresseeEmail("469722471@qq.com");
        sendEmailUtil.sendEmail("测试邮件标题","<h1 style='color: red'>你在说什么</h1>",sendEmailUtil.html);
    }*/
}
