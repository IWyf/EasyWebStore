package com.yif.store.test;
 
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
 
public class testmail {
 
	/**
	 * 使用Transport 非静态方法 发送邮件 连接163服务，给QQ邮箱发邮件
	 * @throws MessagingException 
	 */
	public static void main(String[] args) throws MessagingException{
		// 属性对象
		Properties properties = new Properties();
		// 开启debug调试 ，打印信息
		properties.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		properties.setProperty("mail.smtp.auth", "true");
		// 发送服务器端口，可以不设置，默认是25
		properties.setProperty("mail.smtp.port", "25");
		// 发送邮件协议名称
		properties.setProperty("mail.transport.protocol", "smtp");
		// 设置邮件服务器主机名
		properties.setProperty("mail.host", "smtp.163.com");
		// 环境信息
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 在session中设置账户信息，Transport发送邮件时会使用
				return new PasswordAuthentication("13419631181@163.com", "shouquanma163");
			}
		});
 
		// 创建邮件对象
		Message message = new MimeMessage(session);
		// 设置主题
		message.setSubject("Tets email");
		// 发件人
		message.setFrom(new InternetAddress("13419631181@163.com"));
		// 多个收件人
		message.setRecipients(RecipientType.TO, InternetAddress.parse("756794035@qq.com"));
		// 抄送人
//		message.setRecipient(RecipientType.CC, new InternetAddress("756794035@qq.com"));
		// 暗送人
//		message.setRecipient(RecipientType.BCC, new InternetAddress("756794035@qq.com"));
		// HTML内容
		message.setContent("<a href='http://blog.csdn.net/y534560449'>Hello Boy!!</a>", "text/html;charset=utf-8");
 
		// 连接邮件服务器、发送邮件、关闭连接，全做了
		Transport.send(message);
 
	}
}