package Impl;

import dao.UserDao;
import entity.User;
import service.UserService;
import util.DateUtils;
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
	}

	public User findUserByCode(String code) throws Exception {
        UserDao ud=new UserDaoImpl();
        User user=ud.findUserByCode(code);
		if(user==null) {
			//˵�����û�û��ע��ɹ�;����null,��UserServlet�����ж�
			return null;
		}
		user.setState(1);//���ע��ɹ��˾Ͱ��û���״̬�ӳ�ʼֵ0��Ϊ1,˵���Ѿ�������
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
		user.setLasttime(DateUtils.getSystemTime());
		ud.update(user);
		user.toString();
		return user;
	}

	public User updateInformation(String name,String nickname,String sex,String age,String profile) throws Exception {
		UserDao ud=new UserDaoImpl();
		User user=ud.getUser(name);
		if(user==null) {
			return null;
		}
		user.setNickname(nickname);
		user.setSex(sex);
		user.setAge(age);
		user.setProfile(profile);
		ud.update(user);
		return user;
	}



	public User updatePassword(String name,String password) throws Exception {
		UserDao ud = new UserDaoImpl();
		User user=ud.getUser(name);
		if(user==null) {
			return null;
			}
		user.setPassword(password);
		ud.update(user);
		return user;
	}

	public User findUserByName(String name) throws Exception {
		UserDao ud=new UserDaoImpl();
		User user=ud.getUser(name);
		if(user==null) {
			//˵���û����������ݿ��У�����null����UserServlet�����ж�
			return null;
		}
		return user;
	}

	public User updatePicture(String name, String profilehead) throws Exception {
		UserDao ud = new UserDaoImpl();
		User user=ud.getUser(name);
		if(user==null) {
			return null;
			}
		user.setProfilehead(profilehead);
		ud.update(user);
		return user;
	}

	public User findUserByNickname(String nickname) throws Exception {
		UserDao ud=new UserDaoImpl();
		User user=ud.findUserByNiackname(nickname);
		if(user==null) {
			//˵���û����������ݿ��У�����null����UserServlet�����ж�
			return null;
		}
		return user;
	}

}
