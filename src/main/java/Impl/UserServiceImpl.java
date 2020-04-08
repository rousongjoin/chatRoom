package Impl;

import dao.UserDao;
import entity.User;
import service.UserService;
import util.MailUtils;

public class UserServiceImpl implements UserService {
	
	public void add(User user) throws Exception {
		UserDao ud = new UserDaoImpl();
		ud.add(user);
		// ���ͼ����ʼ�
			// email:�ռ��˵�ַ
				// emailMsg:�ʼ�������
		String code = user.getCode();
		String emailMsg = "��ӭ��ע���Ϊ���ǵĻ�Ա,�����֤��Ϊ��"+code+
				"<a href='http://localhost:8080/chatRoom/UserServlet?method=activeUI&code="
				+ user.getCode() + "'>������Ӽ���</a>";
		MailUtils.sendMail(user.getE_mail(), emailMsg);// �����һ��������
		System.out.println("�ʼ����ͳɹ�");
	}

	public User findUserByCode(String code) throws Exception {
        UserDao ud=new UserDaoImpl();
        User user=ud.findUserByCode(code);
		if(user==null) {
			//˵�����û�û��ע��ɹ�;����null,��UserServlet�����ж�
			return null;
		}
		user.setState(1);//���ע��ɹ��˾Ͱ��û���״̬�ӳ�ʼֵ0��Ϊ1,˵���Ѿ�������
		System.out.println("�Ѵ��ݼ�������");
		ud.update(user);
		return user;
		
	}

	public User findUserByUsernameAndPwd(String username, String password) throws Exception {
		UserDao ud=new UserDaoImpl();
		User user=ud.findUserByUsernameAndPwd(username, password);
		if(user==null) {
			//˵���û����������ݿ��У�����null����UserServlet�����ж�
			return null;
		}
		return user;
	}

}
