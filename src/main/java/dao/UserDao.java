package dao;

import java.sql.SQLException;

import entity.User;

public interface UserDao {
	// ����û�
	public void add(User user);
	
	//ͨ����֤�����û�
	public User findUserByCode(String code) throws Exception;
	
	//�޸���֤״̬
	public void update(User user) throws SQLException;
	
	//ͨ���˺���Ϣ���û�
	public User findUserByUsernameAndPwd(String username, String password) throws Exception;
	
	//�޸��û���Ϣ
	public void updateInformation(User user) throws Exception;
	
	//����û�
	public User getUser(String name)throws Exception;
	
	//�޸��û�����
	public void updatePassword(User user) throws Exception;
}
