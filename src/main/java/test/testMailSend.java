package test;

import util.MailUtils;

public class testMailSend {
	    public static void main(String[] args) {
	        try {
	        	// �뽫�˴��� 690717394@qq.com �滻Ϊ�����ռ��������
	            MailUtils.sendMail("1139905744@qq.com", String.valueOf(Math.random() * 999));
	            System.out.println("�ʼ����ͳɹ�!");
	        } catch (Exception e) {
	        	System.out.println(1);
	            e.printStackTrace();
	        }
	    }

}
