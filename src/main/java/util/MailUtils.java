package util;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

@SuppressWarnings("restriction")
public class MailUtils {
	
	public static void sendMail(String email, String emailMsg)
			throws  MessagingException, javax.mail.MessagingException {
		// 创建一个程序与邮件服务器会话对象 Session
		Properties props = new Properties();
		//设置发送的协议
		props.setProperty("mail.transport.protocol", "SMTP");
		
		//设置发送邮件的服务器
		props.setProperty("mail.host", "smtp.163.com");/**localhost自己根据邮箱服务器修改的,我用的是本地的eyou服务器*/
		
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true
 
		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				//设置发送人的帐号和密码
				return new PasswordAuthentication("ys1597532486@163.com","ys159753");/**账号和验证密码*/
			}
		};
 
		Session session = Session.getInstance(props, auth);
 
		//创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);
		
		//设置发送者
		message.setFrom(new InternetAddress("ys1597532486@163.com"));/**邮箱账号*/
		
		//设置发送方式与接收者
		message.setRecipient(RecipientType.TO, new InternetAddress(email)); 
 
		//设置邮件主题
		message.setSubject("用户激活");
		
 
		//设置邮件内容
		message.setContent(emailMsg, "text/html;charset=utf-8");
 
		// 创建 Transport用于将邮件发送
		Transport.send(message);
	}


}
