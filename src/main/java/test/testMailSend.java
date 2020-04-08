package test;

import util.MailUtils;

public class testMailSend {
	    public static void main(String[] args) {
	        try {
	        	// 请将此处的 690717394@qq.com 替换为您的收件邮箱号码
	            MailUtils.sendMail("1139905744@qq.com", String.valueOf(Math.random() * 999));
	            System.out.println("邮件发送成功!");
	        } catch (Exception e) {
	        	System.out.println(1);
	            e.printStackTrace();
	        }
	    }

}
