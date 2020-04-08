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
	
	//ͨ����֤�����û�
	public User findUserByUsernameAndPwd(String username, String password) throws Exception;


}
