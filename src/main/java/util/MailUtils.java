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
		// ����һ���������ʼ��������Ự���� Session
		Properties props = new Properties();
		//���÷��͵�Э��
		props.setProperty("mail.transport.protocol", "SMTP");
		
		//���÷����ʼ��ķ�����
		props.setProperty("mail.host", "smtp.163.com");/**localhost�Լ���������������޸ĵ�,���õ��Ǳ��ص�eyou������*/
		
		props.setProperty("mail.smtp.auth", "true");// ָ����֤Ϊtrue
 
		// ������֤��
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				//���÷����˵��ʺź�����
				return new PasswordAuthentication("ys1597532486@163.com","ys159753");/**�˺ź���֤����*/
			}
		};
 
		Session session = Session.getInstance(props, auth);
 
		//����һ��Message�����൱�����ʼ�����
		Message message = new MimeMessage(session);
		
		//���÷�����
		message.setFrom(new InternetAddress("ys1597532486@163.com"));/**�����˺�*/
		
		//���÷��ͷ�ʽ�������
		message.setRecipient(RecipientType.TO, new InternetAddress(email)); 
 
		//�����ʼ�����
		message.setSubject("�û�����");
		
 
		//�����ʼ�����
		message.setContent(emailMsg, "text/html;charset=utf-8");
 
		// ���� Transport���ڽ��ʼ�����
		Transport.send(message);
	}


}
